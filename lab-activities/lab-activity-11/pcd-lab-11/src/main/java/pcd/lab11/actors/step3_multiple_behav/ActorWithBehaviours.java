package pcd.lab11.actors.step3_multiple_behav;
import static pcd.lab11.actors.step3_multiple_behav.MsgProtocol.*;

import org.apache.pekko.actor.*;

public class ActorWithBehaviours extends AbstractActor {

	private int state;
	
	/* Base behaviour */
	
	public ActorWithBehaviours() {
		state = 0;
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(MsgZero.class,this::onMsgZero)
				.build();
	}

	private void onMsgZero(MsgZero msg) {
		log("msgZero - state: " + state);
		state++;
		this.getContext().become(receiverBehaviourA());
	}


	/* Behaviour A */

	public Receive receiverBehaviourA() {
		return receiveBuilder()
				.match(MsgOne.class,this::onMsgOne)
				.build();
	}
	
	private void onMsgOne(MsgOne msg) {
		log("msgOne - state: " + state);	
		state++;

		this.getContext().become(receiverBehaviourB());
	}
	
	/* Behaviour B */
	
	public Receive receiverBehaviourB() {
		return receiveBuilder()
				.match(MsgTwo.class,this::onMsgTwo)
				.build();
	}

	private void onMsgTwo(MsgTwo msg) {
		log("msgTwo - state: " + state);		
		this.getContext().stop(this.getSelf());
	}


	private void log(String msg) {
		System.out.println("[ActorWithBehaviour] " + msg);
	}


	
}
