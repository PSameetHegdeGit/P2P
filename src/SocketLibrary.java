
import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class SocketLibrary{


    public static class ClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private ArrayList<Tuple<String, Integer>> peers_in_network;


        public ClientHandler(Socket socket, ArrayList<Tuple<String, Integer>> peers_in_network){
            this.clientSocket = socket;
            this.peers_in_network = peers_in_network;
        }

        public void run(){

            ObjectOutputStream oos;
            ObjectInputStream ois;

            try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line = in.readLine();

                switch(line){
                    case "broadcast":
                        System.out.println("RECEIVED BROADCAST!");
                        ois = new ObjectInputStream(clientSocket.getInputStream());


                        try{
                            Tuple<String, Integer> peer_info = (Tuple <String, Integer>) ois.readObject();
                            System.out.println("client peer info: " + peer_info.host + " " + peer_info.port);
                            peers_in_network.add(peer_info);
                            out.println("SERVER " + clientSocket.getInetAddress().getHostName() + " " + clientSocket.getPort() + " RECEIVED YOUR INFO");

                        }
                        catch(ClassNotFoundException e){
                            e.printStackTrace();
                        }

                    case "getArraylist":
                        System.out.println("WILL GET ARRAYLIST!");
                        oos = new ObjectOutputStream(clientSocket.getOutputStream());
                        oos.writeObject(peers_in_network);

                    case "startup":
                        System.out.println("FOR STARTUP!");
                        ois = new ObjectInputStream(clientSocket.getInputStream());
                        try{
                            Tuple<String, Integer> peer_info = (Tuple <String, Integer>) ois.readObject();

//                            System.out.println("client peer info: " + peer_info.host + " " + peer_info.port);

                            oos = new ObjectOutputStream(clientSocket.getOutputStream());
                            oos.writeObject(peers_in_network);

                            peers_in_network.add(peer_info);
                        }
                        catch(ClassNotFoundException e){
                            e.printStackTrace();
                        }

                    default:
                        out.println("Message Received: " + line);

                }

            }
            catch(IOException e){
                e.printStackTrace();
            }

        }


    }

    protected ServerSocket serverSocket;
    protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;


    //Port number for server thread on peer
    int portno;


    public void startServer(int port){

        try{

            serverSocket = new ServerSocket(port);

            while(true)
                new ClientHandler(serverSocket.accept(), peers_in_network).start();

        }
        catch(IOException e){
            e.printStackTrace();
        }


    }


    public void stopServer(){
        try{

            in.close();
            clientSocket.close();
            serverSocket.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }




    /*
        Below are client Functions

     */

    public void startConnection(String ip, int port){
        try{
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

    public void stopConnection(){
        try{
            in.close();
            out.close();
            clientSocket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }



    //Methods and Variables need for communication between peers

    ArrayList<Tuple<String, Integer>> peers_in_network = new ArrayList<>();


    public void sendMessage(int type){

        /*
            if type = 0, broadcast
            if type=1, get arraylist
            if type=2, send String message

         */

        BufferedReader br;
        String line;
        ObjectOutputStream oos;

        try{
            switch(type){
                case 0:
                    out.println("broadcast");

                    oos = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos.writeObject(new Tuple<> (clientSocket.getInetAddress().getHostAddress(), portno));

                    System.out.println("Response: " + in.readLine());

                case 1:
                    out.println("getArraylist");
                    System.out.println("Response: " + in.readLine());

                case 2:
                    System.out.println("Enter message to Send:");
                    br = new BufferedReader(new InputStreamReader(System.in));
                    line = br.readLine();
                    out.println(line);
                    System.out.println("Response: " + in.readLine());

                case 3:
                    out.println("startup");

                    br = new BufferedReader(new InputStreamReader(System.in));
                    oos = new ObjectOutputStream(clientSocket.getOutputStream());

                    System.out.println("Enter Port no: ");
                    portno = Integer.parseInt(br.readLine());

                    oos.writeObject(new Tuple<> (clientSocket.getInetAddress().getHostAddress(), portno));

                    try{
                        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                        peers_in_network = (ArrayList<Tuple<String, Integer>>) ois.readObject();
                        ois.close();
                    }
                    catch(ClassNotFoundException e){
                        e.printStackTrace();
                    }

                    oos.close();


            }

        }
        catch(IOException e){
            e.printStackTrace();
        }


    }




}
