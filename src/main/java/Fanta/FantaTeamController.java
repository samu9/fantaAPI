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
public class FantaTeamController{
    private final JanusGraph graph;
    private final GraphTraversalSource g;

    public FantaTeamController(){
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }

    @RequestMapping(value = "/fantateam/", method = RequestMethod.GET)
    public FantaTeam[] getAllFantateams(){
        List<Object> list = this.g.V().has("fantateam id").order().by("name").values("fantateam id").toList();

        FantaTeam[] result = new FantaTeam[list.size()];

        for(int i = 0; i < list.size(); i++){
            result[i] = this.getFantaTeamById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return result;
    }
    @RequestMapping(value = "/fantateam/", method = RequestMethod.POST)
    public FantaTeam createFantaTeam(@RequestParam(value="name",required = true) String name, @RequestParam(value="userId") long userId ) throws java.io.IOException {
        Vertex user = this.g.V().has("user id", userId).next();

        long newId = this.g.V().hasLabel("fantateam").count().next() + 1;
        Vertex team = this.g.addV("fantateam").property("fantateam id", newId).property("name",name).next();
        g.V(user).as("a").V(team).addE("fanta owns").from("a").next();
        this.g.tx().commit();

        return null;
    }
    @RequestMapping(value = "/fantateam/", method = RequestMethod.DELETE)
    public boolean deleteFantaTeam(){
        this.g.V().has("fantateam id").drop().iterate();
        this.g.tx().commit();
        return true;
    }

    @RequestMapping(value = "/fantateam/{id}", method = RequestMethod.GET)
    public FantaTeam getFantaTeamById(@PathVariable long id ){
        Path p = this.g.V().has("fantateam id", id).as("team").in("fanta owns").as("user")
                .path().next();
        Vertex team = p.get("team");
        Vertex user = p.get("user");

        LocalDate today = LocalDate.now();

        double total = 0;
        List<Object> list = this.g.V(team).in("fanta plays for").values("birthdate").toList();

        for(Object o : list){
            LocalDate birthdate = LocalDate.parse((String)o.toString());
            double age = YEARS.between(birthdate, today);
            total += age;
        }

        String name = (String) team.property("name").value();
        long players = list.size();
        double avgAge = Math.round(total/list.size() * 100.00) / 100.00;
        String logo = "";
        String username = (String)user.property("username").value();
        long ownerId = Long.parseLong(String.valueOf(user.property("user id").value()));

        FantaTeam result = new FantaTeam(id, name, players, avgAge, logo, username, ownerId);
        return result;
    }

    @RequestMapping(value = "/fantateam/{teamId}/player", method = RequestMethod.GET)
    public Player[] getPlayers(@PathVariable long teamId ){
        Vertex team = this.g.V().has("fantateam id", teamId).next();
        List<Object> list = this.g.V(team).in("fanta plays for").values("player id").toList();

        PlayerController pc = new PlayerController();
        Player[] result = new Player[list.size()];
        for(int i = 0; i < list.size(); i++){
            result[i] = pc.getPlayerById(Long.parseLong(String.valueOf(list.get(i))),true);
        }
        return result;
    }

    @RequestMapping(value = "/fantateam/{teamId}/player", method = RequestMethod.POST)
    public boolean addPlayer(@RequestParam(value="playerId") String playerId, @PathVariable long teamId ) throws java.io.IOException {
        Vertex team = this.g.V().has("fantateam id", teamId).next();
        Vertex player = this.g.V().has("player id", playerId).next();
        this.g.V(player).as("a").V(team).addE("fanta plays for").from("a").next();
        this.g.tx().commit();

        return true;
    }

    @RequestMapping(value = "/fantateam/{teamId}/player", method = RequestMethod.DELETE)
    public boolean removePlayer(@RequestParam(value="playerId") String playerId, @PathVariable long teamId ) throws java.io.IOException {
        Vertex player = this.g.V().has("player id", playerId).next();
        this.g.V(player).out("fanta plays for").as("e").in("fanta plays for").has("fantateam id", teamId).select("e").drop();
        this.g.tx().commit();

        return true;
    }

}
