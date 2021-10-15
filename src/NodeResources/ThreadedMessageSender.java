package NodeResources;


public class ThreadedMessageSender implements Runnable{

    Client client;
    Tuple<String, Integer> peer_info;

    public ThreadedMessageSender(Client client, Tuple<String, Integer> peer_info){
        this.client = client;
        this.peer_info = peer_info;
    }

    public void run(){
        client.startConnection(peer_info.host, peer_info.port);

        System.out.println("Sending message to " + peer_info.host + " " + peer_info.port );

        client.sendMessage(0);

    }

}
