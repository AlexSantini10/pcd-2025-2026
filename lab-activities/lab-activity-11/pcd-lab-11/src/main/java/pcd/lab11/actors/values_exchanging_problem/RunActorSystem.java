package pcd.lab11.actors.values_exchanging_problem;

import static pcd.lab11.actors.values_exchanging_problem.ValuesExchangingProtocol.*;

import java.util.ArrayList;

import org.apache.pekko.actor.*;

public class RunActorSystem {
  public static void main(String[] args) throws Exception  {
	  
		var system = ActorSystem.create("my-actor-system");	
		
		var peers = new ArrayList<ActorRef>();
		int numPeers = 10;
		
		for (int i = 0; i < numPeers; i++) {
			var peer =  system.actorOf(Props.create(PeerActor.class), "peer-" + i);
			peers.add(peer);
		}

		for (var p: peers) {
			p.tell(new BootMsg(peers), ActorRef.noSender());
		}
  }
}
