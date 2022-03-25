/*KnowledgeBase.java
  *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 07/05/2019
   *Description: 
   This is the main program that creates a servlet to run the LoginPage
*/
package myPackage;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.annotation.WebServlet;@WebServlet(urlPatterns = {"/KnowledgeBase"})
public class KnowledgeBase extends HttpServlet 
{
    private List<Issue> tempList = new ArrayList<Issue>();	//Will hold the filtered Article list to be output by the jsp
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        String paramDate = request.getParameter("date");
        String paramCategory = request.getParameter("category");

        KnowledgeBaseBean kb = (KnowledgeBaseBean) session.getAttribute("kb");
        kb = new KnowledgeBaseBean();
        session.setAttribute("kb", kb);	//Set the Knowledge Base as an attribute

      	String returnValue = request.getParameter("returnValue");	//If the return button is pressed
    		if(returnValue == null)
    			returnValue = "error";
    		
    		if(returnValue.equals("knowledge"))
    		{
    			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/knowledgeBase.jsp");
    			dispatcher.forward(request, response);
    		}

       if(paramCategory == null)	//If the category parameter is empty
        {
          paramCategory = "";
        }

          if(!paramCategory.equals(""))	//If the category parameter is not empty
          {
            Issue tempIssue = new Issue();	//Temporary issue used when parsing through a list of issues

            List<Issue> issues = new ArrayList<Issue>();
            issues = kb.getArticles();		//Retrieve the articles stored in the Knowledge Base

            ListIterator<Issue> iterIssues = issues.listIterator();	//Define a list iterator to iterate through the list

            for(int i = 0; i < issues.size(); i++)	//Loop for size of the knowledge base
            {
              tempIssue = iterIssues.next();	//Next issue

              if(tempIssue.getCategory().equals(paramCategory) || paramCategory.equals("All"))	//If the selected Category is the same as the current issue
              {
                tempList.add(tempIssue); //add to the filtered list
              }
            }
            session.setAttribute("sortedList", tempList);	//Set the sorted list as an attribute to be output in the jsp
            session.setAttribute("sorted", "true");		//Set sorted to true so the jsp knows to look for a filtered list
          }

          if(paramDate == null)		//If the date in the parameter is null
          {
            paramDate = "";
          }
          else
          {
            List<Issue> issues = new ArrayList<Issue>();
            issues = tempList;	//Set issues is equal the category filtered list 

            Issue[] arrIssue = new Issue[issues.size()];
            arrIssue = issues.toArray(arrIssue);	//Transform the filtered list into an array

            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");	//Formatter to filter by date
            Date date1 = new Date();	//Dates to be swapped if they are not in order
            Date date2 = new Date();

            for(int i = 0; i < issues.size(); i++)	//loop for the size of issues
            {
              try 
              {
                date1 = formatter.parse(arrIssue[i].getDateReported());		//Retrieve the date of the current issue
              } 
              catch (ParseException e)
              {
                  e.printStackTrace();
              }

              for(int j = i + 1; j < (issues.size()); j++)	//loop for the all of the items in the array
              {
                try 
                {
                  date2 = formatter.parse(arrIssue[j].getDateReported());	//Set date2 to equal the date to be compared
                } 
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                int result = date1.compareTo(date2);	//compare the dates
                
                if(result > 0 && paramDate.equals("oldToNew"))	//If the user selects oldToNew, list the items from oldest to newest
                {
                  swap(i, j, arrIssue);
                }
                else if(result < 0 && paramDate.equals("newToOld"))	//If the user selects oldToNew, list the items from oldest to newest
                {
                  swap(i, j, arrIssue);
                }
              }
          }

          tempList = Arrays.asList(arrIssue);	//Turn the filtered array back to a list

          session.setAttribute("sortedList", tempList);	//Set filtered list as an attribute to be output by the jsp
            session.setAttribute("sorted", "true");
          }

          tempList = new ArrayList<Issue>();

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/knowledgeBase.jsp");
        dispatcher.forward(request, response);
    }
	
	/*
	Precondition: date1 and date2 are integers that point to cells in the array to be swapped. arrIssue is the array that will be changed
	Postcondition: An array with the cells represented by date1 and date2 are swapped.
	*/
    private Issue[] swap(int date1, int date2, Issue[] arrIssue)
    {
      Issue tempIssue = new Issue();

      tempIssue = arrIssue[date1];		//Swap the cells in the array
      arrIssue[date1] = arrIssue[date2];
      arrIssue[date2] = tempIssue;

      return arrIssue;
    }
}
