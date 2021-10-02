package NodeTypes;

import java.io.*;

import ClientCommands.SendMessage;
import NodeResources.*;

public class Peer implements Runnable{

    private Client _client;
    private Server _server;

    public static void main(String [] args){

        PeersInNetwork peersInNetwork = new PeersInNetwork();

        Server server = new Server(peersInNetwork);
        Client client = new Client(peersInNetwork);

        Peer p = new Peer(client, server);

        p._client.startConnection("localhost", ConnectingServer.GetConnectingServerPortNumber());
        p._client.StartupForNode();

        new Thread(p).start();

        p._server.StartServer(p._client.GetPortNo());

    }

    public Peer(Client client, Server server){
        this._client = client;
        this._server = server;
    }


    public Client GetClient()
    {
        return _client;
    }

    public Server GetServer()
    {
        return _server;
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
                    case "SendMessage":
                        new SendMessage(this.GetClient()).Execute();

                    case "Broadcast":
                        _client.Broadcast();
                        break;

                    case "output peers":
                        _client.peersInNetwork.OutputPeers();
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
