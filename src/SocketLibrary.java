
import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class SocketLibrary{


    public static class ClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;


        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        public void run(){
            try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line = in.readLine();
                System.out.println(line);

                switch(line){
                    case "broadcast":
                        out.println("WILL BROADCAST!");
                        
                    case "getArraylist":
                        out.println("WILL GET ARRAYLIST!");
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


    public void startServer(int port){

        try{

            serverSocket = new ServerSocket(port);

            while(true)
                new ClientHandler(serverSocket.accept()).start();

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

        try{
            switch(type){
                case 0:
                    out.println("broadcast");
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

            }

        }
        catch(IOException e){
            e.printStackTrace();
        }


    }




}
