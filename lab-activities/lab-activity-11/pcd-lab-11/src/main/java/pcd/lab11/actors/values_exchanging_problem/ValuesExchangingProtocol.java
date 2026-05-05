package pcd.lab11.actors.values_exchanging_problem;

import java.util.List;

import org.apache.pekko.actor.ActorRef;

public interface ValuesExchangingProtocol {

	public record BootMsg (List<ActorRef> peers) {}
	public record ValueMsg (int value) {}
	
	

}
