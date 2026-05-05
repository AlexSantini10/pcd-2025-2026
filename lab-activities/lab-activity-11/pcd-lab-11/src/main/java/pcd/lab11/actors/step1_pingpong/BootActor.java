package pcd.lab11.actors.step1_pingpong;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.Props;


public class BootActor extends AbstractActor {

	public Receive createReceive() {
		return receiveBuilder()
				.match(BootMsg.class, this::onBootMsg)
	            .build();
	}

	private void onBootMsg(BootMsg msg) {
		ActorRef pinger = this.getContext().actorOf(Props.create(PingerActor.class), "pinger");
		ActorRef ponger = this.getContext().actorOf(Props.create(PongerActor.class), "ponger");
		pinger.tell(new pcd.lab11.actors.step1_pingpong.PingerPongerProtocol.BootMsg(ponger), this.getSelf());
	}

	/* messages */
	
	static public class BootMsg {}
}
