package Fanta;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.YEARS;

@RestController
public class TeamController {
    JanusGraph graph;
    GraphTraversalSource g;

    public TeamController(){
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }

    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public Team[] getAllTeams() throws java.io.IOException {
        List<Object> list = this.g.V().has("team id").order().by("name").values("team id").toList();

        Team[] allTeams = new Team[list.size()];
        for(int i = 0; i < list.size(); i++){
            allTeams[i] = getTeamById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return allTeams;
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public Team getTeamById(@PathVariable long id) throws java.lang.IllegalStateException {
        Path p = this.g.V().has("team id", id).as("team").in("trains").as("coach")
                .out("trains").has("team id",id).in("owns").as("president")
                .out("owns").has("team id",id).out("plays in").as("stadium").path().next();

        Vertex team = p.get("team");
        Vertex coach = p.get("coach");
        Vertex president = p.get("president");
        Vertex stadium = p.get("stadium");

        LocalDate today = LocalDate.now();

        double total = 0;
        List<Object> list = this.g.V(team).in("plays for").values("birthdate").toList();

        for(Object o : list){
            LocalDate birthdate = LocalDate.parse((String)o.toString());
            double age = YEARS.between(birthdate, today);
            total += age;
        }

        String name = (String) team.property("name").value();
        long players = list.size();
        double avgAge = Math.round(total/list.size() * 100.00) / 100.00;
        String logo = "";
        try {
            logo = (String) team.property("logo").value();
        }catch (Exception e) {
            System.out.println("Errore squadra" + id);
        }
        String coachName = (String) coach.property("name").value();
        long coachId = Long.parseLong(String.valueOf(coach.property("coach id").value()));
        String presidentName = (String) president.property("name").value();
        long presidentId = Long.parseLong(String.valueOf(president.property("president id").value()));
        String stadiumName = (String) stadium.property("name").value();
        long stadiumId = Long.parseLong(String.valueOf(stadium.property("stadium id").value()));

        Team result = new Team(id, name, players, avgAge, logo, coachName, coachId, presidentName, presidentId, stadiumName, stadiumId);
        return result;

    }

}
