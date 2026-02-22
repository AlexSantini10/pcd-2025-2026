package pcd.lab02.bballs;

import java.util.*;

public class Context {

    private Boundary bounds;
    private ArrayList<BallAgent> balls;
    private int id = 0;
    
    public Context(){
        bounds = new Boundary(-1.0,-1.0,1.0,1.0);
        balls = new ArrayList<BallAgent>();
    } 
    
    public void createNewBall(){
    	id++;
        BallAgent agent = new BallAgent(id, this);
        balls.add(agent);
        agent.start();
    }
    
    public void removeBall(){
        if (balls.size()>0){
            BallAgent ball = (BallAgent)balls.get(0);
            balls.remove(ball);
            ball.notifyStopped();
       	}
    }
    
    /**
     * 
     * Possible races?
     * 
     * @return
     */
    public P2d[] getPositions(){
    	P2d[] array = new P2d[balls.size()];
        for (int i=0; i<array.length; i++){
            array[i] = balls.get(i).getPos();
        }
        return array;
    }
    
    public  Boundary getBounds(){
        return bounds;
    }
}
