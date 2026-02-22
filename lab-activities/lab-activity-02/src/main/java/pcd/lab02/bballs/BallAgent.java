package pcd.lab02.bballs;

import java.util.*;

public class BallAgent extends BasicAgent {
    
    private P2d pos;
    private V2d vel;
    private double speed;
    private Context ctx;
    private long lastUpdate;
    private static final int DELAY_MS = 5;
    
    public BallAgent(int id, Context ctx){
    	super("BallAgent-"+id);
    	this.ctx = ctx;
        pos = new P2d(0,0);
        Random rand = new Random(System.currentTimeMillis());
        double dx = rand.nextDouble();
        vel = new V2d(dx,Math.sqrt(1-dx*dx));
        speed = rand.nextDouble()*3;
    }

    public void run() {
        //log("INIT: vel "+vel+"speed "+speed);
        try {
            lastUpdate = System.currentTimeMillis();
	        while (!hasBeenStopped()){
	            updatePos();
	            Thread.sleep(DELAY_MS);	
	        }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void updatePos(){
        long time = System.currentTimeMillis();
        long dt = time - lastUpdate;
        lastUpdate = time;
        pos = pos.sum(vel.mul(speed*dt*0.001));
        applyConstraints();
    }

    private void applyConstraints(){
        Boundary bounds = ctx.getBounds();
        if (pos.x > bounds.getX1()){
            pos.x = bounds.getX1();
            vel.x = -vel.x;
        } else if (pos.x < bounds.getX0()){
            pos.x = bounds.getX0();
            vel.x = -vel.x;
        } else if (pos.y > bounds.getY1()){
            pos.y = bounds.getY1();
            vel.y = -vel.y;
        } else if (pos.y < bounds.getY0()){
            pos.y = bounds.getY0();
            vel.y = -vel.y;
        }
    }
    
    /**
     * 
     * Possible races?
     * 
     * @return
     */

    public P2d getPos(){        
    	return new P2d(pos.x,pos.y);
    }

}
