/*Comment.java
   *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   This Class is a simplly holds information for a comment
   While it looks similar to a bean it isn't used as one
*/
package myPackage;

import java.util.Date;

public class Comment
{
	private String issueID;
	private String commentInfo;
	private String username;
	private String commentDate;
	private String commentID;
	private boolean solution;

	public Comment()
	{

	}
	
	//Methods to get And Set Comments variables
	public String getCommentID()
	{
		return commentID;
	}

	public void setCommentID(String new_commentID)
	{
		commentID = new_commentID;
	}

	public String getIssueID()
	{
		return issueID;
	}

	public void setIssueID(String new_issueID)
	{
		issueID = new_issueID;
	}

	public String getCommentInfo()
	{
		return commentInfo;
	}

	public void setCommentInfo(String new_commentInfo)
	{
		commentInfo = new_commentInfo;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String new_username)
	{
		username = new_username;
	}

	public String getCommentDate()
	{
		return commentDate;
	}

	public void setCommentDate(String new_commentDate)
	{
		commentDate = new_commentDate;
	}

	public boolean isSolution()
	{
		return solution;
	}

	public void setSolution(boolean new_solution)
	{
		solution = new_solution;
	}
}
