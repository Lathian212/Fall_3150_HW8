import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//The output pipes are read here in Server, Server then has to display a message and write out the message to the PipedWriter pipes
//All these pipes must be connected to there complimentory pipes in ClientA and ClientB
public class Server implements Runnable{
	private PipedReader ClientAOutput;
	private PipedReader ClientBOutput;
	private PipedWriter ClientAInput;
	private PipedWriter ClientBInput;
	int readA;
	int readB;
	/*
	 * Constructor will set up instances of the pipes that will then use the connect method with the Clients.
	 */
	public Server(){
		ClientAOutput = new PipedReader();
		ClientBOutput = new PipedReader();
		ClientAInput = new PipedWriter();
		ClientBInput = new PipedWriter();
	}
	public void setConnectionClientA (PipedReader clientAsnk, PipedWriter clientAsrc) {
		try {
			ClientAOutput.connect(clientAsrc);
			ClientAInput.connect(clientAsnk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setConnectionClientB (PipedReader clientBsnk, PipedWriter clientBsrc) {
		try {
			ClientBOutput.connect(clientBsrc);
			ClientBInput.connect(clientBsnk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		/**
		 * try catch up here
		 * 
		 */
		try {
			while (true) {

				if(ClientAOutput.ready()){
					System.out.print("At " + timeStamp() + " server says Client A said: ");
					while(ClientAOutput.ready()) {
						readA = ClientAOutput.read();
						System.out.print((char)readA);
						ClientBInput.write(readA);
					}
					System.out.println();
				}
				if(ClientBOutput.ready()){
					System.out.print("At " + timeStamp() + " server says Client B said: ");
					while(ClientBOutput.ready()) {
						readB = ClientBOutput.read();
						System.out.print((char)readB);
						ClientAInput.write(readB);
					}
					System.out.println();
				}
			}
		}
		catch(IOException ex) {
			System.err.println("Caught exception in server");
		}
	}
	public String timeStamp(){
		LocalDateTime timePoint = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:m:s:A a : MM/dd/yyyy");
		String time = timePoint.format(formatter);
		return time;
	}

}
