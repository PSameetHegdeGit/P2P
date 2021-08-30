package NodeTypes;

import java.io.*;

import ClientCommands.Connect;
import NodeResources.*;

public class Peer implements Runnable{

    private Client client;
    private Server server;


    public static void main(String [] args){

        PeersInNetwork peersInNetwork = new PeersInNetwork();

        Server server = new Server(peersInNetwork);
        Client client = new Client(peersInNetwork);

        Peer p = new Peer(client, server);

        p.client.sendMessage(3);

        new Thread(p).start();

        p.server.StartServer(Server.SpecifyServerPortNo());

        p.client.startConnection("localhost", ConnectingServer.CONNECTING_SERVER_PORT_NO);

    }

    public Peer(Client client, Server server){
        this.client = client;
        this.server = server;
    }


    public Client GetClient(){
        return client;
    }

    public Server GetServer(){
        return server;
    }

    public void run(){

        ClientController();
    }

    public void ClientController(){

        while(true){
            System.out.println("Enter Command to run: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                switch(br.readLine()){
                    case "Connect":
                        new Connect(this, null, null).Execute();

                    case "Broadcast":
                        client.Broadcast();
                        break;

                    case "output peers":
                        client.peersInNetwork.OutputPeers();
                        break;

                    default:
                        break;
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }

        }


    }



}
