/*  
 *  Name: Thomas Miller
 *  Student Number: C3279309
 *  Date: 12/05/2019
 *  Course: SENG2050, Assignment 2
 *  Bean: Database
*/

package game;

import java.io.*;
import java.util.*;

public class Database implements Serializable
{
	private GameState[] saves = new GameState[20]; // capped at a max of 20 users to be saved in the database
	private int numUsers = 0; // stores the number of users in the databse
	
	public Database() // empty constructor 
	{
		
	}
	
	public void setSave(int index, GameState save)
	{
		saves[index] = save; // add user defined by an ID to the database 
	}
	
	public GameState getSave(int index)
	{
		return saves[index]; // returns that save
	}	
	
	public String getName(int index)
	{
		return saves[index].getUsername(); // uses the getUsername method in GameState to return the username of the save
	}
	
	public void setName(int index, String name) // sets the name of the save at the index
	{
		saves[index].setUsername(name);
	}
	
	public void setNumUser() // increments the number of users in database 
	{
		numUsers++;
	}
	
	public int getNumUser()
	{
		return numUsers;
	}
}