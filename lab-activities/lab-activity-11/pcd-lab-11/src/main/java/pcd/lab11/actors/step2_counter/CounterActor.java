package pcd.lab11.actors.step2_counter;

import static pcd.lab11.actors.step2_counter.CounterMsgProtocol.*;

import org.apache.pekko.actor.*;

public class CounterActor extends AbstractActor {

	private int count;
	
	private CounterActor() {
		count = 0;
	}

	public Receive createReceive() {
		return receiveBuilder()
				.match(IncMsg.class, this::onIncMsg)
				.match(GetValueMsg.class, this::onGetValueMsg)
	            .build();
	}

	private void onIncMsg(IncMsg msg) {
		count++;
	}

	private void onGetValueMsg(GetValueMsg msg) {
		msg.replyTo().tell(new CounterValueMsg(count), this.getSelf());
	}
	
}
