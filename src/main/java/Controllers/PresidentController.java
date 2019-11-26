package Controllers;

import Mappers.PresidentMapper;
import Models.President;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PresidentController extends Controller {
    PresidentMapper mapper = new PresidentMapper();

    public PresidentController(){
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

        return mapper.VertexToEntity(president, team);
    }
}
