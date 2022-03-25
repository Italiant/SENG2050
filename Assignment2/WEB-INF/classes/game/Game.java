/*  
 *  Name: Thomas Miller
 *  Student Number: C3279309
 *  Date: 12/05/2019
 *  Course: SENG2050, Assignment 2
 *  Class: Game
*/
package game;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/Game"})
public class Game extends HttpServlet
{	
	private final double[] caseValues = {0.50, 1, 10, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000};
	private GameState cases;
	private Database data = new Database(); // create a new database to store all game states linked by user id 
	private boolean load = false, match = false;
	private String filePath = " ";
	private int index;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{	
		doPost(request, response); // redirect straight to doPost
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		/*                  Always first load in database from file                */
		filePath = getServletConfig().getServletContext().getRealPath("/WEB-INF/database.txt"); // saves in WEB-INF folder of current server location
		try
		{
			FileInputStream file = new FileInputStream(filePath);
			ObjectInputStream input = new ObjectInputStream(file);
			data = (Database)input.readObject();
			input.close();
			file.close();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}		
		
		String newGame = request.getParameter("newGame");
		String username = request.getParameter("username");
		request.setAttribute("winMoney", "false"); // condition to show if user has chosen deal to show win screen 
		
		if(newGame != null || load == true) // if the user has clicked on start new game or load existing game
		{
			if(newGame == null) {newGame = " ";} // prevent null pointer exception
			
			if(newGame.equals("old")) // load existing game
			{
				HttpSession session = request.getSession(); // get current session
				session.invalidate(); // remove any old sessions
				session = request.getSession(); // make new session
				
				load = true;
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/saveGame.jsp");
				dispatcher.forward(request, response); // load save game interface to get a username from the user
			} 
			else if(username != null)
			{
				if(data.getNumUser() != 0) // checks how many users are currently in the database
				{
					load = false;
					match = false;
					index = 1;
					
					for(int i = 1; i <= data.getNumUser(); i++) // searches through the entire database of users
					{
						if(username.equals(data.getName(i))) // if the entered name matches an existing one
						{
							index = i;
							match = true; // raise flag 
						}
					}
					if(match == true)
					{
						HttpSession session = request.getSession();
						session.invalidate();
						
						session = request.getSession();
						
						cases = data.getSave(index); // replaces the current game state with the matched one
						
						request.setAttribute("winMoney", "false");
						session.setAttribute("cases", cases); // sends the current game state bean to the session to be used by the jsp
						
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DoND.jsp");
						dispatcher.forward(request, response); // redirects to the main game interface page
					} 
					else // if there is no user by that name found in the database then redirect back to the main menu 
					{
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
						dispatcher.forward(request, response);
					}
				}	
				else // if there is no users in the database already 
				{
					HttpSession session = request.getSession();
					session.invalidate();
				
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
				
			}
			else // if a usernmae has not been entered, therefore the user has not loaded a game and just clicked on start new game 
			{
				HttpSession session = request.getSession();
				session.invalidate();
				session = request.getSession();
				
				cases = new GameState(); // create a new game state 
				
				/*			Method to randomly add all the money values to the cases 			*/
				LinkedList<Integer> list = new LinkedList<Integer>();
				for (int i = 0; i < 12; i++) 
				{
					list.add(i); // make a list of numbers 0-11
				}
				Collections.shuffle(list); // randomise that list

				// set all case values from the random list
				cases.setCaseA(caseValues[list.get(0)]);
				cases.setCaseB(caseValues[list.get(1)]);
				cases.setCaseC(caseValues[list.get(2)]);
				cases.setCaseD(caseValues[list.get(3)]);
				cases.setCaseE(caseValues[list.get(4)]);
				cases.setCaseF(caseValues[list.get(5)]);
				cases.setCaseG(caseValues[list.get(6)]);
				cases.setCaseH(caseValues[list.get(7)]);
				cases.setCaseI(caseValues[list.get(8)]);
				cases.setCaseJ(caseValues[list.get(9)]);
				cases.setCaseK(caseValues[list.get(10)]);
				cases.setCaseL(caseValues[list.get(11)]);
				
				session.setAttribute("cases", cases);
				
				cases.setRound("1"); // initialise the round 
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DoND.jsp");
				dispatcher.forward(request, response);
			}
		}
		else
		{	
			HttpSession session = request.getSession();
			cases = (GameState)session.getAttribute("cases"); // get current game state from session
			String name = request.getParameter("username"); // get input name if the user has chosen save and exit 
			
			boolean duplicate = false;
			if(name != null) // user has chosen save and exit and has entered a username to check
			{
				if(data.getNumUser() != 0)
				{
					for(int i = 1; i <= data.getNumUser(); i++)
						{
							if(name.equals(data.getName(i))) // if there already exists a save with the entered username 
							{ // replace the current save by the username
								cases.setUsername(name); // set the username of the current state
								data.setSave(i, cases); // save the state to the database identified by a userID
								duplicate = true;
							}
						}
				}
				if(duplicate == false)
				{
					data.setNumUser(); // increment the number of users in the database, this is also the userID
					
					cases.setUsername(name);
					data.setSave(data.getNumUser(), cases);
				}
				
				try // save the current database to file 
				{
					FileOutputStream file = new FileOutputStream(new File(filePath));
					ObjectOutputStream output = new ObjectOutputStream(file);
					output.writeObject(data);	
					output.close();
					file.close();	
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}	
				
				session.invalidate();
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
			
			// save all required session variables before ending the current session and starting a new one
			request.setAttribute("winMoney", "false");
			String openCase = request.getParameter("openCase"); // returned by clicking on a case in main game
			String choice = request.getParameter("choice"); // returned by clicking either deal or no deal in game
			String saveGame = request.getParameter("saveState"); // returned by clicking on save and exit in main game
			session.invalidate();
			
			session = request.getSession();

			if(openCase != null){
				openCases(openCase); // method which decides which case to open/set as true 
			}	
			
			cases.setNumOpened(numOpen()); // updates the total number of cases currently opened 
			cases.setLargest(totalMoneyLeft(true)); // updates the current largest case value still unopened 
			int flag = 0;
				
			switch(cases.getNumOpened()) // switch case for the current number of opened cases
			{
				case 0: // round 1
					cases.setNumOpened(0); // set the number of opened cases to 0
					cases.setRound("1"); // set the round number to display in interface 
					cases.setPickCases(4);
				case 4: // round 2
					cases.setBankOffer(totalMoneyLeft(false) / 8); // update the current offer by the bank as the total amount of unopened money divided by the total number of unopened cases
					cases.setOfferRound(true); // display the deal or no deal buttons in interface 
					cases.setRound("2"); 
					cases.setPickCases(3);
					break;
				case 7: // round 3
					cases.setBankOffer(totalMoneyLeft(false) / 4);
					cases.setOfferRound(true);
					cases.setRound("3");
					cases.setPickCases(2);
					break;
				case 9: // round 4
					cases.setBankOffer(totalMoneyLeft(false) / 3);
					cases.setOfferRound(true);
					cases.setRound("4");
					cases.setPickCases(1);
					break;
				case 10: // round 5
					cases.setBankOffer(totalMoneyLeft(false) / 2);
					cases.setOfferRound(true);
					cases.setRound("5");
					cases.setPickCases(1);
					break;
				case 11: // pick last case
					cases.setOfferRound(true);
					cases.setBankOffer(totalMoneyLeft(false)); // bank offer will be the last unopened case value 
					cases.setFinalOffer(cases.getBankOffer()); // sets the final offer which is the last unopened case value
					choice = "noDeal"; // by default sets the choice to no deal to make user click on the last case 
					cases.setRound("win"); // set the round to win so the interface displays the final message 
					flag = 1;
					break;
				case 12: // last case
					choice = "deal";
					flag = 1;
					break;
				default:
					break;
			}	
			if(choice != null) // the user has clicked on either deal or no deal
			{
				if(choice.equals("noDeal"))
				{
					cases.setOfferRound(false); // remove the deal or no deal buttons and continue the game 
				}
				else if(choice.equals("deal"))
				{
					session.invalidate();
					session = request.getSession();
					request.setAttribute("winMoney", "true"); // display the final money won to interface  
					if(flag == 1){
						cases.setBankOffer(cases.getFinalOffer());
					}
				}
			}
			
			cases.setBankOffer(Math.round(cases.getBankOffer() * 100.0) / 100.0); // rounds the bank offer to 2 decimal places 
				
			session.setAttribute("cases", cases);
				
			if(saveGame != null) // user has clicked on save and exit 
			{
				if(saveGame.equals("save"))
				{
					session.setAttribute("cases", cases);
					
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/saveGame.jsp");
					dispatcher.forward(request, response); // get username to save game in 
				} 
			} 
			else 
			{
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/DoND.jsp");
				dispatcher.forward(request, response);
			}
		}
	}	
	
	/*
	*	Function: openCases
	*	Preconditions: a string with a case number
	*	Postconditions: sets the open status of that case to true 
	*/
	private void openCases(String openCase)
	{
		switch(openCase)
			{
				case "1":
					cases.setCase1(true);
					break;
				case "2":
					cases.setCase2(true);
					break;
				case "3":
					cases.setCase3(true);
					break;
				case "4":
					cases.setCase4(true);
					break;
				case "5":
					cases.setCase5(true);
					break;
				case "6":
					cases.setCase6(true);
					break;
				case "7":
					cases.setCase7(true);
					break;
				case "8":
					cases.setCase8(true);
					break;
				case "9":
					cases.setCase9(true);
					break;
				case "10":
					cases.setCase10(true);
					break;
				case "11":
					cases.setCase11(true);
					break;
				case "12":
					cases.setCase12(true);
					break;
				default:
					break;
			}	
	}
	
	/*
	*	Function: numOpen
	*	Preconditions: none
	*	Postconditions: returns the number of cases which have been opened
	*/
	private int numOpen()
	{
		boolean[] numCases = new boolean[12];
		numCases[0] = cases.isCase1();
		numCases[1] = cases.isCase2();
		numCases[2] = cases.isCase3();
		numCases[3] = cases.isCase4();
		numCases[4] = cases.isCase5();
		numCases[5] = cases.isCase6();
		numCases[6] = cases.isCase7();
		numCases[7] = cases.isCase8();
		numCases[8] = cases.isCase9();
		numCases[9] = cases.isCase10();
		numCases[10] = cases.isCase11();
		numCases[11] = cases.isCase12();
		int counter = 0;
		for(int i = 0; i < numCases.length; i++)
		{
			if(numCases[i] == true)
			{
				counter++;
			}
		}
		return counter;
	}
	
	/*
	*	Function: totalMoneyLeft
	*	Preconditions: a boolean flag to change the return of this function
	*	Postconditions: if flag is false: returns the total amount of money left in all unopened cases 
	*					if flag is true: returns the amount of money in the largest unopened case 
	*/
	private double totalMoneyLeft(boolean largest)
	{
		boolean[] numCases = new boolean[12];
		numCases[0] = cases.isCase1();
		numCases[1] = cases.isCase2();
		numCases[2] = cases.isCase3();
		numCases[3] = cases.isCase4();
		numCases[4] = cases.isCase5();
		numCases[5] = cases.isCase6();
		numCases[6] = cases.isCase7();
		numCases[7] = cases.isCase8();
		numCases[8] = cases.isCase9();
		numCases[9] = cases.isCase10();
		numCases[10] = cases.isCase11();
		numCases[11] = cases.isCase12();
		
		double[] numCaseMoney = new double[12];
		numCaseMoney[0] = cases.getCaseA();
		numCaseMoney[1] = cases.getCaseB();
		numCaseMoney[2] = cases.getCaseC();
		numCaseMoney[3] = cases.getCaseD();
		numCaseMoney[4] = cases.getCaseE();
		numCaseMoney[5] = cases.getCaseF();
		numCaseMoney[6] = cases.getCaseG();
		numCaseMoney[7] = cases.getCaseH();
		numCaseMoney[8] = cases.getCaseI();
		numCaseMoney[9] = cases.getCaseJ();
		numCaseMoney[10] = cases.getCaseK();
		numCaseMoney[11] = cases.getCaseL();
		
		double total = 0.0;
		if(largest == false)
		{
			for(int i = 0; i < 12; i++)
			{
				if(numCases[i] == false)
				{
					total += numCaseMoney[i]; // adds the total money in all unopened cases 
				}
			}
		} else {
			for(int i = 0; i < 12; i++)
			{
				if(numCases[i] == false)
				{
					if(numCaseMoney[i] > total){
						total = numCaseMoney[i]; // updates the total with the largest amount of all cases 
					}
				}
			}
		}
		return total;
	}
}