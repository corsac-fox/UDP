import java.io.*;
import java.net.*;

public class UDPClient {
	
	private static String[] data = {"We", "never", "thought", "it", "was", "the", "end"};
	private DatagramSocket clientSocket;
	InetAddress addr;
	DatagramPacket packet;
	
	public static void main(String[] args)
	{
		UDPClient client = new UDPClient();
		client.send();
		
	}
	
	public UDPClient()
	{
		try
		{
			clientSocket = new DatagramSocket();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void send()
	{
		try
		{
			addr = InetAddress.getByName("localhost");
			for (String s : data)
			{
				byte[] byteArray = s.getBytes();
				packet = new DatagramPacket(byteArray, byteArray.length, addr, 2005);
				clientSocket.send(packet);
			}
			
            System.out.println("Datagram sent");
            clientSocket.close();
			
        } catch (Exception e)
		{
            e.printStackTrace();
        }
	}
	
}
