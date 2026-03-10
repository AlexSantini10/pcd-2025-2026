package pcd.lab04.deadlock_obs;

public interface Observer {
	void notifyStateChanged(Observed obs);
}
