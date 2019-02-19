package screens.ranking;



import javafx.beans.property.SimpleStringProperty;

public class scoreTable {
    private final SimpleStringProperty username;
    private final SimpleStringProperty score;



    public scoreTable(String username, String score) {
        this.username = new SimpleStringProperty(username);
        this.score = new SimpleStringProperty(score);
    }



    public String getUsername() {
        return username.get();
    }



    public SimpleStringProperty usernameProperty() {
        return username;
    }



    public void setUsername(String username) {
        this.username.set(username);
    }



    public String getScore() {
        return score.get();
    }


    public SimpleStringProperty scoreProperty() {
        return score;
    }



    public void setScore(String score) {
        this.score.set(score);
    }
}
