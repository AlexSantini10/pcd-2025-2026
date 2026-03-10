package pcd.lab04.ex01_synchwithsem;

public class Ponger extends ActiveComponent {
	
	public Ponger() {
	}	
	
	public void run() {
		while (true) {
			println("pong");
		}
	}
}