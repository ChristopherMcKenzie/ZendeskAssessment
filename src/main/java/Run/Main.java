package Run;

import java.util.InputMismatchException;
import java.util.Scanner;

import RestController.*;
import picocli.CommandLine;
import picocli.CommandLine.*;


public class Main{
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		
		TicketController tc = new TicketController();
		tc.Init();

		
		System.out.print("Welcome to Zendesk Assessment.\r\nSelect a command to continue\r\nValid Commands\r\n"
				+ "All Tickets\r\n"
				+ "Single Ticket\r\n"
				+ "Exit - allows you end the program\r\n");
		
		int ticketNum;
		int pageNum = 1;
		boolean flag = true;
		boolean allTicketsFlag = true;
		boolean singleTicketFlag = true;

		do
		{
			//Bug here where when the loop restarts using "back" it takes in a command and brings it down to default
			//the issue is "nextLine()" should be next but that casuses other issues
			
			String input = sc.nextLine().toLowerCase().trim();

			switch(input)
			{
				case "all tickets":			

					System.out.println(tc.DisplayAllTickets(pageNum));
					System.out.println("Next/Prev to change pages\r\nBack to leave this menu");

					input = sc.next().toLowerCase().trim();

					do
					{
						switch(input)
						{							
							case "next":
								pageNum += 1;
								if(pageNum > 4)
								{
									System.out.println("Cannot go above 4 page(s)");
									pageNum = 4;
								}
								System.out.println(tc.DisplayAllTickets(pageNum));
								System.out.println("Next/Prev to change pages\r\nBack to leave this menu");
								input = sc.next();
								
								break;
								
							case "prev":
								pageNum -= 1;
								if(pageNum < 1)
								{
									System.out.println("Cannot go below 1 page(s)");
									pageNum = 1;
								}
								System.out.println(tc.DisplayAllTickets(pageNum));
								System.out.println("Next/Prev to change pages\r\nBack to leave this menu");
								input = sc.next();

								break;

								
							case "back":
								allTicketsFlag = false;
								break;
								
							case "default":
								System.out.println("Unknown command please select a valid command\n Next, Prev, Back are the only valid commands");	
								input = sc.next();
								break;
						
						}
					}while(allTicketsFlag);
						
					
					break;
					
				case "single ticket":
					System.out.println("Select a ticket number");
					ticketNum = sc.nextInt();
					System.out.println(tc.GetSingleTicket(ticketNum));
					
					do
					{
						
						System.out.println("Select another ticket or go back");
						
						input = sc.next();

						if(input.equals("back"))
							break;	
						
						//We check if the input cna be parsed as an int if so it should be bigger than the samllest possible value
						else if(Integer.parseInt(input) > Integer.MIN_VALUE)
							System.out.println(tc.GetSingleTicket(Integer.parseInt(input)));
						
						else
							System.out.println("Command not avaiable try again");
					}while(singleTicketFlag);
					break;
					
				case "exit":
					System.out.println("Program will now end");
					sc.close();
					System.exit(0);
					break;

				default:
					System.out.println("Unknown command please select a valid command");	
					System.out.println("Select a command to continue\nValid Commands\r\n" + 
							"All Tickets\r\n" + 
							"Single Ticket\r\n" + 
							"Exit - allows you end the program");
					
					break;
			}
		}while(flag);
		

	}

}
