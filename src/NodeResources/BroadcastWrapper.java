package NodeResources;

import NodeResources.Client;

public class BroadcastWrapper implements Runnable{

    Client client;
    Tuple<String, Integer> peer_info;

    public BroadcastWrapper(Client client, Tuple<String, Integer> peer_info){
        this.client = client;
        this.peer_info = peer_info;
    }

    public void run(){
        client.startConnection(peer_info.host, peer_info.port);

        System.out.println("Broadcasting message to " + peer_info.host + " " + peer_info.port );

        client.sendMessage(0);

    }


}
