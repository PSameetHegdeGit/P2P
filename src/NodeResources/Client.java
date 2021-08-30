package NodeResources;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {


    public PeersInNetwork peersInNetwork;

    protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;

    int portno;

    public Client(PeersInNetwork peersInNetwork){
        this.peersInNetwork = peersInNetwork;
    }


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

    public void Broadcast(){
        ArrayList<Tuple<String, Integer>> peersInNetwork = this.peersInNetwork.Get();
        peersInNetwork.forEach((peer_info) -> new Thread(new BroadcastWrapper(this, peer_info)).start());
    }



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

                case 2:
                    System.out.println("Enter message to Send:");
                    br = new BufferedReader(new InputStreamReader(System.in));
                    line = br.readLine();
                    out.println(line);
                    System.out.println("Response: " + in.readLine());
                    break;

                case 3:
                    out.println("startup");

                    br = new BufferedReader(new InputStreamReader(System.in));
                    oos = new ObjectOutputStream(clientSocket.getOutputStream());

                    System.out.println("Enter Port no: ");
                    portno = Integer.parseInt(br.readLine());

                    oos.writeObject(new Tuple<>(clientSocket.getInetAddress().getHostAddress(), portno));

                    try{
                        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

                        //Unchecked Type Exception
                        peersInNetwork.Set((ArrayList<Tuple<String, Integer>>) ois.readObject());

                        ois.close();
                    }
                    catch(ClassNotFoundException e){
                        e.printStackTrace();
                    }

                    oos.close();
                    break;


            }

        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

}
