import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ConnectingServer extends SocketLibrary{

    ArrayList<Tuple<InetAddress, Integer>> peers_in_network = new ArrayList<>();

    public static void main(String [] args){

        ConnectingServer server = new ConnectingServer();

        server.startServer(5000);


    }

    @Override
    public void startServer(int port){

        try{

            super.serverSocket = new ServerSocket(port);

            while(true) {
                Socket clientSocket = serverSocket.accept();
                peers_in_network.add(new Tuple(clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort()));
                new ClientHandler(clientSocket).start();
                peers_in_network.forEach((i) -> System.out.println(i));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }



}
