/*NewArticle.java
  *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 07/05/2019
   *Description: 
   This is the main program that creates a servlet to run the LoginPage
*/
package myPackage;

import java.time.*;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import java.util.Random;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.annotation.WebServlet;@WebServlet(urlPatterns = {"/NewArticle"})

public class NewArticle extends HttpServlet 
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      RequestDispatcher dispatcher;

      String first = request.getParameter("new");

      if(first == null)	//If the "new" paramter is empty, set to nothing
      {
        first = "error";
      }

      if(first.equals("first"))	//If this is the first access of this page e.g. coming from details.jsp
      {
        HttpSession session = request.getSession();

        GeneralUser person = (GeneralUser) session.getAttribute("generalUser");	//Get current user
        Issue issue = new Issue();
        issue.setUserName(person.getUsername());	//Link username to the new issue	
        issue.setState("New");				//Initilaise state of issue
       
        issue.setResolved(false);

        Date tempDate = new Date();	//Get the current date to find when the date was created
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        String date = formatter.format(tempDate);	//Format the date
        issue.setDateReported(date);

        session.setAttribute("issue", issue);	//Set issue to an attribute to be output on jsp

        dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/details.jsp");
      }
      else	//If the page is accessed by NewArticle.jsp
      {
        String[] strCategory = {"Network", "Software", "Hardware", "Email", "Account"};	//All possible categories and subcategories
        String[] strSubcategory = {"Cannot connect", "Speed", "Constant dropouts", "Other", "Slow to load", "Will not load at all", "Other", "Computer will not turn on", "Computer blue screens", 
                                    "Disk drive", "Peripherals", "Other", "Cannot send", "Cannot recieve", "Spam or Phishing", "Other", "Password reset", "Wrong details", "Other"};

        HttpSession session = request.getSession();
        Issue issue = (Issue) session.getAttribute("issue");	//Retrieve issue

        if(request.getParameter("issueTitle") != null)		//If issueTitle exists
        {
          String tempTitle = request.getParameter("issueTitle");
          issue.setIssueTitle(tempTitle);			//Set title of issue

          String tempDescription = request.getParameter("issueDescription");
          issue.setIssueDescription(tempDescription);		//Set description of issue

          String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   /*Possible characters in security code*/
          String randomString = "";
          int length = 6;
          
          Random rand = new Random();	//Define random object for creating a random issue ID
          
          char[] text = new char[length];
          
          for(int i = 0; i < length; i++) /*Add random characters to the char array*/
          {
            text[i] = characters.charAt(rand.nextInt(characters.length()));
          }
          
          for(int i = 0; i < text.length; i++)  /*add the chars to a string*/
          {
            randomString += text[i];
          }

          issue.setIssueID(randomString);	//Set random issue ID

          session.setAttribute("issue", issue);

          KnowledgeBaseBean kb = (KnowledgeBaseBean) session.getAttribute("kb");
          List<Issue> tempList = kb.getIssues();	//Retrieve issues from the Issues Database in the form of a list
          tempList.add(issue);				//Add the new issue to the list
          kb.setIssues(tempList);			//Set the updated list to the Issues Database
          session.setAttribute("kb", kb);

          dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/viewArticle.jsp");
        }
        else
        {
          String strIssue = request.getParameter("numIssue");
          int numIssue = Integer.valueOf(strIssue);		//Turn the string stored in the parameter to an int
          issue.setSubcategory(strSubcategory[numIssue]);	//Set the subcategory to the matching subcategory in the array of subcategories

          if(numIssue <= 3)				//If the user selected Network as the Category
          {
            issue.setCategory(strCategory[0]);	//Set the category to the macthing category from the category array
          }
          else if(numIssue > 3 && numIssue <= 6)	//If the user selected Software as the Category
          {
            issue.setCategory(strCategory[1]);
          }
          else if(numIssue > 6 && numIssue <= 11)	//If the user selected Hardware as the Category
          {
            issue.setCategory(strCategory[2]);
          }
          else if(numIssue > 11 && numIssue <= 15)	//If the user selected Email as the Category
          {
            issue.setCategory(strCategory[3]);
          }
          else if(numIssue > 15 && numIssue <= 18)	//If the user selected Accountas the Category
          {
            issue.setCategory(strCategory[4]);
          }

          KnowledgeBaseBean kb = (KnowledgeBaseBean) session.getAttribute("kb");
          kb = new KnowledgeBaseBean();
          session.setAttribute("kb", kb);

          session.setAttribute("issue", issue);

          Issue tempIssue = new Issue();
          List<Issue> tempList = new ArrayList<Issue>();

          List<Issue> issues = new ArrayList<Issue>();
          issues = kb.getArticles();	//Retrieve Articles from the Knowledge base and store them in issues

          ListIterator<Issue> iterIssues = issues.listIterator();	//Define a lister iterator to search through the articles

          for(int i = 0; i < issues.size(); i++)	//Loop for the size of the list
          {
            tempIssue = iterIssues.next();	//Next item in the list

            if(tempIssue.getSubcategory().equals(strSubcategory[numIssue]))	//If the subcategory matches the category in the filter
            {
              tempList.add(tempIssue);	//Add to the filtered list
            }
          }
          session.setAttribute("suggestList", tempList);	//set as an attribute to print the filtered list in the jsp

          dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/newArticle.jsp");	//send to the jsp for output
        }
      }

	    dispatcher.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp");
        dispatcher.forward(request, response);
    }
}
