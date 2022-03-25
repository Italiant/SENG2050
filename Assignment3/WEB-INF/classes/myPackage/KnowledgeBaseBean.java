/*CommentsBean.java
  *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   This bean uses Sql to store all Issues/ Articles into a table
   Where Issues are unResolved Articles
*/
package myPackage;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class KnowledgeBaseBean implements Serializable
{
	private List<Issue> articles;
	private List<Issue> issues;
	
	//bean constructor
	public KnowledgeBaseBean()
	{

	}
	
	//preCondition: none
	//postCondition: returns list of all articles in the KnowledgeBase
	public List<Issue> getArticles()
	{
		articles = new ArrayList<Issue>();

		String query = "SELECT * FROM KnowledgeBase";
		
		Connection connection = null;
		Statement statement = null;
		ResultSet dataTables = null;
		ResultSet result = null;

		try
		{
			//setting up connection
			connection = Config.getConnection(); 
			statement = connection.createStatement(); 

			DatabaseMetaData data = connection.getMetaData();
			dataTables = data.getTables(null, null, "KnowledgeBase", null);
			if(!dataTables.next())
			{
				statement.execute("CREATE TABLE KnowledgeBase (state VARCHAR(30), username VARCHAR(20), category VARCHAR(20), subcategory VARCHAR(30), dateReported VARCHAR(100), dateResolved VARCHAR(100), issueTitle VARCHAR(60), issueDescription VARCHAR(200), resolved BOOLEAN, issueID VARCHAR(6));");
			}
			
			//if table dosn't exsist in SQL the statement isn't run
			result = statement.executeQuery(query); 

			while(result.next())
			{ 
				Issue issue = new Issue();
				issue.setState(result.getString(1));
				issue.setUserName(result.getString(2));
				issue.setCategory(result.getString(3));
				issue.setSubcategory(result.getString(4));
				issue.setDateReported(result.getString(5));
				issue.setDateResolved(result.getString(6));
				issue.setIssueTitle(result.getString(7));
				issue.setIssueDescription(result.getString(8));
				issue.setResolved(result.getBoolean(9));
				issue.setIssueID(result.getString(10));

				CommentsBean commentsbean = new CommentsBean();
				List<Comment> cList1 = commentsbean.getComments();
				List<Comment> cList2 = new ArrayList<Comment>();
				//Give issue its comments and only its comments;
				ListIterator<Comment> iter = cList1.listIterator();
				Comment c1 = new Comment();
	
				//grabs comments from the comments bean that have matching issueIDs
				while(iter.hasNext())
				{ 
					c1 = iter.next();
					if(c1.getIssueID().equals(issue.getIssueID()))
					{ 
						cList2.add(c1);
					}
				}

				issue.setComments(cList2);

				articles.add(issue);
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

		return articles;
	}
	
	//preCondition: none
	//postCondition: sets SQL table for Articles to passed list
	public void setArticles(List<Issue> new_articles)
	{
		Connection connection = null;
		Statement statement = null;

		try
		{
			//setting up connection
			connection = Config.getConnection(); 
			statement = connection.createStatement();
			
			//drops table then makes new table to be refilled with new list
			statement.execute("DROP TABLE KnowledgeBase;");
			statement.execute("CREATE TABLE KnowledgeBase (state VARCHAR(30), username VARCHAR(20), category VARCHAR(20), subcategory VARCHAR(30), dateReported VARCHAR(100), dateResolved VARCHAR(100), issueTitle VARCHAR(60), issueDescription VARCHAR(200), resolved BOOLEAN, issueID VARCHAR(6));");
			String query = "INSERT INTO KnowledgeBase VALUES";

			ListIterator<Issue> iter = new_articles.listIterator();
			Issue article = new Issue();
			while(iter.hasNext())
			{
				article = iter.next();

				String resolved = String.valueOf(article.isResolved());
				resolved = resolved.toUpperCase();

			 	query +=  "(\'";
				query += article.getState() + "\', \'";
				query += article.getUserName() + "\', \'";
				query += article.getCategory() + "\', \'";
				query += article.getSubcategory() + "\', \'";
				query += article.getDateReported() + "\', \'";
				query += article.getDateResolved() + "\', \'";
				query += article.getIssueTitle() + "\', \'";
				query += article.getIssueDescription() + "\', ";
				query += resolved + ", \'";
				if(iter.hasNext())
					query += article.getIssueID() + "\'),";
				else
					query += article.getIssueID() + "\');";
			}
			if(new_articles.size() != 0)//if empty don't run 
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

		articles = new_articles;
	}
	
	//preCondition: none
	//postCondition: returns list of all Issues in the KnowledgeBase
	public List<Issue> getIssues()
	{
		issues = new ArrayList<Issue>();

		String query = "SELECT * FROM IssuesBase";
		
		Connection connection = null;
		Statement statement = null;
		ResultSet dataTables = null;
		ResultSet result = null;

		try
		{	
			//setting up connection
			connection = Config.getConnection(); //step 1
			statement = connection.createStatement(); //step 2
			DatabaseMetaData data = connection.getMetaData();
			dataTables = data.getTables(null, null, "IssuesBase", null);
			if(!dataTables.next())
			{
				statement.execute("CREATE TABLE IssuesBase (state VARCHAR(30), username VARCHAR(20), category VARCHAR(20), subcategory VARCHAR(30), dateReported VARCHAR(100), dateResolved VARCHAR(100), issueTitle VARCHAR(60), issueDescription VARCHAR(200), resolved BOOLEAN, issueID VARCHAR(6));");
			}
			dataTables.close();

			result = statement.executeQuery(query);
			while(result.next())
			{ 
				Issue issue = new Issue();
				issue.setState(result.getString(1));
				issue.setUserName(result.getString(2));
				issue.setCategory(result.getString(3));
				issue.setSubcategory(result.getString(4));
				issue.setDateReported(result.getString(5));
				issue.setDateResolved(result.getString(6));
				issue.setIssueTitle(result.getString(7));
				issue.setIssueDescription(result.getString(8));
				issue.setResolved(result.getBoolean(9));
				issue.setIssueID(result.getString(10));

				CommentsBean commentsbean = new CommentsBean();
				List<Comment> cList1 = commentsbean.getComments();
				List<Comment> cList2 = new ArrayList<Comment>();
				//Give issue its comments and only its comments;
				ListIterator<Comment> iter = cList1.listIterator();
				Comment c1 = new Comment();

				while(iter.hasNext())
				{ 
					c1 = iter.next();
					if(c1.getIssueID().equals(issue.getIssueID()))
					{ 
						cList2.add(c1);
					}
				}

				issue.setComments(cList2);

				issues.add(issue);
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
			e.printStackTrace();
		}
		finally
		{	
			try{ if(dataTables != null) dataTables.close();}catch(Exception e){};
			try{ if(statement != null) statement.close();}catch(Exception e){};
			try{ if(result != null) result.close();}catch(Exception e){};
			try{ if(connection != null) connection.close();}catch(Exception e){};
		}
		return issues;
	}
	
	//preCondition: none
	//postCondition: sets SQL table for Issues to passed list
	public void setIssues(List<Issue> new_issues)
	{
		Connection connection = null;
		Statement statement = null;

		try
		{
			//setting up conection
			connection = Config.getConnection();
			statement = connection.createStatement();
			
			//Drops table to be remade with new list
			statement.execute("DROP TABLE IssuesBase;");
			statement.execute("CREATE TABLE IssuesBase (state VARCHAR(30), username VARCHAR(20), category VARCHAR(20), subcategory VARCHAR(30), dateReported VARCHAR(100), dateResolved VARCHAR(100), issueTitle VARCHAR(60), issueDescription VARCHAR(200), resolved BOOLEAN, issueID VARCHAR(6));");
			String query = "INSERT INTO IssuesBase VALUES";
			ListIterator<Issue> iter = new_issues.listIterator();
			Issue issue = new Issue();
			
			while(iter.hasNext())
			{
				issue = iter.next();

				String resolved = String.valueOf(issue.isResolved());
				resolved = resolved.toUpperCase();

			 	query +=  "(\'";
				query += issue.getState() + "\', \'";
				query += issue.getUserName() + "\', \'";
				query += issue.getCategory() + "\', \'";
				query += issue.getSubcategory() + "\', \'";
				query += issue.getDateReported() + "\', \'";
				query += issue.getDateResolved() + "\', \'";
				query += issue.getIssueTitle() + "\', \'";
				query += issue.getIssueDescription() + "\', ";
				query += resolved + ", \'";
				if(iter.hasNext())
					query += issue.getIssueID() + "\'),";
				else
					query += issue.getIssueID() + "\');";
			}
			if(new_issues.size() != 0) //don't run if list is empty
				statement.executeUpdate(query);					// Removed setting comments as that is already handled when a comment is created

		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{	
			try{ if(statement != null) statement.close();}catch(Exception e){};
			try{ if(connection != null) connection.close();}catch(Exception e){};
		}
		issues = new_issues;
	}
}
