package pcd.lab11.actors.step1_pingpong;

import org.apache.pekko.actor.*;

import pcd.lab11.actors.step1_pingpong.PingerPongerProtocol.*;

public class PongerActor extends AbstractActor {

	public Receive createReceive() {
		return receiveBuilder()
				.match(PingMsg.class, this::onPingMsg)
	            .build();
	}

	private void onPingMsg(PingMsg msg) {
		log("got ping " + msg.count() + " => pong " + (msg.count() + 1));
		msg.pinger().tell(new PongMsg(msg.count() + 1, this.getSelf()), this.getSelf());
	}
	
	private void log(String msg) {
		System.out.println("[Ponger] " + msg);
	}
	
}
