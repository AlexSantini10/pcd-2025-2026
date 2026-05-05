package pcd.lab11.actors.step2_counter;

import org.apache.pekko.actor.ActorRef;

public class CounterMsgProtocol {

	static public record IncMsg() {}
	
	static public record GetValueMsg(ActorRef replyTo) {}

	static public record CounterValueMsg(int value) {} 

}
