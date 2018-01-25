import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InteractiveDPViewer extends JComponent{
    //a value that determines the size of the bobs in the visualisation.
    private int BOB_RADIUS = 10;

    public int getBOB_RADIUS() {
        return BOB_RADIUS;
    }

    public void setBOB_RADIUS(int BOB_RADIUS) {
        this.BOB_RADIUS = BOB_RADIUS;
    }

    private InteractiveDP controller;

    /**
     * Field for the double pendulum that we wish to visualise.
     */
    private DoublePendulum dp;


    /**
     * A circular draggable region, with centre (x,y) and radius r
     */
    private static class Hotspot{
        int x, y, r;
    }

    //we will always have 4 hotspots
    private Hotspot[] hotspot = new Hotspot[4];
    private int activeHotspot = -1; //currently active hotspot. -1 represents no current hotspot
    private Point displacement = new Point(0 , 0); //displacement of current hotspot

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
     * A class that handles mouse actions
     */

    private MouseAdapter mouseFollower = new MouseAdapter(){
        //start of the drag if it is in progress
        private Point lastClick = null;

        @Override
                public void mousePressed(MouseEvent e){
            lastClick = e.getPoint();
            //hotspots are processed in reverse order so that the point that is plotted last is detected first
            for (int i = hotspot.length; i >= 0; i--){

                int dx = lastClick.x - hotspot[i].x;
                int dy = lastClick.y -hotspot[i].y;

                //a valid movement is detected and the current hotspot is set as active
                if (dx*dx + dy*dy <= hotspot[i].r * hotspot[i].r){
                    activeHotspot = i;
                    displacement.x = displacement.y = 0;
                    addMouseListener(mouseFollower);
                    break;
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e){
                    //do nothing if there is no current hotspot
            if (activeHotspot == -1) return;

            //get the current location of the mouse
            Point p = e.getPoint();
            //work out the displacement
            displacement.x = p.x - lastClick.x;
            displacement.y = p.y - lastClick.y;

            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e){
            //again do nothing if no current hotspot
            if (activeHotspot == -1) return;

            //get the current location of the mouse
            Point p = e.getPoint();
            //work out the displacement
            displacement.x = p.x - lastClick.x;
            displacement.y = p.y - lastClick.y;

            hotspotDragEnded();
            removeMouseListener(mouseFollower);
            //reset active hotspot to none
            activeHotspot = -1;
            repaint();
        }
    };

    /**
     * a method that handles the events once a hotspot has finished being dragged.
     */
    private void hotspotDragEnded(){
        
    }

    /**
     * A helper function to draw an arrow to display a directional vector of the velocity of a bob.
     *
     * @param g the current drawing component
     * @param x the x coord of the pendulum.
     * @param y the y coord of the pendulum.
     * @param mag the magnitude of the vector to display.
     */

    private void drawVector(Graphics g, double x, double y, double mag){

        mag/=10;

        g.fillRect((int) Math.round(x+mag),(int) Math.round(y+mag),(int)Math.round( 5*mag), (int)Math.round(2*mag));

        //draw the triangle using polygons
        Polygon triangle = new Polygon();

        //add the three points of the triangle in series
        triangle.addPoint((int) Math.round(x+5*mag), (int)(y + 1.5*mag));
        triangle.addPoint((int) Math.round(x+5*mag), (int)(y - 1.5*mag));
        triangle.addPoint((int)Math.round(x+5*mag+2.6), (int) y);

        g.fillPolygon(triangle);


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
        Particle3D[] state = controller.getDpState();



        //draw the string connecting the first mass to the origin, and then to the second mass
        g.setColor(Color.BLUE);
        g.drawLine((int) Ox, (int) Oy,(int) (Ox + Sx*state[0].getPosition().getX()), (int) (Oy + Sy*state[0].getPosition().getY()));
        g.drawLine((int) (Ox + Sx*state[0].getPosition().getX()),
                (int) (Oy + Sy*state[0].getPosition().getY()),
                (int) (Ox + Sx*state[1].getPosition().getX()),
                (int) (Oy + Sy*state[1].getPosition().getY())
        );

        //add bobs
        g.setColor(Color.red);
        drawCenteredCircle(g, (int) (Ox+Sx*state[0].getPosition().getX()), (int) (Oy+Sy*state[0].getPosition().getY()), BOB_RADIUS);
        drawCenteredCircle(g, (int) (Ox+Sx*state[1].getPosition().getX()), (int) (Oy+Sy*state[1].getPosition().getY()), BOB_RADIUS);

    }




}
