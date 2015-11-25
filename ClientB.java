import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class ClientB implements Runnable{
		PipedWriter src;
		PipedReader snk;
		boolean said;
		int readA;
		File fileB;
		Scanner scInput;
		char [] writeToken;
		Random rnd;
		
		public ClientB(){
			fileB = new File("Random_Sherlock_Holmes_Excerpt_2.txt");
			try {
				scInput = new Scanner(fileB);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rnd = new Random();
			src = new PipedWriter();
			snk = new PipedReader();
		}
		
		public PipedWriter getWriter(){
			return src;
		}
		public PipedReader getReader(){
			return snk;
		}
		@Override
		public void run() {
			/**
			 * try catch up here
			 * 
			 */
			try {
				while (true) {
					if(!scInput.hasNext()){
						scInput.close();
						scInput = new Scanner(fileB);
					}
					// System.out.println("Above readB in clientA");
					if(snk.ready()){
						System.out.print("At " + timeStamp() + " Client B received: ");
						while(snk.ready()) {
							readA = snk.read();
							System.out.print((char)readA);
							}
						System.out.println();
					}
					if((rnd.nextInt(50_000_000)) == 0) {
						writeToken = ((scInput.next()).toCharArray());
						src.write(writeToken);
					}
				}
			}
			catch(IOException ex) {
				System.err.println("IO exception in client B");
				
			}
			catch(Throwable ex) {
				System.err.println("Throwable exception in client B");
			}
			Thread.yield();
			
		}
		private String timeStamp(){
			LocalDateTime timePoint = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:m:s:A a : MM/dd/yyyy");
			String time = timePoint.format(formatter);
			return time;
		}		
}


