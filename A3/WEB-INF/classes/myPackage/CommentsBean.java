/*CommentsBean.java
  *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   This bean uses Sql to store all comments into a table
*/
package myPackage;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class CommentsBean implements Serializable
{
	private List<Comment> comments;
	
	//preCondition: none
	//postCondition: retrievs comments list from sql table
	public List<Comment> getComments()
	{
		comments = new ArrayList<Comment>();

		String query = "SELECT * FROM Comments";
		
		Connection connection = null;
		Statement statement = null;
		ResultSet dataTables = null;
		ResultSet result = null;

		try
		{
			//setting up connection
			connection = Config.getConnection(); 
			statement = connection.createStatement(); 
	
			//If table exsists return table
			//else return empty list
			DatabaseMetaData data = connection.getMetaData();
			dataTables = data.getTables(null, null, "Comments", null);
			if(!dataTables.next())
			{
				statement.execute("CREATE TABLE Comments (issueID VARCHAR(100),commentInfo VARCHAR(100), username VARCHAR(20), commentDate VARCHAR(100), solution BOOLEAN, commentID VARCHAR(20));");
			}

			result = statement.executeQuery(query); //executes create table query
	
			//cycles throught table and makes comments to be added to a list and returned 
			while(result.next())
			{ 
				Comment comment = new Comment();
				comment.setIssueID(result.getString(1));
				comment.setCommentInfo(result.getString(2));
				comment.setUsername(result.getString(3));
				comment.setCommentDate(result.getString(4));
				comment.setSolution(result.getBoolean(5));
				comment.setCommentID(result.getString(6));

				comments.add(comment);
			}
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
			e.printStackTrace();
		}
		finally
		{	
			try{ if(dataTables != null) dataTables.close();}catch(Exception e){};
			try{ if(statement != null) statement.close();}catch(Exception e){};
			try{ if(result != null) result.close();}catch(Exception e){};
			try{ if(connection != null) connection.close();}catch(Exception e){};
		}

		return comments;
	}
	
	
	//preCondition: none
	//postCondition: sets Sql table to passed list 
	public void setComments(List<Comment> new_comments)
	{
		Connection connection = null;
		Statement statement = null;

		try
		{
			//setting up connection
			connection = Config.getConnection(); 
			statement = connection.createStatement();
			//drops current table to be replaced with new comments list
			statement.execute("DROP TABLE Comments;");
			statement.execute("CREATE TABLE Comments (issueID VARCHAR(100), commentInfo VARCHAR(100), username VARCHAR(20), commentDate VARCHAR(100), solution BOOLEAN, commentID VARCHAR(20));");

			String query = "INSERT INTO Comments VALUES";

			ListIterator<Comment> iter = new_comments.listIterator();
			Comment comment = new Comment();
			//Loops through list and makes new insert calls to sql
			while(iter.hasNext())
			{
				comment = iter.next();

				String solution = String.valueOf(comment.isSolution());
				solution = solution.toUpperCase();

			 	query +=  "(\'";
			 	query += comment.getIssueID() + "\', \'";
				query += comment.getCommentInfo() + "\', \'";
				query += comment.getUsername() + "\', \'";
				query += comment.getCommentDate() + "\', ";
				query += solution + ", \'";
				if(iter.hasNext())
					query += comment.getCommentID() + "\'),";
				else
					query += comment.getCommentID() + "\');";
			}
			if(new_comments.size() != 0)	//if the pased list is empty then it won't execute the query
				statement.execute(query);
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
			e.printStackTrace();
		}
		finally
		{	
			try{ if(statement != null) statement.close();}catch(Exception e){};
			try{ if(connection != null) connection.close();}catch(Exception e){};
		}
		comments = new_comments;
	}
}
