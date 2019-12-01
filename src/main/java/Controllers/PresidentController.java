package Controllers;

import DAOs.PresidentDAO;
import Mappers.PresidentMapper;
import Models.President;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PresidentController extends Controller {
    PresidentMapper mapper = new PresidentMapper();
    PresidentDAO dao = new PresidentDAO();
    public PresidentController(){
    }

    @RequestMapping(value = "/president/", method = RequestMethod.GET)
    public President[] getAllPresidents(){
        List<Object> list = dao.getIdList("president id");

        President[] result = new President[list.size()];
        for(int i = 0; i < list.size(); i++){
            result[i] = this.getPresidentById(Long.parseLong(String.valueOf(list.get(i))));
        }
        dao.commit();
        return result;
    }

    @RequestMapping(value = "/president/{id}", method = RequestMethod.GET)
    public President getPresidentById(@PathVariable long id){
        Path p = dao.getPresidentPathById(id);
        Vertex president = p.get("president");
        Vertex team = p.get("team");

        return mapper.VertexToEntity(president, team);
    }
}
