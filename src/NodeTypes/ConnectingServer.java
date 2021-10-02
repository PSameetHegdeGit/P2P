package NodeTypes;

import NodeResources.*;

public class ConnectingServer {


    private static int CONNECTING_SERVER_PORT_NO = 5000;

    private Client _client;
    private Server _server;

    public static void main(String [] args){

        System.out.println("Setting up Connecting Server");
        PeersInNetwork peersInNetwork = new PeersInNetwork();

        Client client = new Client(peersInNetwork);
        Server server = new Server(peersInNetwork);

        ConnectingServer connectingServer = new ConnectingServer(client, server);

        connectingServer._server.StartServer(CONNECTING_SERVER_PORT_NO);
    }

    private ConnectingServer(Client client, Server server){
        this._client = client;
        this._server = server;
    }

    public static int GetConnectingServerPortNumber()
    {
        return CONNECTING_SERVER_PORT_NO;
    }




}
