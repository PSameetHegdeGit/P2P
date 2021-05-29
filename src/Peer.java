import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.io.IOException;


public class Peer extends SocketLibrary implements Runnable {



    public static void main(String [] args){

        Peer p = new Peer();

        //Testing Peer Broadcast
        p.peers_in_network.add(new Tuple<>("localhost", 5002));
        p.peers_in_network.add(new Tuple<>("localhost", 5003));
        /////////////////////////////////

        p.startConnection("localhost", 5000);

        //Request Arraylist
        p.sendMessage(1);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter Port no: ");

        try {
            int portno = Integer.parseInt(br.readLine());
            new Thread(p).start();
            p.startServer(portno);
        }
        catch(IOException e){
            e.printStackTrace();
        }



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
