package pcd.lab11.actors.step4_multiple_behav_stash;

import org.apache.pekko.actor.*;

public class RunActorWithMultipleBehavioursAndStashing {
  public static void main(String[] args) throws Exception  {

	  final ActorSystem system = ActorSystem.create("myActorSystem");		
	  final ActorRef myActor =  system.actorOf(Props.create(ActorWithBehavioursAndStashing.class), "myActor");

	  /* 
	   * The following two msgs are not handled in the initial behaviour.
	   * However they are not lost, they are stashed
	   *  
	   */	  
	  myActor.tell(new MsgProtocol.MsgOne(), null);  
	  myActor.tell(new MsgProtocol.MsgTwo(), null);  
	  
	  /* Give time to the logging system to setup */
	  Thread.sleep(1000);

	  /* 
	   * The next msg will cause a transition to the new behaviour, 
	   * where the stashed msgs will be unstashed and processed.  
	   */
	  myActor.tell(new MsgProtocol.MsgZero(), null); 
  
	  System.out.println("done");
  }
}
