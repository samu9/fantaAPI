package Controllers;

import DAOs.ProsecutorDAO;
import Mappers.ProsecutorMapper;
import Models.Player;
import Models.Prosecutor;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProsecutorController extends Controller {

    private ProsecutorMapper mapper = new ProsecutorMapper();
    private ProsecutorDAO dao = new ProsecutorDAO();


    @RequestMapping(value = "/prosecutor/", method = RequestMethod.GET)
    public Prosecutor[] getAllStadiums(){
        List<Object> list = dao.getIdList("prosecutor id");
        Prosecutor[] result = new Prosecutor[list.size()];

        for(int i = 0; i < list.size(); i++){
            result[i] = this.getStadiumById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return result;
    }

    @RequestMapping(value = "/prosecutor/{id}", method = RequestMethod.GET)
    public Prosecutor getStadiumById(@PathVariable long id){
        Vertex prosecutor = dao.getVertexById("prosecutor id", id);

        return mapper.VertexToEntity(prosecutor);
    }

    @RequestMapping(value = "/prosecutor/{id}/player/", method = RequestMethod.GET)
    public Player[] getAssistedPlayers(@PathVariable long id){
        PlayerController playerController = new PlayerController();
        List<Object> list = dao.getListInValues(id, "prosecutor id","is assisted by", "player id");

        return playerController.getPlayersByIdList(list);
    }

}
