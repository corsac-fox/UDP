package tests;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPServer implements Runnable{

    public static final int PORT = 2005;
    private DatagramSocket servSocket;
    private boolean keepRunning = true;
    static InetAddress clientAddress;
    static int clientPort;
    private Thread sender;

    public static void main(String[] args)
    {
        UDPServer server = new UDPServer();
        server.service();
    }

    public UDPServer()
    {
        try
        {
            servSocket = new DatagramSocket(PORT);
            sender = new Thread(this);
            sender.setDaemon(true);

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

                data = new byte[100];
                datagram = new DatagramPacket(data, data.length);
                servSocket.receive(datagram);
                //if (clientAddress == null || clientPort == 0)
                {
                    clientAddress = datagram.getAddress();
                    clientPort = datagram.getPort();
                    if (!sender.isAlive()) sender.start();

                }

                String output = new String(data,  0, datagram.getLength());
                System.out.println(output);

            } catch(IOException e) {

                System.err.println("I/O Exception : " + e.toString());
            }
        }
    }

    @Override
    public void run() //отправляет сообщения, когда узнает адрес клиента
    {
        Scanner scanner = new Scanner(System.in);
        DatagramPacket packet;
        while (keepRunning)
        {

            try
            {
                String input = scanner.nextLine();

                if (input.equals("end"))
                {
                    keepRunning = false;
                    System.out.println("\nDatagram received");
                    servSocket.close();
                    System.exit(0);
                }

                else if (clientAddress != null && input != "")
                {
                    byte[] byteArray = input.getBytes();
                    packet = new DatagramPacket(byteArray, byteArray.length, clientAddress, clientPort);
                    servSocket.send(packet);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}