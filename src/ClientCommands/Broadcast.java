package ClientCommands;

import Interfaces.IClientCommand;
import NodeTypes.Peer;
import jdk.jshell.spi.ExecutionControl;

public class Broadcast implements IClientCommand {

    private Peer p;


    public Broadcast(Peer p){
        this.p = p;
    }

    @Override
    public void Execute() {

    }


}