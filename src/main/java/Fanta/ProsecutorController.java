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

import java.util.List;

@RestController
public class ProsecutorController {
    private final JanusGraph graph;
    private final GraphTraversalSource g;

    public ProsecutorController(){
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }

    @RequestMapping(value = "/prosecutor/", method = RequestMethod.GET)
    public Prosecutor[] getAllStadiums(){
        List<Object> list = this.g.V().hasLabel("prosecutor").order().by("name").values("prosecutor id").toList();

        Prosecutor[] result = new Prosecutor[list.size()];

        for(int i = 0; i < list.size(); i++){
            result[i] = this.getStadiumById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return result;
    }

    @RequestMapping(value = "/prosecutor/{id}", method = RequestMethod.GET)
    public Prosecutor getStadiumById(@PathVariable long id){
        Path p = this.g.V().has("prosecutor id", id).as("prosecutor").in("is assisted by").as("player").path().next();
        Vertex prosecutor = p.get("prosecutor");
        Vertex player = p.get("player");

        String name = (String)prosecutor.property("name").value();


        String playerName = (String)player.property("name").value();
        long playerId = Long.parseLong(String.valueOf(player.property("player id").value()));

        Prosecutor result = new Prosecutor(id,name,playerName,playerId);
        return result;
    }
}
