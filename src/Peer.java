

public class Peer extends SocketLibrary {

    public static void main(String [] args){

        Peer p = new Peer();

        p.startConnection("localhost", 5000);

        p.sendMessage();
    }


}
