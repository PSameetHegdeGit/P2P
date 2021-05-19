import jdk.jfr.Description;

import java.net.*;
import java.io.*;


public class SocketLibrary{


    private static class ClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;


        public ClientHandler(Socket socket){
            this.clientSocket = socket;
            manageMessage();
        }

        public void manageMessage(){
            try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line = in.readLine();
                System.out.println(line);
                out.println("Message Received: " + line);
            }
            catch(IOException e){
                e.printStackTrace();
            }

        }


    }

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


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

    public void sendMessage(){

        System.out.println("Enter message to Send:");

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            out.println(line);
            System.out.println("Response: " + in.readLine());

        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

}
