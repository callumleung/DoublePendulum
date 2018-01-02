import javax.swing.*;
import java.awt.*;

/**
 * Created by Callum on 22/12/2017.
 */
public class DPViewer extends JComponent {

    //a value that determines the size of the bobs in the visualisation.
    private int BOB_RADIUS = 10;

    public int getBOB_RADIUS() {
        return BOB_RADIUS;
    }

    public void setBOB_RADIUS(int BOB_RADIUS) {
        this.BOB_RADIUS = BOB_RADIUS;
    }


    /**
     * Field for the double pendulum that we wish to visualise.
     */
    private DoublePendulum dp;

    /**
     * A helper method to draw a centered circle
     *
     * @param g
     * @param x coord of the centre of the circle
     * @param y coord of the centre of the circle
     * @param r the radius of the circle
     */
    private void drawCenteredCircle(Graphics g, int x, int y, int r) {
        x = x - r;
        y = y - r;
        g.fillOval(x,y, 2*r, 2*r);
    }

    /**
     * A method that handles all of the drawing.
     * Called when a component needs drawing or updated.
     *
     * @param g the current drawing component.
     */
    @Override
    protected void paintComponent (Graphics g) {

        //first get component's dimensions
        int width = getWidth();
        int height = getHeight();

        //clear the background if an opaque component
        if(isOpaque()){
            g.setColor(getBackground());
            g.fillRect(0, 0, width, height);
        }

        //set up the coordinate system such that, the real point (x,y)
        // gets mapped to (ox + sx*x, oy + sy*y)
        //(ox, oy) is the origin of graphic and sx and sy are scale factors dependent on
        // the maximum length of the double pendulum.

        //set up ox and oy
        double Ox = (double) width/2.0;
        double Oy = (double) height/2.0;

        //set up the scale factors Sx, Sy
        double Sy = (double) -height/(2*dp.getMaxLength());
        double Sx = -Sy;

        //get the real world positions and notify waiting threads that the positions have been obtained.
        Vector3D p1pos, p2pos;
        synchronized (dp) {
            p1pos = dp.getPendulum1().getPosition();
            p2pos = dp.getPendulum2().getPosition();
            dp.notifyAll();
        }
        //draw the string connecting the first mass to the origin, and then to the second mass
        g.setColor(Color.BLUE);
        g.drawLine((int) Ox, (int) Oy,(int) (Ox + Sx*p1pos.getX()), (int) (Oy + Sy*p1pos.getY()));
        g.drawLine((int) (Ox + Sx*p1pos.getX()),
                (int) (Oy + Sy*p1pos.getY()),
                (int) (Ox + Sx*p2pos.getX()),
                (int) (Oy + Sy*p2pos.getY())
        );

        //add bobs
        g.setColor(Color.red);
        drawCenteredCircle(g, (int) (Ox+Sx*p1pos.getX()), (int) (Oy+Sy*p1pos.getY()), BOB_RADIUS);
        drawCenteredCircle(g, (int) (Ox+Sx*p2pos.getX()), (int) (Oy+Sy*p2pos.getY()), BOB_RADIUS);

    }

    /**
     * a constructor to set up the visualisation with the douple pendulum object passed
     * as an argument to the constructor.
     * @param dpview the double pendulum object to be visualised.
     */
    public DPViewer(DoublePendulum dpview){

        dp = dpview;
        setOpaque(true);
        setBackground(Color.white);

    }

}
