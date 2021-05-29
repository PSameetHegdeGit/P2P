

public class BroadcastFactory extends SocketLibrary implements Runnable{

    Peer p;
    Tuple<String, Integer> peer_info;

    public BroadcastFactory(Peer p, Tuple<String, Integer> peer_info){
        this.p = p;
        this.peer_info = peer_info;
    }

    public void run(){
        System.out.println("port: " + peer_info.port);
        System.out.println("Broadcasting message!");
        p.startConnection(peer_info.host, peer_info.port);

        p.sendMessage(0);

    }


}
