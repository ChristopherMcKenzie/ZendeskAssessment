package RestController;
import java.io.*;
import org.asynchttpclient.Response;
import Security.Constants;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.zendesk.client.v2.*;
import org.zendesk.client.v2.model.Ticket;
import org.zendesk.client.v2.model.User;
public class TicketController {


	protected Zendesk zd;
	protected List<Ticket> allTickets;
	
	protected Response response;

	public TicketController() {}
	
	//We use this method so we only have call it once
	public void Init()
	{
		try 
		{ 	
			//to test for some errors change .setToken to .setPassword or similar
			//Run a command of any sort and you should recieve a 401 error
			zd = new Zendesk.Builder(Constants.DOMAIN)
		        .setUsername(Constants.USERNAME)
		        .setToken(Constants.TOKEN)
		        .build();
			
		}
		catch(ZendeskException zendeskE)
		{
			System.out.println(zendeskE.getMessage() + " Seems to been an error conencting to the Zendesk library");	
		}

	}
	
	public List<Ticket> GetAllTickets()
	{
		try
		{
			allTickets = new ArrayList<Ticket>();
			
			//We create a blank ticket so that our loop doesn't start at 0
			//TODO FIND A BETTER FIX FOR THIS
			allTickets.add(new Ticket());
			//Loop through it and get all our tickets
			//Put them into an array list
			
			for (Ticket ticket: zd.getTickets()) {
				allTickets.add(ticket);
			}		
			
			return allTickets;
		}	
		catch(ZendeskResponseException ze)
		{
			GetResponseDetails(ze);
		}
		catch(NullPointerException npe)
		{
			System.out.println("NullPointerException: Details are missing");
		}
		catch(IndexOutOfBoundsException ibe)
		{
			System.out.println("Something went wrong with the list try again.");
		}
		return allTickets;
	}
	
	public String DisplayAllTickets(int pageNumber)
	{
		try
		{
			GetAllTickets();
			//We start at one since we have an empty ticket as the first value we don't want to display that
			//TODO fix this for the better
			//make a sublist
			//when command next or previous is called change the values in the sublist
			return Pagination(pageNumber);
		}
		catch(ZendeskResponseException ze)
		{
			System.out.println(GetResponseDetails(ze));
		}
		catch(NullPointerException npe)
		{
			System.out.println("NullPointerException: Details are missing");
		}
		catch(IndexOutOfBoundsException ibe)
		{
			System.out.println("Something went wrong with the list try again.");
		}
		return "No details could be gathered here try again";
	}
	
	public String GetSingleTicket(int ticketID)
	{
		//Check if the tickets are empty if they are else fill it up
		//Check if an ID actually
		try
		{
			GetAllTickets();
			//return allTickets.get(ticketID).toString();
			return toString(allTickets.get(ticketID));
		}
		catch(ZendeskResponseException ze)
		{
			System.out.println(GetResponseDetails(ze));
		}
		catch(NullPointerException npe)
		{
			System.out.println("NullPointerException: Details are missing");
		}
		catch(IndexOutOfBoundsException ibe)
		{
			System.out.println("Something went wrong with the list try again.");
		}
		return "No details could be gathered here try again";
		
	}
	
	
	//To string to create a nicely displayed ticket
	public String toString(Ticket ticket)
	{
		try
		{ 
			return  "\r\nTicketID: " + ticket.getId() + "\r\n" + "Requester ID: "+ ticket.getRequesterId() + "\r\n" + "Submitter ID: " + ticket.getSubmitterId() + "\r\n"  
					+ "Created at: " + ticket.getCreatedAt() + "\r\n" + "Updated at: " + ticket.getUpdatedAt() + "\r\n" 
					+ "Subject: " + ticket.getSubject() + "\r\n" + "Ticket description: " + ticket.getDescription() + "\r\n";
					
					 
		}
		catch(NullPointerException ex)
		{
			return "Error getting the data from your ticket";
		}
	}
	
	//Lets use page through the details
	public String Pagination(int pageNumber)
	{
		String fullDetails = "";
		
		int ticketsPerPage = 25;
		
		int to = pageNumber * ticketsPerPage + 1;
		int from = to - ticketsPerPage;
		
		
		for(int i = from; i < to; i++)
		{
			fullDetails += toString(allTickets.get(i));
		}
		return fullDetails;
	}

	
	//We'll use this when get a response exception
	//We create a seperate method to find the most common errors as it's less time consuming
	//in a larger scale project we'd have this in a seperate security class so as not to duplicate this across any api calls
	public String GetResponseDetails(ZendeskResponseException zre)
	{
		switch(zre.getStatusCode())
		{
			case 400:
				return (zre.getStatusCode() + " "+ zre.getStatusText() + " There seems to be a syntax error try again.");
			case 401:
				return (zre.getStatusCode() + " "+ zre.getStatusText() + " Unauthorised access, log in details are incorrect.");
			case 403:
				return (zre.getStatusCode() + " "+ zre.getStatusText() + " You dont not have permission to view this information.");
			case 404:
				return (zre.getStatusCode() + " "+ zre.getStatusText() + " This information cannot be found try again later.");
			case 500:
				return (zre.getStatusCode() + " "+ zre.getStatusText() + " There is an error on the server try again.");
			case 501:
				return (zre.getStatusCode() + " "+ zre.getStatusText() + " This method does exist check to make sure it's spelled correctly.");
			default:
				return (zre.getStatusCode() + " "+ zre.getStatusText() + " Unknown error contact support.");
		}
	}

}
