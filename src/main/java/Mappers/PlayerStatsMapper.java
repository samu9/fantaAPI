package Mappers;

import DAOs.BaseDAO;
import Models.PlayerStats;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class PlayerStatsMapper extends Mapper{
    private BaseDAO dao = new BaseDAO();

    public PlayerStats VertexToStats(Vertex stat, Vertex team){
        String year = this.mapString(stat,"year");
        String seasonTeam = "";
        try {
            seasonTeam = this.mapName(team);
        } catch (Exception e){
            seasonTeam = this.mapString(stat,"team");
        }
        long playedMatches = this.mapLong(stat,"played matches");
        long goals = this.mapLong(stat,"scored goals");
        long assists = this.mapLong(stat,"assists");
        long concededGoals = this.mapLong(stat,"conceded goals");
        long ownGoals = this.mapLong(stat,"own goals");
        long redCards = this.mapLong(stat,"red cards");
        long yellowCards = this.mapLong(stat,"yellow cards");
        double average = this.mapDouble(stat,"average");
        double fantaAverage = this.mapDouble(stat,"fanta average");
        double avgGaussianRating = this.mapDouble(stat,"gauss average");
        double avgGaussianFantaRating = this.mapDouble(stat,"gauss fanta average");

        return new PlayerStats(year, seasonTeam, playedMatches, goals, assists, concededGoals, ownGoals, redCards, yellowCards, average, fantaAverage,avgGaussianRating,avgGaussianFantaRating);
    }
}
