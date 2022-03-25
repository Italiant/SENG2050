/*  
 *  Name: Thomas Miller
 *  Student Number: C3279309
 *  Date: 12/05/2019
 *  Course: SENG2050, Assignment 2
 *  Bean: GameState
*/

package game;

import java.io.*;
import java.util.*;

public class GameState implements Serializable{
	/* default values for the game state */
	private boolean	case1 = false, // false = unopened
		case2 = false,
		case3 = false,
		case4 = false,
		case5 = false,
		case6 = false,
		case7 = false,
		case8 = false,
		case9 = false,
		case10 = false,
		case11 = false,
		case12 = false,
		offerRound = false; // value to tell the interface to display the deal or no deal buttons or not
	private double caseA = 0.50, // case values
		caseB = 1,
		caseC = 10,
		caseD = 100,
		caseE = 200,
		caseF = 500,
		caseG = 1000,
		caseH = 2000,
		caseI = 5000,
		caseJ = 10000,
		caseK = 20000,
		caseL = 50000;
	private String username = "no name"; // username linked to each save
	private double largest = 0.0, bankOffer = 0.0, finalOffer = 0.0; // largest = largest unopened case value
	private String round = "1"; // round number as string 
	private int numOpened = 0, pickCases = 4; // number of cases opened and number of cases to pick
	
	public GameState() // empty constructor
	{
		
	}
	
	public void setUsername(String name)    {username = name;}
	public String getUsername()    			{return username;}
	
	public void setOfferRound(boolean state){offerRound = state;}
	public boolean isOfferRound()			{return offerRound;}
	
	public void setLargest(double value)	{largest = value;}
	public double getLargest()				{return largest;}
	
	public void setBankOffer(double value)	{bankOffer = value;}
	public double getBankOffer()			{return bankOffer;}
	
	public void setFinalOffer(double value)	{finalOffer = value;}
	public double getFinalOffer()			{return finalOffer;}
	
	public void setRound(String value)		{round = value;}
	public String getRound()				{return round;}
	
	public void setNumOpened(int value)		{numOpened = value;}
	public int getNumOpened()				{return numOpened;}
	
	public void setPickCases(int value)		{pickCases = value;}
	public int getPickCases()				{return pickCases;}
	
	public void setCase1(boolean state)		{case1 = state;}
	public void setCase2(boolean state)		{case2 = state;}
	public void setCase3(boolean state)		{case3 = state;}
	public void setCase4(boolean state)		{case4 = state;}
	public void setCase5(boolean state)		{case5 = state;}
	public void setCase6(boolean state)		{case6 = state;}
	public void setCase7(boolean state)		{case7 = state;}	
	public void setCase8(boolean state)		{case8 = state;}	
	public void setCase9(boolean state)		{case9 = state;}	
	public void setCase10(boolean state)	{case10 = state;}	
	public void setCase11(boolean state)	{case11 = state;}	
	public void setCase12(boolean state)	{case12 = state;}
	
	public boolean isCase1()				{return case1;}
	public boolean isCase2()				{return case2;}
	public boolean isCase3()				{return case3;}
	public boolean isCase4()				{return case4;}
	public boolean isCase5()				{return case5;}
	public boolean isCase6()				{return case6;}
	public boolean isCase7()				{return case7;}
	public boolean isCase8()				{return case8;}
	public boolean isCase9()				{return case9;}
	public boolean isCase10()				{return case10;}
	public boolean isCase11()				{return case11;}
	public boolean isCase12()				{return case12;}
	
	public void setCaseA(double value)		{caseA = value;}
	public void setCaseB(double value)		{caseB = value;}
	public void setCaseC(double value)		{caseC = value;}
	public void setCaseD(double value)		{caseD = value;}
	public void setCaseE(double value)		{caseE = value;}
	public void setCaseF(double value)		{caseF = value;}
	public void setCaseG(double value)		{caseG = value;}
	public void setCaseH(double value)		{caseH = value;}
	public void setCaseI(double value)		{caseI = value;}
	public void setCaseJ(double value)		{caseJ = value;}
	public void setCaseK(double value)		{caseK = value;}
	public void setCaseL(double value)		{caseL = value;}
	
	public double getCaseA()				{return caseA;}
	public double getCaseB()				{return caseB;}
	public double getCaseC()				{return caseC;}
	public double getCaseD()				{return caseD;}
	public double getCaseE()				{return caseE;}
	public double getCaseF()				{return caseF;}
	public double getCaseG()				{return caseG;}
	public double getCaseH()				{return caseH;}
	public double getCaseI()				{return caseI;}
	public double getCaseJ()				{return caseJ;}
	public double getCaseK()				{return caseK;}
	public double getCaseL()				{return caseL;}
}