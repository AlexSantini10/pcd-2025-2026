package pcd.lab11.actors.values_exchanging_problem;

import static pcd.lab11.actors.values_exchanging_problem.ValuesExchangingProtocol.*;

import java.util.List;
import java.util.Random;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorRef;

public class PeerActor extends AbstractActor {

	private int myValue;
	private int minValue, maxValue;
	private int numValuesReceived;
	private List<ActorRef> peers;
	
	public Receive createReceive() {
		return receiveBuilder()
				.match(BootMsg.class, this::onBootMsg)
				.match(ValueMsg.class, this::onValueMsg)
	            .build();
	}

	private void onBootMsg(BootMsg msg) {		
		this.peers = msg.peers();		
		var gen = new Random();
		myValue = gen.nextInt(1000);
		log("Booted - my value: " + myValue);
		
		numValuesReceived = 0;
		minValue = maxValue = myValue;	
		for (var peer: msg.peers()) {
			if (!peer.equals(getSelf())) {
				peer.tell(new ValueMsg(myValue), getSelf());
			}
		}
	}
	
	private void onValueMsg(ValueMsg msg) {
		if (msg.value() < minValue) {
			minValue = msg.value();
		} else if (msg.value() > maxValue) {
			maxValue = msg.value();
		}
		
		numValuesReceived++; 
		if (numValuesReceived == peers.size() - 1) {
			log("Done - min: " + minValue + ", max: " + maxValue);
		}
	}
	
	private void log(String msg) {
		System.out.println("[ " + System.currentTimeMillis() + " ][ " + this.getSelf().path().name() + " ] " + msg);
	}
	
}
