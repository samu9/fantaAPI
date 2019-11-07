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
public class StadiumController {
    private final JanusGraph graph;
    private final GraphTraversalSource g;

    public StadiumController(){
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }

    @RequestMapping(value = "/stadium/", method = RequestMethod.GET)
    public Stadium[] getAllStadiums(){
        List<Object> list = this.g.V().hasLabel("stadium").order().by("name").values("stadium id").toList();

        Stadium[] result = new Stadium[list.size()];

        for(int i = 0; i < list.size(); i++){
            result[i] = this.getStadiumById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return result;
    }

    @RequestMapping(value = "/stadium/{id}", method = RequestMethod.GET)
    public Stadium getStadiumById(@PathVariable long id){
        Path p = this.g.V().has("stadium id", id).as("stadium").in("plays in").as("team").path().next();
        Vertex stadium = p.get("stadium");
        Vertex team = p.get("team");

        String name = (String)stadium.property("name").value();

        String city = (String)stadium.property("city").value();
        long capacity = Long.parseLong(String.valueOf(stadium.property("capacity").value()));
        String img = (String)stadium.property("img").value();
        String teamName = (String)team.property("name").value();
        long teamId = Long.parseLong(String.valueOf(team.property("team id").value()));

        Stadium result = new Stadium(id,name,city,capacity,img,teamName,teamId);
        return result;
    }
}
