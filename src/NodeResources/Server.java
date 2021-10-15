package NodeResources;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import Interfaces.IClientHandler;
import Interfaces.IServer;

public class Server implements IServer {


    private PeersInNetwork peersInNetwork;
    private ServerSocket serverSocket;

    public Server (PeersInNetwork peersInNetwork)
    {
        this.peersInNetwork = peersInNetwork;
    }

    @Override
    public void StartServer(int portno)
    {

        try{
            serverSocket = new ServerSocket(portno);

            while(true)
            {
                new ClientHandler(serverSocket.accept(), peersInNetwork).start();
            }
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

    private class ClientHandler extends Thread implements IClientHandler {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private PeersInNetwork peersInNetwork;


        public ClientHandler(Socket socket, PeersInNetwork peersInNetwork) {
            this.clientSocket = socket;
            this.peersInNetwork = peersInNetwork;
        }

        public void run() {

            ObjectOutputStream oos;
            ObjectInputStream ois;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line = in.readLine();

                System.out.println("Received Message: " + line);

                switch (line) {
                    case "broadcast":
                        BroadcastHandler();
                        break;

                    case "getArraylist":
                        GetArrayList();
                        break;

                    case "startup":
                        Startup();
                        break;

                    default:
                        MessageHandler(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        @Override
        public void BroadcastHandler() {
            System.out.println("RECEIVED BROADCAST!");
//                        ois = new ObjectInputStream(clientSocket.getInputStream());


            try {
//                            NodeResources.Tuple<String, Integer> peer_info = (NodeResources.Tuple <String, Integer>) ois.readObject();
//                            System.out.println("client peer info: " + peer_info.host + " " + peer_info.port);
//                            peers_in_network.add(peer_info);
                out.println("SERVER " + clientSocket.getInetAddress().getHostName() + " " + clientSocket.getPort() + " RECEIVED YOUR INFO");

            }
//                        catch(ClassNotFoundException e){
//                            e.printStackTrace();
//                        }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void MessageHandler(String message) {
            out.println("Message Received: " + message);

        }

        @Override
        public void Startup() {
            System.out.println("FOR STARTUP!");
            try {
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                Tuple<String, Integer> peer_info = (Tuple<String, Integer>) ois.readObject();
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                oos.writeObject(peersInNetwork.Get());

                peersInNetwork.Add(peer_info);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void GetArrayList() {
            System.out.println("WILL GET ARRAYLIST!");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                oos.writeObject(peersInNetwork);
            }
            catch(IOException e){
                e.printStackTrace();
            }

        }
    }
}










