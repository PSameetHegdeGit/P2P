package ClientCommands;

import Interfaces.IClientCommand;
import NodeResources.Client;
import NodeResources.ThreadedMessageSender;
import NodeResources.Tuple;
import NodeTypes.Peer;

import java.util.ArrayList;

public class Broadcast implements IClientCommand {

    private Client _client;


    public Broadcast(Client client)
    {
        this._client = client;
    }

    @Override
    public void Execute()
    {
        ArrayList<Tuple<String, Integer>> peersInNetwork = _client.peersInNetwork.Get();
        peersInNetwork.forEach((peer_info) -> new Thread(new ThreadedMessageSender(_client, peer_info)).start());
    }


}
