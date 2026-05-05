package pcd.lab11.actors.step1_pingpong;

import org.apache.pekko.actor.ActorRef;

public interface PingerPongerProtocol {
	static public record PongMsg(long count, ActorRef ponger) {}
	static public record PingMsg (long count, ActorRef pinger) {}
	static public record BootMsg (ActorRef ponger) {}
}
