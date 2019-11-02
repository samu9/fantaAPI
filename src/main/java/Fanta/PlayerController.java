package Fanta;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.YEARS;

@RestController
public class PlayerController {
    JanusGraph graph;
    GraphTraversalSource g;
    LocalDate today = LocalDate.now();

    public PlayerController() {
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }

    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public Player[] getAllPlayers(){

        List<Object> list = this.g.V().has("player id").order().by("name").values("player id").toList();
        Player[] allPlayers = new Player[list.size()];
        for(int i = 0; i < list.size(); i++){
            allPlayers[i] = getPlayerById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return allPlayers;
    }


    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public Player getPlayerById(@PathVariable long id) {

        Path p = this.g.V().has("player id", id).as("player")
                .out("plays for").as("team")
                .in("plays for").has("player id",id).out("is assisted by").as("prosecutor")
                .path().next();

        Vertex player = p.get("player");
        Vertex team = p.get("team");
        Vertex prosecutor = p.get("prosecutor");

        List<Vertex> statList = this.g.V().has("player id", id).out("stats").toList();
        PlayerStats[] stats = new PlayerStats[statList.size()];
        for(int i = 0; i < statList.size(); i++){
            Vertex v = statList.get(i);
            String year = (String)v.property("year").value();
            long playedMatches = Long.parseLong(String.valueOf(v.property("played matches").value()));
            long goals = Long.parseLong(String.valueOf(v.property("scored goals").value()));
            long assists = Long.parseLong(String.valueOf(v.property("assists").value()));
            long concededGoals = Long.parseLong(String.valueOf(v.property("conceded goals").value()));
            long ownGoals = Long.parseLong(String.valueOf(v.property("own goals").value()));
            long redCards = Long.parseLong(String.valueOf(v.property("red cards").value()));
            long yellowCards = Long.parseLong(String.valueOf(v.property("yellow cards").value()));
            double average = Double.parseDouble(String.valueOf(v.property("average").value()));
            double fantaAverage = Double.parseDouble(String.valueOf(v.property("fanta average").value()));
            stats[i] = new PlayerStats(year,"Team",playedMatches,goals,assists,concededGoals,ownGoals,redCards,yellowCards,average,fantaAverage,0);

        }


        String name = (String)player.property("name").value();
        String birthplace = (String)player.property("birthplace").value();
        LocalDate birthdate = LocalDate.parse((String)player.property("birthdate").value());
        long age = YEARS.between(birthdate, today);
        String height = (String)player.property("height").value();
        String position = (String)player.property("role").value();
        String mainfoot = (String)player.property("mainFoot").value();
        String img = (String)player.property("img").value();
        long quot = Long.parseLong(String.valueOf(player.property("quot").value()));
        String teamName = (String)team.property("name").value();
        String prosecutorName = (String)prosecutor.property("name").value();
        return new Player(id,name,birthplace,birthdate,age,height,position,mainfoot,stats, quot, teamName, prosecutorName,img);
    }
}
