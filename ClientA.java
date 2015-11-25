import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class ClientA implements Runnable{
		PipedWriter src;
		PipedReader snk;
		int readB;
		File fileA;
		Scanner scInput;
		char [] writeToken;
		Random rnd;
		
		public ClientA(){
			fileA = new File("Random_Sherlock_Holmes_Excerpt_1.txt");
			try {
				scInput = new Scanner(fileA);
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
					// System.out.println("In Client A's outer while loop");
					if(!scInput.hasNext()){
						scInput.close();
						scInput = new Scanner(fileA);
					}
					// System.out.println("Above readB in clientA");
					if(snk.ready()){
						System.out.print("At " + timeStamp() + " Client A received: ");
						while(snk.ready()) {
							readB = snk.read();
							System.out.print((char)readB);
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
				System.err.println("IO exception in client A");
				
			}
			catch(Throwable ex) {
				System.err.println("Throwable exception in client A");
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


