/*ViewArticle.java
  *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   Methods to view an article for both an issue and one in the knowledge base
*/
package myPackage;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;@WebServlet(urlPatterns = {"/ViewArticle"})
public class ViewArticle extends HttpServlet 
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {		
    	HttpSession session = request.getSession();

    		String value = request.getParameter("issue"); // issue object
	 	String newComment = request.getParameter("commentInfo"); // not null if someone has posted a new comment 
		String commentSolution = request.getParameter("solution"); // ID of solution comment
		String State = request.getParameter("state"); // either reporter or third party
		String Send = request.getParameter("send"); // button to send issue to knowledgebase
		String Choice = request.getParameter("choice"); // either accept or reject
		
		if(Choice != null) // sets the state of the issue to eithe resolved or not accepted
		{
			Issue issue = (Issue) session.getAttribute("issue"); //grab current issue to set state
			if(Choice.equals("accept"))
			{
				issue.setState("Resolved");
			}
			else if(Choice.equals("reject"))
			{
				issue.setState("Not_Accepted");
				issue.setResolved(false);
			}
			overwriteIssue(issue, session); // adds updated issue to database
			session.setAttribute("issue", issue);
		}
		else if(Send != null) // sends issue to knowledgebase
		{
			if(Send.equals("true"))
			{
				Issue issue = (Issue) session.getAttribute("issue"); //grab current issue
				KnowledgeBaseBean kb = (KnowledgeBaseBean) session.getAttribute("kb");
				List<Issue> list = kb.getIssues();
				ListIterator<Issue> listIter = list.listIterator();
				Issue temp;

				while(listIter.hasNext()) //cycles through and removes current Issue
				{ 
					temp = listIter.next();
					if(temp.getIssueID().equals(issue.getIssueID()))
					{
						listIter.remove();
						break;
					}
				}
				kb.setIssues(list);
				
				List<Issue> list2 = kb.getArticles();
				
				Date tempDate = new Date();	//sets resolved Date of issue
				SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
				String date = formatter.format(tempDate);
				issue.setDateResolved(date);
				
				list2.add(issue);	//adds to knowledgeBase as article
				kb.setArticles(list2);
			}
		}
		else if(State != null)
		{
			Issue issue = (Issue) session.getAttribute("issue"); //grab current issue
			if(State.equals("reporter"))
			{
				issue.setState("Waiting on Reporter");	//setting state of issue
			}
			else if(State.equals("party"))
			{
				issue.setState("Waiting on Third Party");
			}
			overwriteIssue(issue, session);
			session.setAttribute("issue", issue);
		}
		else if(commentSolution != null)	//if passed a solution (comment)
		{
           		 Issue issue = (Issue) session.getAttribute("issue"); //grab current issue
			CommentsBean beanCom = new CommentsBean();
			Comment comTemp = new Comment();
			
            		List<Comment> comlist = new ArrayList<Comment>();
			List<Comment> comlist2 = new ArrayList<Comment>();
			comlist = beanCom.getComments();
			
			ListIterator<Comment> iterCom = comlist.listIterator();
			
			for(int i = 0; i < comlist.size(); i++)
			{
				comTemp = iterCom.next();
				
				if(comTemp.getCommentID().equals(commentSolution)) //if comment id = passed value
				{
					comTemp.setSolution(true);
					issue.setResolved(true);
					issue.setState("Completed");	//set resolved and state
				}
				else if (comTemp.getIssueID().equals(issue.getIssueID()))//else if comment issue Id = current Issue ID set Solution false
				{
				    comTemp.setSolution(false);
				}
				comlist2.add(comTemp); //add back to list 
			}
			beanCom.setComments(comlist2);
			
			ListIterator<Comment> iterCom2 = comlist2.listIterator();
			
			List<Comment> comlist3 = new ArrayList<Comment>();
			
			Comment tempComment = new Comment();
			
			for(int i = 0; i < comlist2.size(); i++) //cycle through comments and add them if them to issue 
			{
				tempComment = iterCom2.next();
				if(issue.getIssueID().equals(tempComment.getIssueID()))
				{
					comlist3.add(tempComment);
				}
			}
			issue.setComments(comlist3);
			overwriteIssue(issue, session);
			session.setAttribute("issue", issue);
			
		}
        else if(newComment != null)	//if someone has entered a new comment
        {
            Issue issue = (Issue) session.getAttribute("issue"); //grab current issue
        	Comment temp = new Comment();
        	Date tempDate = new Date();
            String date = String.valueOf(tempDate);
        	GeneralUser person = (GeneralUser) session.getAttribute("generalUser"); //get current user
        	Staff staff = (Staff) session.getAttribute("staffUser"); //get current staff
			
        	temp.setCommentInfo(newComment);		//set comment data
        	if(person.getUsername() != null)
        		temp.setUsername(person.getUsername());
        	else
            {
        		temp.setUsername(staff.getUsername());    //If Staff sets a comment change to in progress
                issue.setState("In Progress");
            }
        	temp.setCommentDate(date);

            //Random id generator
             String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   /*Possible characters in security code*/
              String randomString = "";
              int length = 6;
              
              Random rand = new Random();
              
              char[] text = new char[length];
              
              for(int i = 0; i < length; i++) /*Add random characters to the char array*/
              {
                text[i] = characters.charAt(rand.nextInt(characters.length()));
              }
              
              for(int i = 0; i < text.length; i++)  /*add the chars to a string*/
              {
                randomString += text[i];
              }

              temp.setCommentID(randomString);		//Set comment ID to a random String

        	//get Issue was here
            temp.setIssueID(issue.getIssueID());

        	List<Comment> tempList = issue.getComments();	//add to list
        	if(tempList == null)
        		tempList = new LinkedList<Comment>();
        	tempList.add(temp);
            //
            CommentsBean bean = new CommentsBean();
            List<Comment> comlist = bean.getComments();	//Define a list of comments
            comlist.add(temp);	//add temporary comment
            bean.setComments(comlist);	//Set the list of comments back to the bean
            //
        	issue.setComments(tempList);	//Set the list of comments to the temporary issue list

        	session.setAttribute("issue", issue);
            overwriteIssue(issue, session);
        }
        else if(!value.equals("new")) // searched for issue in knowledgebase and send to session
        { 
        	Issue issue = searchKbforIssue(session, request, value);
        	session.setAttribute("issue", issue);	//Set issue as an attribute to be output by the jsp
        }       
		
		RequestDispatcher dispatcher = null;
		if(Send != null)
			dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/index.jsp");
		else
			dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/viewArticle.jsp");
		dispatcher.forward(request, response);
    }
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp");
        dispatcher.forward(request, response);
    }
	
	/*
	Precondition: String id is the id of the issue
	Postcondition: returns an issue from the database that matches id
	*/
    private Issue searchKbforIssue(HttpSession session, HttpServletRequest request, String id)
    {
    	KnowledgeBaseBean kb = (KnowledgeBaseBean) session.getAttribute("kb");
    	List<Issue> list = kb.getIssues();	//retrieves the issues from the knowledge base

    	ListIterator<Issue> listIter = list.listIterator();	//Defines an iterator to look through the issues
    	Issue temp;
    	Issue returnValue = null;

    	while(listIter.hasNext())	//loop for all of the issues. 
    	{ 
    		temp = listIter.next();
    		if(temp.getIssueID().equals(id))	//If the issues match
    		{
    			returnValue = temp; //set returnValue to the issue
    		}
    	}
        if(returnValue == null)	//If not found, repeat for articles
        {
            List<Issue> list2 = kb.getArticles();
            ListIterator<Issue> listIter2 = list2.listIterator();
            while(listIter2.hasNext())
            { 
                temp = listIter2.next();
                if(temp.getIssueID().equals(id))
                {
			request.setAttribute("article", "sent");
                    	returnValue = temp; 
                }
            }
        }
    	return returnValue;
    }

	/*
	Precondition: issue is of type Issue to replace the old issue with a matching id
	Postcondition:	N/A
	*/
    private void overwriteIssue(Issue issue, HttpSession session) 
    {
        KnowledgeBaseBean kb = (KnowledgeBaseBean) session.getAttribute("kb");
        List<Issue> list = kb.getIssues();	//retrieves issues from knowledge base
        ListIterator<Issue> listIter = list.listIterator();
        Issue temp;

        while(listIter.hasNext())	//iterates through issues
        { 
            temp = listIter.next();
            if(temp.getIssueID().equals(issue.getIssueID()))	//If finds matching id, replace old issue with new issue
            {
                listIter.remove();
                list.add(issue);
                break;
            }
        }
        kb.setIssues(list);	//Sets new issue list back to the database
    }
}
