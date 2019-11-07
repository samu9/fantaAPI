package Fanta;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PresidentController {
    private final JanusGraph graph;
    private final GraphTraversalSource g;

    public PresidentController(){
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }

    @RequestMapping(value = "/president/", method = RequestMethod.GET)
    public President[] getAllPresidents(){
        List<Object> list = this.g.V().hasLabel("president").order().by("name").values("president id").toList();

        President[] result = new President[list.size()];

        for(int i = 0; i < list.size(); i++){
            result[i] = this.getPresidentById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return result;
    }

    @RequestMapping(value = "/president/{id}", method = RequestMethod.GET)
    public President getPresidentById(@PathVariable long id){
        Path p = this.g.V().has("president id", id).as("president").out("owns").as("team").path().next();
        Vertex president = p.get("president");
        Vertex team = p.get("team");

        String name = (String)president.property("name").value();
        LocalDate birthdate = LocalDate.parse((String)president.property("birthdate").value());
        String birthplace = (String)president.property("birthplace").value();
        String nationality = (String)president.property("nationality").value();
        String ownedTeamName = (String)team.property("name").value();
        long ownedTeamId = Long.parseLong(String.valueOf(team.property("team id").value()));

        President result = new President(id,name,birthdate,birthplace,nationality,ownedTeamName,ownedTeamId);
        return result;
    }
}
