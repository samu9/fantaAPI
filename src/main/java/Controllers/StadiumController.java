package Controllers;


import Mappers.StadiumMapper;
import Models.Stadium;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StadiumController extends Controller{

    private StadiumMapper mapper = new StadiumMapper();

    public StadiumController(){

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

        return mapper.VertexToEntity(stadium,team);
    }
}
