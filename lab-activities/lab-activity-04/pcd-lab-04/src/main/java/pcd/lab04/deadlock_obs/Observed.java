package pcd.lab04.deadlock_obs;

public interface Observed {
	int getState();
	void register(Observer obj);
}

