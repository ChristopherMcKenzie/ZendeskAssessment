package Run;

import RestController.*;
import picocli.CommandLine;
import picocli.CommandLine.*;
public class Main implements Runnable{
	public static void main(String[] args){
		TicketController tc = new TicketController();
		tc.Init();
		tc.GetAllTickets();
		System.out.println(tc.DisplayAllTickets());	

	}
	//TODO move INIT() Into here
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
