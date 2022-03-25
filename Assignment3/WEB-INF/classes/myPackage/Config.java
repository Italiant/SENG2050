/*Config.java
  *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   Config creates the connection to the sql database defined by the context.xml file
*/
package myPackage;

import javax.sql.*;
import java.sql.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Config 
{
	//preCondition: none
	//postCondition: returns a connection to a SQL database
	public static Connection getConnection() throws NamingException, SQLException 
	{
		try 
		{
			DataSource datasource = (DataSource)new InitialContext().lookup("java:/comp/env/assignment3");
			Connection connection = datasource.getConnection();
			return connection;
		}
		catch (NamingException ne) 
		{
			System.err.println(ne.getMessage());
			System.err.println(ne.getStackTrace());
			throw ne;
		}
		catch (SQLException sqle) 
		{
			System.err.println(sqle.getMessage());
			System.err.println(sqle.getStackTrace());
			throw sqle;
		}
	}
}
