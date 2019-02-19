package classes;

import static java.lang.Math.sqrt;

/**
 * @author Marc Ferreiro
 * @version 1.0
 */

public class Hexagon {
    double [] pointsX;
    double [] pointsY;
    double radius;
    double side;

    /**
     *
     * @param positionX
     * @param positionY
     * @param height
     */
    public Hexagon(double positionX, double positionY, double height){
        side = getSide(height);
        radius = getRadius(side);
        pointsX = new double[6];
        pointsY = new double[6];
        //     X                                                Y
        pointsX[0] = positionX + radius;                        pointsY[0] = positionY-side/2;
        pointsX[1] = positionX + radius * 2;                    pointsY[1] = positionY;
        pointsX[2] = positionX + radius * 2;                    pointsY[2] = positionY + side;
        pointsX[3] = positionX + radius;                        pointsY[3] = positionY + side + side/2;
        pointsX[4] = positionX;                                 pointsY[4] = positionY + side;
        pointsX[5] = positionX;                                 pointsY[5] = positionY;
    }

    /**
     *
     * @param height
     * @return Returns side length
     */
    public static double getSide(double height) {
        return (height/sqrt(3));
    }

    /**
     *
     * @param side
     * @return Returns radius length
     */
    public static double getRadius(double side) {
        return ((sqrt(3)/2)*side);
    }

    /**
     *
     * @param height
     * @return Returns half the side
     */

    public static double getOutOfBounds(double height) {
        double side = getSide(height);
        return side/2;
    }

    /**
     *
     * @return Returns pointsX
     */

    public double [] getPointsX(){
        return pointsX;
    }

    /**
     * @return Returns pointsY
     */

    public double [] getPointsY(){
        return pointsY;
    }

    /**
     *
     * @return Returns 6 (as many points as hexagon)
     */

    public int getPoints() {
        return 6;
    }
}
