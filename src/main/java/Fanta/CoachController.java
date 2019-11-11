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
public class CoachController {
    JanusGraph graph;
    GraphTraversalSource g;

    public CoachController(){
        this.graph = JanusGraphFactory.open("conf/janusgraph-cassandra-elasticsearch.properties");
        this.g = graph.traversal();
    }

    @RequestMapping(value = "/coach/", method = RequestMethod.GET)
    public Coach[] getAllCoaches(){
        List<Object> list = this.g.V().has("coach id").order().by("name").values("coach id").toList();

        Coach[] allCoaches = new Coach[list.size()];
        for(int i = 0; i < list.size(); i++){
            allCoaches[i] = getCoachById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return allCoaches;
    }

    @RequestMapping(value = "/coach/{id}", method = RequestMethod.GET)
    public  Coach getCoachById(@PathVariable long id){
        Path p = this.g.V().has("coach id", id).as("coach")
                .out("trains").as("team").path().next();

        Vertex coach = p.get("coach");
        Vertex team = p.get("team");

        String name = (String)coach.property("name").value();

        // vanno corrette le date, ora sono timestap con l'ora
        String tmp = (String)coach.property("birthdate").value();
        LocalDate birthdate = LocalDate.parse(tmp.split(" ")[0]);

        String birthplace = (String)coach.property("birthplace").value();
        String nationality = (String)coach.property("nationality").value();
        String module = (String)coach.property("module").value();
        String teamName = (String)team.property("name").value();
        long teamId = Long.parseLong(String.valueOf(team.property("team id").value()));

        Coach result = new Coach(id,name,birthdate,birthplace,nationality,module,teamName, teamId);
        return result;
    }
}
