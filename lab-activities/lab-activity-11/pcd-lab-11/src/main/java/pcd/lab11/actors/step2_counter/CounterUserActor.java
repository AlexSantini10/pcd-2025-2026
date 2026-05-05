package pcd.lab11.actors.step2_counter;

import static pcd.lab11.actors.step2_counter.CounterMsgProtocol.*;

import org.apache.pekko.actor.*;
import static pcd.lab11.actors.step2_counter.CounterUserMsgProtocol.*;

public class CounterUserActor extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartUsingCounterMsg.class, this::onStartMsg)
				.match(CounterValueMsg.class, this::onCounterValueMsg)
				.build();		
	}

	private void onStartMsg(StartUsingCounterMsg msg) {
		msg.counter().tell(new IncMsg(), this.getSelf());
		msg.counter().tell(new IncMsg(), this.getSelf());
		msg.counter().tell(new GetValueMsg(this.getContext().getSelf()), this.getSelf());
	}

	private void onCounterValueMsg(CounterValueMsg msg){
		log("value: " + msg.value());
	}

	private void log(String msg) {
		System.out.println("[CounterUserActor] " + msg);
	}
}
