package pcd.lab11.actors.step2_counter;

import org.apache.pekko.actor.*;

public class RunActorSystem {
		
  public static void main(String[] args) throws Exception  {

	final ActorSystem system = ActorSystem.create("my-actor-system");		
	final ActorRef bootActor =  system.actorOf(Props.create(BootActor.class), "main-actor");
	bootActor.tell(new BootActor.BootMsg(), null);
  }
}
