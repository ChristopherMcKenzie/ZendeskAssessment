package RestController;
import java.io.*;
import Security.Constants;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.zendesk.client.v2.*;
import org.zendesk.client.v2.model.Ticket;
import org.zendesk.client.v2.model.User;
public class TicketController {


	private Zendesk zd;
	private List<Ticket> allTickets;
	
	
	public TicketController() {}
	
	//We use this method so we only have call it once
	public void Init()
	{
		try 
		{
			zd = new Zendesk.Builder(Constants.DOMAIN)
		        .setUsername(Constants.USERNAME)
		        .setToken(Constants.TOKEN)
		        .build();
			
		}
		catch(ZendeskException zendeskE)
		{
			System.out.println(zendeskE.getMessage());
		}

	}
	
	public List<Ticket> GetAllTickets()
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
	
	public String DisplayAllTickets()
	{
		
		String fullDetails = "";
		try
		{
			if(allTickets.size() < 1)
			{
				GetAllTickets();
			}
			//We start at one since we have an empty ticket as the first value we don't want to display that
			//TODO fix this for the better
			for(int i = 1; i < allTickets.size(); i++)
			{
				fullDetails += toString(allTickets.get(i));
			}
		}
		catch(NullPointerException ex)
		{
			return "Error getting the data from your tickets";

		}
		finally
		{
			zd.close();
		}
		return fullDetails;
	}
	
	public String GetSingleTicket(int ticketID)
	{
		//Check if the tickets are empty if they are else fill it up
		//Check if an ID actually
		try
		{
			if(allTickets.isEmpty())
			{
				GetAllTickets();
			}
			//return allTickets.get(ticketID).toString();
			return toString(allTickets.get(ticketID));
		}
		catch(IndexOutOfBoundsException ex)
		{
			return "Error: No tickets of this ID available";
		}
		finally
		{
			zd.close();
		}
		
	}
	
	
	//To string to create a nicely displayed ticket
	public String toString(Ticket ticket)
	{
		try
		{ 
			return  "\nTicketID: " + ticket.getId() + "\n" + "Requester ID: "+ ticket.getRequesterId() + "\n" + "Submitter ID: " + ticket.getSubmitterId() + "\n"  
					+ "Created at: " + ticket.getCreatedAt() + "\n" + "Updated at: " + ticket.getUpdatedAt() + "\n" 
					+ "Subject: " + ticket.getSubject() + "\n" + "Ticket description: " + ticket.getDescription() + "\n";
					
					 
		}
		catch(NullPointerException ex)
		{
			return "Error getting the data from your ticket";
		}
	}
	
}
