package Fanta;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final JanusGraph graph;
    private final GraphTraversalSource g;
    private final JanusGraphManagement management;

    public UserController() {
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
        this.management = graph.openManagement();
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public User createUser(@RequestParam(value="username",required = true) String username){
        Vertex v = this.g.addV("user").property("username",username).property("user id", 1).property("email","prova@gmail.com").next();
        System.out.println(v);
        User result = new User(1,username,"");
        return result;
    }

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public User[] getUsers(){
        List<Vertex> l = this.g.V().has("user id").toList();
        System.out.println(l);
        User[] result = new User[l.size()];
        for(int i = 0; i < l.size(); i++){
            System.out.println(l.get(i));
            result[i] = new User(i,(String)l.get(i).property("username").value(),(String)l.get(i).property("email").value());
        }
        return result;
    }

    @RequestMapping(value = "/user/{id}/fantateam", method = RequestMethod.POST)
    public FantaTeam createFantaTeam(@PathVariable long id, @RequestParam(value="teamName",required = true) String teamName){
        System.out.println(id + " " + teamName);
        FantaTeam result = new FantaTeam(1,teamName,0,0,"","",id);
        return result;
    }
}
