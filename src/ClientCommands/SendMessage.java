package ClientCommands;

import Interfaces.IClientCommand;
import NodeResources.Client;
import NodeTypes.Peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendMessage implements IClientCommand{

    private Client _client;

    public SendMessage(Client _client){
        this._client = _client;
    }

    @Override
    public void Execute() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try{
            System.out.println("Enter host: ");
            String address = br.readLine();

            System.out.println("Enter port number: ");
            int portno = Integer.parseInt(br.readLine());

            _client.startConnection(address, portno);

            System.out.println("Enter message to Send:");
            String messageToSend = br.readLine();

            _client.GetOutputStream().println(messageToSend);
            System.out.println("Response: " + _client.GetInputStream().readLine());

            _client.stopConnection();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
