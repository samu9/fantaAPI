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

        List<Object> list = this.g.V().has("id giocatore").order().by("nome").values("id giocatore").toList();
        Player[] allPlayers = new Player[list.size()];
        for(int i = 0; i < list.size(); i++){
            allPlayers[i] = getPlayerById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return allPlayers;
    }


    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public Player getPlayerById(@PathVariable long id) {

        Path p = this.g.V().has("id giocatore", id).as("player")
                .out("gioca per").as("team")
                .in("gioca per").has("id giocatore",id).out("è assistito da").as("prosecutor")
                .path().next();

        Vertex player = p.get("player");
        Vertex team = p.get("team");
        Vertex prosecutor = p.get("prosecutor");

        PlayerStats[] stats = new PlayerStats[1];
        stats[0] = new PlayerStats("2017/2018","Juventus",3,5,1,3,20,20.1,1.1);

        String name = (String)player.property("nome").value();
        String birthplace = (String)player.property("luogo nascita").value();
        LocalDate birthdate = LocalDate.parse((String)player.property("data nascita").value());
        long age = YEARS.between(birthdate, today);
        String height = (String)player.property("altezza").value();
        String position = (String)player.property("ruolo").value();
        String mainfoot = (String)player.property("piede").value();
        String img = (String)player.property("img").value();

        String teamName = (String)team.property("nome").value();
        String prosecutorName = (String)prosecutor.property("nome").value();
        return new Player(id,name,birthplace,birthdate,age,height,position,mainfoot,stats, teamName, prosecutorName,img);
    }
}
