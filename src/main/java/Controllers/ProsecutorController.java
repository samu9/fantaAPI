package Controllers;

import Mappers.ProsecutorMapper;
import Models.Player;
import Models.Prosecutor;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProsecutorController extends Controller {
    private ProsecutorMapper mapper = new ProsecutorMapper();

    public ProsecutorController(){

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

        return mapper.VertexToEntity(prosecutor, player);
    }

    @RequestMapping(value = "/prosecutor/{id}/player/", method = RequestMethod.GET)
    public Player[] getAssistedPlayers(@PathVariable long id){
        PlayerController pc = new PlayerController();
        List<Object> list = this.g.V().has("prosecutor id", id).in("is assisted by").values("player id").toList();

        Player[] result = new Player[list.size()];
        for(int i = 0; i < list.size(); i++){
            result[i] = pc.getPlayerById(Long.parseLong(String.valueOf(list.get(i))),true);
        }

        return result;

    }

}
