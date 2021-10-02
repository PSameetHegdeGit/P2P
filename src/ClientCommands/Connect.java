package ClientCommands;

import Interfaces.IClientCommand;
import NodeTypes.Peer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Connect implements IClientCommand{

    private Peer p;
    private Integer portno;
    private String address;


    public Connect(Peer p, String address, Integer portno){
        this.p = p;
        this.address = address;
        this.portno = portno;
    }


    @Override
    public void Execute() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        if(address  == null || portno == null){
            try{
                System.out.println("Enter host: ");
                address = br.readLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            try{
                System.out.println("Enter port number: ");
                portno = Integer.parseInt(br.readLine());
            }
            catch(IOException e){
                e.printStackTrace();
            }

        }

        p.GetClient().startConnection(address, portno);
        p.GetClient().sendMessage(2);
        p.GetClient().stopConnection();

    }
}
