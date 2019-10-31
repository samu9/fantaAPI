package Fanta;

public class PlayerStats {
    private String season;
    private String team;
    private long goals;
    private long assists;
    private long redCards;
    private long yellowCards;
    private double average;
    private double fantamedia;
    private double variance;

    public PlayerStats(String season, String team, long goals, long assists, long redCards, long yellowCards, double average, double fantamedia, double variance){
        this.season = season;
        this.team = team;
        this.goals = goals;
        this.assists = assists;
        this.redCards = redCards;
        this.yellowCards = yellowCards;
        this.average = average;
        this.fantamedia = fantamedia;
        this.variance = variance;
    }

    public String getSeason() {
        return season;
    }

    public String getTeam() {
        return team;
    }

    public long getGoals() {
        return goals;
    }

    public long getAssists() {
        return assists;
    }

    public long getRedCards() {
        return redCards;
    }

    public long getYellowCards() {
        return yellowCards;
    }

    public double getAverage() {
        return average;
    }

    public double getFantamedia() {
        return fantamedia;
    }

    public double getVariance() {
        return variance;
    }
}
