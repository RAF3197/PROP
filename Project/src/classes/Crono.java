package classes;


import classes.enums.TCronState;

import java.io.Serializable;


/**
 * @author Bernat Felip
 */

public class Crono implements Serializable {
    private Long startTime;
    private Long stopTime;
    private TCronState stateCron;

    /**
     *
     */

    public Crono() {
        this.stateCron = TCronState.STOPPED;
        startTime = new Long(0);
        stopTime = new Long(0);
    }

    /**
     *
     */

    public void startCron() {
        startTime = System.currentTimeMillis();
        stateCron = TCronState.RUNNING;
    }

    /**
     *
     */

    public void pauseCron() {
        stopTime = System.currentTimeMillis();
        stateCron = TCronState.STOPPED;
    }

    /**
     *
     */

    public void restartCron() {
        startTime += (System.currentTimeMillis()-stopTime);
        stateCron = TCronState.RUNNING;
    }

    /**
     *
     * @return Returns elapsed time
     */

    public Long getTime() {
        if(stateCron == TCronState.RUNNING){
            return System.currentTimeMillis() - startTime;
        }
        else return stopTime-startTime;
    }
}
