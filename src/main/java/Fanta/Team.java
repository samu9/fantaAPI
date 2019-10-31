package Fanta;

public class Team {
    protected final long id;
    protected final String name;
    protected final long playersNum;
    protected final double avgAge;
    protected final String logo;
    protected final String coach;

    public Team(){
        this.id = 0;
        this.name = "";
        this.playersNum = 0;
        this.avgAge = 0;
        this.logo = "";
        this.coach = "";
    }
    public Team(long id, String name, long players, double avgAge, String logo, String coach){
        this.id = id;
        this.name = name;
        this.playersNum = players;
        this.avgAge = avgAge;
        this.logo = logo;
        this.coach = coach;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPlayersNum() {
        return playersNum;
    }

    public double getAvgAge() {
        return avgAge;
    }

    public String getLogo() {
        return logo;
    }

    public String getCoach() {
        return coach;
    }
}
