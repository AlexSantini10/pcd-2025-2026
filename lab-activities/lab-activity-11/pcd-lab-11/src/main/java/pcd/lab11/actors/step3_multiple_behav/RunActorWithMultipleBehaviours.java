package pcd.lab11.actors.step3_multiple_behav;

import org.apache.pekko.actor.*;

public class RunActorWithMultipleBehaviours {
  public static void main(String[] args) throws Exception  {

	  final ActorSystem system = ActorSystem.create("myActorSystem");		
	  final ActorRef myActor =  system.actorOf(Props.create(ActorWithBehaviours.class), "myActor");

	  /* The next two msgs will be unhandled, because not managed in the initial behaviour */
	  myActor.tell(new MsgProtocol.MsgOne(), null);  
	  myActor.tell(new MsgProtocol.MsgTwo(), null);  
	  
	  /* give time to the logging system to setup */
	  Thread.sleep(1000);

	  /* The next msgs will be managed and will cause a transition to new behaviours, up to stopped */
	  myActor.tell(new MsgProtocol.MsgZero(), null); 
	  myActor.tell(new MsgProtocol.MsgOne(), null); 
	  myActor.tell(new MsgProtocol.MsgTwo(), null); 
  
	  /* these msgs will be not delivered since the actor stopped */
	  myActor.tell(new MsgProtocol.MsgZero(), null); 
	  myActor.tell(new MsgProtocol.MsgOne(), null); 
	  myActor.tell(new MsgProtocol.MsgTwo(), null); 

	  System.out.println("done");
  }
}
