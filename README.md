# ZendeskAssessment

Christopher McKenzie - Zendesk Ticket Assessment

There are two main classes that are used here. The main class which just consumes the TicketController class and uses different commands
The second class is the TicketController class which uses the Zendesk java class library - https://github.com/cloudbees-oss/zendesk-java-client
The use of this class made it much easier to use the api and manipulate the data. 

Theses are the main commands available for this project.
alltickets(displays all the tickets 25 at a time)
  
  -next - allows to move up the pages
  -prev - allows to move down the pages
  -back - allows you to go back to the main menu

singleticket
  -[ticketNumber] - Select a ticket number 
 
exit
 - Exits the program
 
 
To run the program place the jar file in a folder of your chosing. 
Use the command line to navigate to that folder using the cd [folder name] command
When in the folder with the jar file simply type java -jar [jar file name].jar 
