package Controllers;

import DAOs.UserDAO;
import Mappers.UserMapper;
import Models.User;
import Models.FantaTeam;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends Controller {

    private UserDAO dao = new UserDAO();
    private UserMapper mapper = new UserMapper();


    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public User[] getUsers(){
        List<Object> list = dao.getIdList();

        User[] result = new User[list.size()];
        for(int i = 0; i < list.size(); i++){
            result[i] = getUserById(Long.parseLong(String.valueOf(list.get(i))));
        }
        return result;
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public long createUser(@RequestParam(value="username") String username, @RequestParam(value="email", required = true) String email){
        Vertex user = dao.addUser(username, email);
        dao.commit();

        return mapper.mapUserId(user);
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable long id){
        Vertex user = dao.getVertexById("user id", id);
        List<Object> fantaTeams = dao.getListOutValues(user,"fanta owns", "fantateam id");

        return mapper.VertexToEntity(user, fantaTeams);
    }

}
