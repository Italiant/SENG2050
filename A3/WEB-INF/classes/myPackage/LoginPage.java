/*LoginPage.java
   *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   first page presented to user to enter username and password
*/
package myPackage;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;@WebServlet(urlPatterns = {"/"}) // left blank so url with parent folder name will redirect here first
public class LoginPage extends HttpServlet 
{
	//main method is called apon navigating to the page with no data
	//creates saveList and data to save the current running game to the savelist
	//then uses print method to display the ui
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		doPost(request, response);
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {	
    		// Method to create a new database if there isnt one already
		UserDatabase data = new UserDatabase(); // make new user database connected to sql to store all users and admins
		List<GeneralUser> general_list = data.getGeneral(); // get list of all general users
		List<Staff> staff_list = data.getStaff(); // get list of all staff members 
		
		if(general_list.size() == 0 || staff_list.size() == 0) // if databse is empty (note this should only run once ever) 
		{
			data.setGeneral();
			data.setStaff();
		}
		
		String returnValue = request.getParameter("returnValue"); // login button
		
		if(returnValue == null)
			returnValue = "error";
		
		if(returnValue.equals("logout"))
		{
			HttpSession session = request.getSession();
			session.invalidate(); // invalidate the session upon logout request
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp"); // redirect to login.jsp page
		dispatcher.forward(request, response);
    }
}
