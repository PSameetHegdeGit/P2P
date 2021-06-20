import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;


public class Peer extends SocketLibrary implements Runnable {



    public static void main(String [] args){

        Peer p = new Peer();

        p.startConnection("localhost", 5000);

        //Startup
        p.sendMessage(3);

        p.peers_in_network.forEach((i) -> System.out.println("peer info: " + i.host + " " + i.port));

//        p.Broadcast();

        new Thread(p).start();
        p.startServer(p.portno);



    }

    public void run(){

        //Execute client commands for a peer
        while(true){
            ClientController();
        }
    }


    public void ClientController(){
        System.out.println("Enter Command to run: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            switch(br.readLine()){
                case "c":
                    System.out.println("Enter Port Number: ");
                    int portno = Integer.parseInt(br.readLine());
                    System.out.println("Enter host: ");
                    String host = br.readLine();
                    startConnection(host, portno);
                    sendMessage(2);

                case "b":
                    Broadcast();

                case "output peers":
                    peers_in_network.forEach((i) -> System.out.println("peer info: " + i.host + " " + i.port));

                default:
                    break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

    public void Broadcast(){
        peers_in_network.forEach((peer_info) -> new Thread(new BroadcastFactory(this, peer_info)).start());
    }



}
