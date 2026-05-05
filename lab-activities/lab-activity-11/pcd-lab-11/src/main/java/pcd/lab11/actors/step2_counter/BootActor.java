package pcd.lab11.actors.step2_counter;

import static pcd.lab11.actors.step2_counter.CounterUserMsgProtocol.*;

import org.apache.pekko.actor.*;

public class BootActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(BootMsg.class, this::onBootMsg)
				.build();	
	}

	private void onBootMsg(BootMsg msg) {
		ActorRef counter = this.getContext().actorOf(Props.create(CounterActor.class), "myCounter");
		ActorRef counterUser = this.getContext().actorOf(Props.create(CounterUserActor.class), "myCounterUser");
		counterUser.tell(new StartUsingCounterMsg(counter), this.getSelf());
	}

	/* types of messages */
	
	static public final class BootMsg {}
}
