package NodeResources;

import java.io.Serializable;
import java.util.ArrayList;

public class PeersInNetwork implements Serializable{

    private ArrayList<Tuple<String, Integer>> _list = new ArrayList<>();

    public void OutputPeers(){
        _list.forEach((peer_info) -> System.out.println("NodeTypes.Peer Info: " + peer_info.host + " " + peer_info.port));
    }

    public ArrayList<Tuple<String, Integer>> Get()
    {
        return _list;
    }

    public void Set(ArrayList<Tuple<String, Integer>> peersInNetwork)
    {
        this._list = peersInNetwork;
    }

    public void Add(Tuple<String, Integer> item)
    {
        _list.add(item);
    }

    //Pop (remove From last idx)

    //Remove
}
