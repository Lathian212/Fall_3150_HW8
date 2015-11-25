/**
 * http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html
 * 
 * 
 */
//
import java.time.LocalDateTime;
/*
 * Author : Jonathan Kwiat
 * Two object's connect to server via pipe and pipe routes there traffic to each other.
 */
public class Main {

	public static void main(String[] args) {
		//first set up instances of ClientA, ClientB, and Server
		ClientA clientA = new ClientA();
		ClientB clientB = new ClientB();
		Server server = new Server();
		//Connect client pipes to server
		server.setConnectionClientA(clientA.getReader(), clientA.getWriter());
		server.setConnectionClientB(clientB.getReader(), clientB.getWriter());
		//Wrap everything in threads.
		Thread tClientA = new Thread(clientA);
		Thread tClientB = new Thread(clientB);
		Thread tServer = new Thread(server);
		//Start Threads
		//System.out.println("Line 28 main");
		tServer.start();
		tClientA.start();
		tClientB.start();
		
		

	}

}
