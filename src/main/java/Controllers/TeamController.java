package Controllers;

import DAOs.TeamDAO;
import Mappers.TeamMapper;
import Models.Team;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController extends Controller {
    TeamMapper mapper = new TeamMapper();
    TeamDAO dao = new TeamDAO();

    public TeamController(){
    }

    @RequestMapping(value = "/team/", method = RequestMethod.GET)
    public Team[] getAllTeams() {
        List<Object> list = dao.getIdList();

        Team[] allTeams = new Team[list.size()];
        for(int i = 0; i < list.size(); i++){
            allTeams[i] = getTeamById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return allTeams;
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public Team getTeamById(@PathVariable long id) throws java.lang.IllegalStateException {
        Path p = dao.getTeamPathById(id);

        Vertex team = p.get("team");
        Vertex coach = p.get("coach");
        Vertex president = p.get("president");
        Vertex stadium = p.get("stadium");
        List<Object> list = dao.getListInValues(team,"plays for", "birthdate");

        return mapper.VertexToTeam(team,coach,president,stadium,list);
    }

}
