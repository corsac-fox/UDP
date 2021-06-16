import java.io.*;
import java.net.*;

public class UDPServer {
	
    public static final int PORT = 2005;
	private DatagramSocket servSocket;
	private boolean keepRunning = true;
	String output;
	
	public static void main(String[] args) {
		
        UDPServer server = new UDPServer();
        server.service();
   }
	
	public UDPServer()
	{
		try
		{
            servSocket = new DatagramSocket(PORT);
			
        }
		catch(SocketException e)
		{
            System.err.println("Unable to open socket : " + e.toString());
        }	
	}
	
	protected void service() {
		
        DatagramPacket datagram;
        byte[] data;
	  
        while (keepRunning)
	    {
		    try {
                data = new byte[7];
                datagram = new DatagramPacket(data, data.length);
                servSocket.receive(datagram);
			
			    output = new String(data,  0, datagram.getLength());
			    System.out.print(output + " ");
			
			    if (output.equals("end")) 
			    {
				    keepRunning = false;
				    System.out.println("\nDatagram received");
			    }
		    
			
            } catch(IOException e) {
			 
                System.err.println("I/O Exception : " + e.toString());
            }
        }
	    servSocket.close();
    }
}
