package Controllers;

import DAOs.PlayerDAO;
import Mappers.PlayerMapper;
import Models.Player;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController extends Controller{

    PlayerMapper mapper = new PlayerMapper();
    PlayerDAO dao = new PlayerDAO();

    public Player[] getPlayersByIdList(List<Object> list){
        Player[] result = new Player[list.size()];
        for(int i = 0; i < list.size(); i++){
            result[i] = getPlayerById(Long.parseLong(String.valueOf(list.get(i))),false);
        }
        return result;
    }

    @RequestMapping(value = "/player/", method = RequestMethod.GET)
    public Player[] getAllPlayers(@RequestParam(required = false) boolean showStats){
        List<Object> list = dao.getIdList();
        return getPlayersByIdList(list);
    }


    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public Player getPlayerById(@PathVariable long id, @RequestParam(required = false) boolean showStats) throws IllegalStateException {
        Path p = dao.getPlayerPathById(id);

        Vertex player = p.get("player");
        Vertex team = p.get("team");
        Vertex prosecutor = p.get("prosecutor");
        List<Vertex> statList;

        if (!showStats) {
            statList = null;
        } else {
            statList = dao.getListOutVertex(player,"stats");
        }

        return mapper.VertexToPlayer(player, statList, team, prosecutor, showStats);
    }
}
