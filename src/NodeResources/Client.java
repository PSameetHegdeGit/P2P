package NodeResources;

import Interfaces.IClient;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements IClient {


    public PeersInNetwork peersInNetwork;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private int portno;

    public Client(PeersInNetwork peersInNetwork)
    {
        this.peersInNetwork = peersInNetwork;
    }

    public int GetPortNo()
    {
        return this.portno;
    }

    public PrintWriter GetOutputStream(){
        return this.out;
    }

    public BufferedReader GetInputStream(){
        return this.in;
    }

    public void startConnection(String ip, int port)
    {
        try
        {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public void stopConnection()
    {
        try
        {
            in.close();
            out.close();
            clientSocket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void StartupForNode()
    {
        out.println("startup");


        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println("Enter Port no: ");
            portno = Integer.parseInt(br.readLine());

            oos.writeObject(new Tuple<>(clientSocket.getInetAddress().getHostAddress(), portno));
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            //Unchecked Type Exception
            peersInNetwork.Set((ArrayList<Tuple<String, Integer>>) ois.readObject());

            oos.close();
            ois.close();
        }
        catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
        }

    }


    public void sendMessage(int type)
    {

        /*
            if type = 0, broadcast
            if type=1, get arraylist
            if type=2, send String message

         */

        BufferedReader br;
        String line;
        ObjectOutputStream oos;

        try
        {
            switch(type)
            {
                case 0:

                    System.out.println("Sending Message!");

                    out.println("broadcast");

//                    oos = new ObjectOutputStream(clientSocket.getOutputStream());
//                    oos.writeObject(new NodeResources.Tuple<> (clientSocket.getInetAddress().getHostAddress(), portno));
                    System.out.println("Opening Stream");
                    System.out.println("Response: " + in.readLine());

//                    oos.close();

                    break;

                case 1:
                    out.println("getArraylist");
                    System.out.println("Response: " + in.readLine());
                    break;

            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }

}
