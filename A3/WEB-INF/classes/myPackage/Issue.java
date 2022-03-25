/*Issue.java
  *Authors: Michael Price, Thomas Miller, Jakeb Pont
   *Student No#s: C3233787, C3279309, C3283210 
   *Date last Modified: 09/06/2019
   *Description: 
   bean which stores all information about an issue
*/

package myPackage;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;
import java.io.Serializable;

public class Issue implements Serializable
{
	private String state;			//Used to track the state of the issue
	private String username;		//The username of the user who reported the issue
	private List<Staff> staff;		//Staff who are attached to this report
	private String category;		//Category this issue fits into
	private String subcategory;		//Subcateogry this issue fits into
	private String dateReported;		//Date the issue was reported
	private String dateResolved;		//Date the issue was solved
	private String issueTitle;		//Title of the Issue
	private String issueDescription;	//Description of the Issue
	private boolean resolved;		//True = Issue is resolved, false = issue is not resolved
	private List<Comment> comments;		//Comments associated with this issue
	private String issueID;			//Randomly generated Issue ID
	
	public Issue()
	{
		
	}

	public String getState()
	{
		return state;
	}

	public void setState(String new_state)
	{
		state = new_state;
	}

	public String getUserName()
	{
		return username;
	}

	public void setUserName(String new_user)
	{
		username = new_user;
	}

	public List<Staff> getStaff()
	{
		return staff;
	}

	public void setStaff(List<Staff> new_staff)
	{
		staff = new_staff;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String new_category)
	{
		category = new_category;
	}

	public String getSubcategory()
	{
		return subcategory;
	}

	public void setSubcategory(String new_subcategory)
	{
		subcategory = new_subcategory;
	}

	public String getDateReported()
	{
		return dateReported;
	}

	public void setDateReported(String new_dateReported)
	{
		dateReported = new_dateReported;
	}

	public String getDateResolved()
	{
		return dateResolved;
	}

	public void setDateResolved(String new_dateResolved)
	{
		dateResolved = new_dateResolved;
	}

	public String getIssueDescription()
	{
		return issueDescription;
	}

	public void setIssueDescription(String new_issueDescription)
	{
		issueDescription = new_issueDescription;
	}

	public String getIssueTitle()
	{
		return issueTitle;
	}

	public void setIssueTitle(String new_issueTitle)
	{
		issueTitle = new_issueTitle;
	}

	public boolean isResolved()
	{
		return resolved;
	}

	public void setResolved(boolean new_resolved)
	{
		resolved = new_resolved;
	}

	public List<Comment> getComments()
	{
		return comments;
	}

	public void setComments(List<Comment> new_comments)
	{
		comments = new_comments;
	}

	public String getIssueID()
	{
		return issueID;
	}

	public void setIssueID(String new_issueID)
	{
		issueID = new_issueID;
	}
}
