package pcd.lab11.actors.step5_gui;

import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;

public class RunActorWithGUI {
  public static void main(String[] args) throws Exception  {
    ActorSystem system = ActorSystem.create("MySystem");
    
    ActorRef act = system.actorOf(Props.create(ViewActor.class));
	new ViewFrame(act).display();
  }
}
