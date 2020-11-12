package Christopher.Zendesk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.*;
import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.ZendeskResponseException;
import org.zendesk.client.v2.model.Ticket;

import RestController.TicketController;
import Security.Constants;

public class TicketControllerTest {
	
	Zendesk zd;
	List<Ticket> allTickets;
	static TicketController ticketController;
	//Due to a lot of information coming back from these methods in the ticket controller class
	//We use assertNotNull often to confirm that all the details are returning;
	//We also use System.out.println during out tests to check that the result is the one we're getting back requiring manual checking
	//this wouldn't work in a large scale system.
	
	//TODO try to fix this and compare string to string
	@BeforeClass
    public static void setup() {
	//We init the ticketcontroller to the zendesk api so we don't have to repeat it for each test ran 
	//just the initial set up of the class
		ticketController = new TicketController();
		ticketController.Init();
	}
 
    @AfterClass
    public static void tearDown() {
    }
	

	@Test
	public void testGetAllTickets() {
		assertNotNull(ticketController.GetAllTickets());
	}

	
	//This test also covers the Pagination test so no point in duplicating tests
	@Test
	public void testDisplayAllTickets() {
		System.out.println(ticketController.DisplayAllTickets(2));
		assertNotNull(ticketController.DisplayAllTickets(2), ticketController.DisplayAllTickets(2));
	}

	@Test
	public void testGetSingleTicket() {
		System.out.println(ticketController.GetSingleTicket(2));
		assertNotNull("Ticket number 2",ticketController.GetSingleTicket(2));
		
	}


	@Test
	public void testGetResponseDetails() {
		
		//create a new request for the zendesk api
		//loop through our tickets
		//we should get a 401 because our password doesn't match
		
		zd = new Zendesk.Builder(Constants.DOMAIN)
		        .setUsername(Constants.USERNAME)
		        .setPassword(Constants.TOKEN)
		        .build();
		try
		{
			for (Ticket ticket: zd.getTickets()) {
				allTickets.add(ticket);
			}	
		}
		catch(ZendeskResponseException ze)
		{
			assertEquals("401 Unauthorized Unauthorised access, log in details are incorrect." ,ticketController.GetResponseDetails(ze));
		}
		
		
	}

}
