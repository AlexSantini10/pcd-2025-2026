package pcd.lab11.actors.step5_gui;
import org.apache.pekko.actor.AbstractActor;

public class ViewActor extends AbstractActor {
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(PressedMsg.class, msg -> {
			System.out.println("Pressed!");
		}).build();
	}
}
