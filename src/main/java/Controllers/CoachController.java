package Controllers;

import Mappers.CoachMapper;
import Models.Coach;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CoachController extends Controller {

    private CoachMapper mapper = new CoachMapper();

    public CoachController(){
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

        return mapper.VertexToEntity(coach,team);
    }
}
