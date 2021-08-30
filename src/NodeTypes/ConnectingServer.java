package NodeTypes;

import NodeResources.*;

public class ConnectingServer {


    public static int CONNECTING_SERVER_PORT_NO = 5000;

    private Client client;
    private Server server;

    public static void main(String [] args){

        PeersInNetwork peersInNetwork = new PeersInNetwork();

        Client client = new Client(peersInNetwork);
        Server server = new Server(peersInNetwork);

        ConnectingServer connectingServer = new ConnectingServer(client, server);

        connectingServer.server.StartServer(CONNECTING_SERVER_PORT_NO);

    }

    private ConnectingServer(Client client, Server server){
        this.client = client;
        this.server = server;
    }



}
