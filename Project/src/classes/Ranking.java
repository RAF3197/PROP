package classes;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Ricard Abril
 */

public class Ranking {
    private ArrayList<RankingItem> ranking = new ArrayList<>();

    /**
     *
     * @param ranking
     */

    public Ranking(ArrayList<RankingItem> ranking) {
        this.ranking = ranking;
    }

    /**
     *
     */

    public Ranking(){}

    /**
     *
     * @return Returns a array of Rankings
     */

    public ArrayList<RankingItem> getRanking() {
        return this.ranking;
    }

    /**
     *
     * @param IdUSer
     * @return Returns true if the ranking element it was removed successfully
     */

    public boolean removeRankingItem(int IdUSer){
        RankingItem I = findRankingItem(IdUSer);
        if(I != null)return this.ranking.remove(I);
        return false;
    }

    /**
     *
     * @param a
     * @return Returns true if the ranking element it was added successfully
     */

    public boolean addRankingItem(RankingItem a){
        return this.ranking.add(a);
    }

    /**
     *
     * @param IdUser
     * @return Returns true if the ranking element it was find successfully
     */

    private RankingItem findRankingItem(int IdUser){
        for (Iterator<RankingItem> i = this.ranking.iterator() ; i.hasNext();) {
            RankingItem item = i.next();
            if (item.getUser() == IdUser) {
                return item;
            }
        }
        return null;
    }

    /**
     *
     * @return Returns ranking in string format
     */
    @Override
    public String toString() {
        String ret = "";
        for (Iterator<RankingItem> i = this.ranking.iterator() ; i.hasNext();) {
            RankingItem item = i.next();
            ret +="User: " + item.getUser() + "\t" + "Puntuacio: " + item.getPuntuacio() + "\n";
        }
        return ret;
    }
}