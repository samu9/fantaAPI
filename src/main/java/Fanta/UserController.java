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

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public User[] getUsers(){
        List<Vertex> l = this.g.V().has("user id").toList();

        User[] result = new User[l.size()];
        for(int i = 0; i < l.size(); i++){
            result[i] = new User(i,(String)l.get(i).property("username").value(),(String)l.get(i).property("email").value(),null);
        }
        return result;
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public User createUser(@RequestParam(value="username") String username, @RequestParam(value="email", required = true) String email){
        long newId = this.g.V().hasLabel("user").count().next() + 1; //provvisorio
        Vertex v = this.g.addV("user").property("username",username).property("user id", newId).property("email",email).next();
        this.g.tx().commit();
        User result = new User(newId,username,email,null);
        return result;
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable long id){
        Vertex user = this.g.V().has("user id", id).next();
        List<Object> t = this.g.V(user).out("fanta owns").values("fantateam id").toList();

        String username = (String)user.property("username").value();
        String email = (String)user.property("email").value();

        FantaTeamController fc = new FantaTeamController();
        FantaTeam[] teams = new FantaTeam[t.size()];

        for(int i = 0; i < t.size(); i++){
            teams[i] = fc.getFantaTeamById(Long.parseLong(String.valueOf(t.get(i))));
        }
        User result = new User(id,username,email, teams);
        return result;
    }

}
