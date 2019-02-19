package classes;

import java.io.Serializable;

/**
 * @author Bernat Felip
 */

public class Log implements Serializable {
    /**
     *
     */
    private int x, y;

    /**
     *
     * @param x
     * @param y
     */

    public Log(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return Returns the X coordinate of a number introduced by the player
     */

    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */

    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return Returns the Y coordinate of a number introduced by the player
     */

    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */

    public void setY(int y) {
        this.y = y;
    }

}
