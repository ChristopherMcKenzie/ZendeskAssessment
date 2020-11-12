package Run;

import java.util.Scanner;

import RestController.*;
import picocli.CommandLine;
import picocli.CommandLine.*;

@Command(name = "ASCIIArt", version = "ASCIIArt 1.0", mixinStandardHelpOptions = true) 

public class Main implements Runnable{
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		
		TicketController tc = new TicketController();
		tc.Init();
	//	tc.GetAllTickets();
		//System.out.println(tc.DisplayAllTickets(1));
		
		System.out.print("Welcome to Zendesk Assessment.\nSelect a command to continue\nValid Commands\n"
				+ "All Tickets\n"
				+ "Single Ticket\n"
				+ "Exit - allows you end the program");
		
		int ticketNum;
		int pageNum = 1;
		boolean flag = true;
		boolean flag2 = true;

		do
		{
			String input = sc.nextLine().toLowerCase().trim();

			switch(input)
			{
				case "alltickets":			

					System.out.println(tc.DisplayAllTickets(pageNum));
					System.out.println("Next/Prev to change pages");

					input = sc.nextLine();

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
									System.out.println(tc.DisplayAllTickets(pageNum));

									input = sc.nextLine();
									break;
								}
								System.out.println(tc.DisplayAllTickets(pageNum));
								System.out.println("Next/Prev to change pages\nBack to leave this menu");
								input = sc.nextLine();
								
								break;
								
							case "prev":
								pageNum -= 1;
								if(pageNum < 1)
								{
									System.out.println("Cannot go below 1 page(s)");
									pageNum = 1;
									System.out.println(tc.DisplayAllTickets(pageNum));
									input = sc.nextLine();

									break;
								}
								System.out.println(tc.DisplayAllTickets(pageNum));
								System.out.println("Next/Prev to change pages\nBack to leave this menu");
								input = sc.nextLine();

								break;

								
							case "back":
								System.out.print("Welcome to Zendesk Assessment.\nSelect a command to continue."
										+ "Valid Commands:\n"
										+ "All Tickets\n"
										+ "Single Ticket\n"
										+ "To change pages select a number 1-4\n"
										+ "Exit - allows you end the program");
								flag2 = false;
								break;
								
							case "default":
								System.out.println("Unknown command please select a valid command\n Next, Prev, Back are the only valid commands");	
								input = sc.nextLine();
								break;
						
						}
					}while(flag2);
						
					
					break;
					
				case "singleticket":
					System.out.println("Select a ticket number");
					ticketNum = sc.nextInt();
					System.out.println(tc.GetSingleTicket(ticketNum));
					break;
					
				case "exit":
					System.out.println("Program will now end");
					sc.close();
					System.exit(0);
					break;
				
					//brings user back to the main menu
				case "back":
					System.out.print("Welcome to Zendesk Assessment.\nSelect a command to continue."
							+ "Valid Commands:\n"
							+ "All Tickets\n"
							+ "Single Ticket\n"
							+ "To change pages select a number 1-4\n"
							+ "Exit - allows you end the program");
					flag = false;
					
					break;
					
					
				default:
					System.out.println("Unknown command please select a valid command");	
					System.out.println("Select a command to continue\nValid Commands\r\n" + 
							"All Tickets\r\n" + 
							"Single Ticket\r\n" + 
							"To change pages select a number 1-4\r\n" + 
							"Exit - allows you end the program");
					
					break;
			}
		}while(flag);
		

	}
	//TODO move INIT() Into here
	@Option(names = { "-s", "--font-size" }, description = "Font size") 
    int fontSize = 19;

    @Parameters(paramLabel = "<word>", defaultValue = "Hello, picocli", 
               description = "Words to be translated into ASCII art.")
    private String[] words = { "Hello,", "picocli" }; 

    @Override
    public void run() { 
        // The business logic of the command goes here...
        // In this case, code for generation of ASCII art graphics
        // (omitted for the sake of brevity).
    }
}
