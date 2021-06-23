package udp;

import java.net.*;
import java.util.Scanner;

public class SecondClient implements Runnable{
    private DatagramSocket clientSocket;
    InetAddress address;
    private boolean isConnect = true;
    private final String disconnect = "end";
    private Thread receiver;

    public static void main(String[] args)
    {
        SecondClient client = new SecondClient();
        client.chat();
    }

    public SecondClient()
    {
        try
        {
            clientSocket = new DatagramSocket();
            address = InetAddress.getByName("192.168.88.251");
            receiver = new Thread(this);
            receiver.setDaemon(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        DatagramPacket datagram;
        byte[] data;

        while(isConnect)
        {
            try
            {
                data = new byte[100];
                datagram = new DatagramPacket(data, data.length);

                clientSocket.receive(datagram);
                String output = new String(data,  0, datagram.getLength());
                System.out.println(output);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    protected void chat()// отправляет сообщения
    {
        Scanner scanner = new Scanner(System.in);
        DatagramPacket packet;
        while (isConnect)
        {
            try
            {
                String input = scanner.nextLine();

                if (input.equals(disconnect))
                {
                    isConnect = false;
                    System.out.println("\nDatagram received");
                    clientSocket.close();
                }
                else {
                    byte[] byteArray = input.getBytes();
                    packet = new DatagramPacket(byteArray, byteArray.length, address, 2005);
                    clientSocket.send(packet);
                    if (!receiver.isAlive()) receiver.start();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}