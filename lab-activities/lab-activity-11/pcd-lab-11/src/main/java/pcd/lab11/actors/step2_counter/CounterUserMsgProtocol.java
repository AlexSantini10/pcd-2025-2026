package pcd.lab11.actors.step2_counter;

import org.apache.pekko.actor.ActorRef;

public class CounterUserMsgProtocol {

	static public record StartUsingCounterMsg(ActorRef counter) {}


}
