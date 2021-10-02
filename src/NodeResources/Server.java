package NodeResources;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import Interfaces.IServer;

public class Server implements IServer {


    private PeersInNetwork peersInNetwork;
    protected ServerSocket serverSocket;

    public Server (PeersInNetwork peersInNetwork)
    {
        this.peersInNetwork = peersInNetwork;
    }

    /*public static int SpecifyServerPortNo()
    {
        System.out.println("Enter Port Number for Server: ");
        BufferedReader portReader = new BufferedReader(new InputStreamReader(System.in));

        int portno = 0;

        try {
            portno = Integer.parseInt(portReader.readLine());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(portno);

        return portno;
    }*/

    @Override
    public void StartServer(int portno)
    {

        try{
            serverSocket = new ServerSocket(portno);

            while(true)
                new ClientHandler(serverSocket.accept(), peersInNetwork).start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void StopServer()
    {

        try{
            serverSocket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public class ClientHandler extends Thread
    {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private PeersInNetwork peersInNetwork;



        public ClientHandler(Socket socket, PeersInNetwork peersInNetwork)
        {
            this.clientSocket = socket;
            this.peersInNetwork = peersInNetwork;
        }

        public void run(){

            ObjectOutputStream oos;
            ObjectInputStream ois;

            try
            {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line = in.readLine();

                System.out.println("Received Message: " + line);

                switch(line)
                {
                    case "broadcast":
                        System.out.println("RECEIVED BROADCAST!");
//                        ois = new ObjectInputStream(clientSocket.getInputStream());


                        try
                        {
//                            NodeResources.Tuple<String, Integer> peer_info = (NodeResources.Tuple <String, Integer>) ois.readObject();
//                            System.out.println("client peer info: " + peer_info.host + " " + peer_info.port);
//                            peers_in_network.add(peer_info);
                            out.println("SERVER " + clientSocket.getInetAddress().getHostName() + " " + clientSocket.getPort() + " RECEIVED YOUR INFO");

                        }
//                        catch(ClassNotFoundException e){
//                            e.printStackTrace();
//                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        break;

                    case "getArraylist":
                        System.out.println("WILL GET ARRAYLIST!");
                        oos = new ObjectOutputStream(clientSocket.getOutputStream());
                        oos.writeObject(peersInNetwork);
                        break;

                    case "startup":
                        System.out.println("FOR STARTUP!");
                        ois = new ObjectInputStream(clientSocket.getInputStream());
                        try{
                            Tuple<String, Integer> peer_info = (Tuple<String, Integer>) ois.readObject();

//                            System.out.println("client peer info: " + peer_info.host + " " + peer_info.port);

                            oos = new ObjectOutputStream(clientSocket.getOutputStream());
                            oos.writeObject(peersInNetwork.Get());

                            peersInNetwork.Add(peer_info);
                        }
                        catch(ClassNotFoundException e){
                            e.printStackTrace();
                        }
                        break;

                    default:
                        out.println("Message Received: " + line);

                }

            }
            catch(IOException e){
                e.printStackTrace();
            }

        }


    }









}
