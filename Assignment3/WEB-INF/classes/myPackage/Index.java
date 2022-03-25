/*Index.java
   *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   Index page which displays list of articles with a side menu with options for staff and users
*/
package myPackage;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;@WebServlet(urlPatterns = {"/Index"})
public class Index extends HttpServlet 
{
	private UserDatabase data = new UserDatabase();
	private List<Issue> tempList = new ArrayList<Issue>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp");
        dispatcher.forward(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
		
        String name = request.getParameter("userId"); // username
        String password = request.getParameter("password"); // password
	String deleteID = request.getParameter("delete"); // ID of issue to delete
	String paramDate = request.getParameter("date"); // date for sorting
	String paramCategory = request.getParameter("category"); // category for sorting
		
        KnowledgeBaseBean kb = (KnowledgeBaseBean) session.getAttribute("kb");
	kb = new KnowledgeBaseBean();
	session.setAttribute("kb", kb);

        session.setAttribute("sorted", "");
		
		if(deleteID != null) // deletes an issue from button on index page
		{
				kb = (KnowledgeBaseBean) session.getAttribute("kb");
				List<Issue> list = kb.getIssues();
				ListIterator<Issue> listIter = list.listIterator();
				Issue temp;

				while(listIter.hasNext()) //  loops through each issue
				{ 
					temp = listIter.next();
					if(temp.getIssueID().equals(deleteID)) // finds issue to delete then removes it
					{
						listIter.remove();
						break;
					}
				}
				kb.setIssues(list); // update database list of issues
				
				CommentsBean bean = new CommentsBean();
				List<Comment> comlist = bean.getComments();
				ListIterator<Comment> listIter2 = comlist.listIterator();
				Comment temp2;
				while(listIter2.hasNext())
				{ 
					temp2 = listIter2.next();
					if(temp2.getIssueID().equals(deleteID)) // loops through each issue
					{
						listIter2.remove(); // if a comment is found which is linked to the issue through a unique ID then remove it
					}
				}
				bean.setComments(comlist); // update comment list
		}
		
		if(name != null && password != null) // checks if user exists in database, if not then display error page
		{
			boolean isStaff = false;
			int flag = 0;
			
			List<GeneralUser> general_list = data.getGeneral(); // get general users from sql database
			List<Staff> staff_list = data.getStaff(); // get staff from sql database
			ListIterator<GeneralUser> listIter_gen = general_list.listIterator();
			ListIterator<Staff> listIter_staff = staff_list.listIterator();
			
			GeneralUser temp_gen;
			Staff temp_staff;
			
			while(listIter_gen.hasNext()) // loops through each general user in the sql database
			{ 
				temp_gen = listIter_gen.next();
				if(temp_gen.getUsername().equals(name) && temp_gen.getPassword().equals(password))
				{
					isStaff = false; // if exists then it is not a staff
				}
				else
				{
					flag++;
				}
			}
			
			while(listIter_staff.hasNext()) // loops through each staff member in sql database
			{ 
				temp_staff = listIter_staff.next();
				if(temp_staff.getUsername().equals(name) && temp_staff.getPassword().equals(password))
				{
					isStaff = true; // if exists then it is a staff
				}
				else
				{
					flag++;
				}
			}
			
			if(flag >= 6) // if user is not any of the 3 general users or 3 of the staff members so it would run 6 times then go to error page
			{
				session.invalidate();
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp");
				dispatcher.forward(request, response);
			}
			
			if(isStaff) // if a staff member 
			{
				Staff staffMem = new Staff(); // create new local staff object 
				
				//staffMem.setFirstName("null");
				//staffMem.setLastName("null");
				//staffMem.setContactNum("null");
				//staffMem.setEmail("null");
				staffMem.setPassword(password); // set password
				staffMem.setUsername(name); // set username
				
				session.setAttribute("staffUser", staffMem); // send staff object to session
			}
			else // same for general user
			{
				GeneralUser genMem = new GeneralUser();
				
				//genMem.setFirstName("null");
				//genMem.setLastName("null");
				//genMem.setContactNum("null");
				//genMem.setEmail("null");
				genMem.setPassword(password);
				genMem.setUsername(name);
				
				session.setAttribute("generalUser", genMem);
			}
		}


	    if(paramCategory == null) // initial set for category to avoid null errors
	    {
	    	paramCategory = "";
	    }
	    

        if(!paramCategory.equals(""))	//If the category parameter is not empty
        {
        	Issue tempIssue = new Issue();	//temporary Issue used to take on the value as the list is iterated over

        	List<Issue> issues = new ArrayList<Issue>();
        	issues = kb.getIssues();		//Issues is set to a list of issues stored in the knowledge base

        	ListIterator<Issue> iterIssues = issues.listIterator();		//Define an iterator to iterate through the list

        	for(int i = 0; i < issues.size(); i++)	//loop for the size of the list
        	{
        		tempIssue = iterIssues.next();	//Next item in the list

        		if(tempIssue.getCategory().equals(paramCategory) || paramCategory.equals("All"))	//If the category the user selected and the category stored for the issueare the same
        		{
        			tempList.add(tempIssue);	//Add to filtered list
        		}
        	}
        	session.setAttribute("sortedList", tempList);	//Save the filtered list as an attribute
        	session.setAttribute("sorted", "true");
        }

        if(paramDate == null)	//Check if the date parameter is the null to avoid null errors
        {
        	paramDate = "";
        }
        else
        {
        	List<Issue> issues = new ArrayList<Issue>();
        	issues = tempList;

        	Issue[] arrIssue = new Issue[issues.size()];
        	arrIssue = issues.toArray(arrIssue);	//Retrieve articles from the knowledge base and stores them in an array

        	SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");	//Format the date for comparison
        	Date date1 = new Date();
        	Date date2 = new Date();

        	for(int i = 0; i < issues.size(); i++)	//loop for size of the array
        	{
        		try 
        		{
		        	date1 = formatter.parse(arrIssue[i].getDateReported());	//Format the date for comparison
		        } 
		        catch (ParseException e)
		        {
		            e.printStackTrace();
		        }

		        for(int j = i + 1; j < (issues.size()); j++)
		        {
		        	try 
	        		{
			        	date2 = formatter.parse(arrIssue[j].getDateReported());	
			        } 
			        catch (ParseException e)
			        {
			            e.printStackTrace();
			        }

			        int result = date1.compareTo(date2);	//compare the dates
			        
			        if(result > 0 && paramDate.equals("oldToNew"))	//If the dates are to be ordered oldest to newest
			        {
			        	swap(i, j, arrIssue);
			        }
			        else if(result < 0 && paramDate.equals("newToOld"))	//If the dates are to be ordered newest to oldest
			        {
			        	swap(i, j, arrIssue);
			        }

		        }
		    }

		    tempList = Arrays.asList(arrIssue);	//Turn the filtered array back into a list

		    session.setAttribute("sortedList", tempList);	//Save the filtered list as a natrribute to be output in the jsp
        		session.setAttribute("sorted", "true");
        }

        tempList = new ArrayList<Issue>();	//Reset the list to be filtered

    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		dispatcher.forward(request, response);
	}
	
	/*
	Precondition: date1 and date2 are integers that reference cells within arrIssue to be swapped. arrIssue is an array that holds Issues
	Postcondition: an issue array is returned with the cells swapped
	*/
	private Issue[] swap(int date1, int date2, Issue[] arrIssue)
	{
		Issue tempIssue = new Issue();

		tempIssue = arrIssue[date1];		//Swap the cells
		arrIssue[date1] = arrIssue[date2];
		arrIssue[date2] = tempIssue;

		return arrIssue;
	}
}
