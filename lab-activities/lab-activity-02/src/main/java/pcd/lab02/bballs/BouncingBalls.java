package pcd.lab02.bballs;

import javax.swing.SwingUtilities;

/**
 * Bouncing Ball demo 
 * 
 * - stressing the number of threads
 * - agents with non-terminating behaviour
 * 
 * @author aricci
 */
public class BouncingBalls {

    public static void main(String[] args) {
        
        Context ctx = new Context();
        
        BallViewer viewer = new BallViewer(ctx);
        viewer.start();
      
        ControlPanel control = new ControlPanel(ctx);
        SwingUtilities.invokeLater(() -> {
            control.setVisible(true);
        });

    }
}
