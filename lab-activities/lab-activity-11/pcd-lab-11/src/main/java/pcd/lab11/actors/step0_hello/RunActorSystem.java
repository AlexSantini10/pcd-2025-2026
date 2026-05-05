package pcd.lab11.actors.step0_hello;

import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.Props;
import org.apache.pekko.actor.ActorSystem;

public class RunActorSystem {

	public static void main(String[] args) {
		
		final ActorSystem system = ActorSystem.create("my-actor-system");
		
		final ActorRef helloActor =  system.actorOf(Props.create(HelloWorldActor.class), "my-actor");
		helloActor.tell(new HelloWorldMsgProtocol.SayHello("World"), ActorRef.noSender());
		helloActor.tell(new HelloWorldMsgProtocol.SayHello("World Again"),  ActorRef.noSender());
	}

}
