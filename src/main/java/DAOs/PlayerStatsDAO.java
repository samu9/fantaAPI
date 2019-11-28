package DAOs;

import org.apache.tinkerpop.gremlin.structure.Vertex;

public class PlayerStatsDAO extends BaseDAO{

    public Vertex addPlayerStats(String year, String role, long playedMatches, long scoredGoals, long concededGoals,
                                 long ownGoals, long assists, long yellowCards, long redCards, double average,
                                 double fantaAverage, double gaussAverage, double gaussFantaAverage, Vertex teamVertex, String team){
        Vertex stats = this.g.addV("stats")
                .property("year", year)
                .property("row", role).property("played matches", playedMatches)
                .property("average", average)
                .property("fanta average", fantaAverage)
                .property("gauss average", gaussAverage)
                .property("gauss fanta average", gaussFantaAverage)
                .property("scored goals", scoredGoals)
                .property("conceded goals", concededGoals)
                .property("assists", assists).property("yellow cards", yellowCards)
                .property("red cards", redCards).property("own goals", ownGoals).next();
        if(teamVertex != null){
            g.V(stats).as("a").V(team).addE("stats").from("a").next();
        } else {
            stats.property("team", team);
        }
        return stats;
    }
}
