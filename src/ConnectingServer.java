import java.net.InetAddress;




public class ConnectingServer extends SocketLibrary{

    InetAddress[] peers_in_network = new InetAddress[100];

    public static void main(String [] args){

        ConnectingServer server = new ConnectingServer();

        server.startServer(5000);


    }


}
