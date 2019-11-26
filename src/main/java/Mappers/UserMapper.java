package Mappers;

import Controllers.FantaTeamController;
import Models.FantaTeam;
import Models.User;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

public class UserMapper extends Mapper {

    public long mapUserId(Vertex user){ return this.mapLong(user,"user id"); }

    public User VertexToEntity(Vertex user, List<Object> fantateamsIdList){

        String username = this.mapString(user, "username");
        long id = this.mapLong(user, "user id");
        String email = this.mapString(user,"email");

        FantaTeamController fantaTeamController = new FantaTeamController();
        FantaTeam[] teams = fantaTeamController.getFantaTeamsByIdList(fantateamsIdList);

        return new User(id,username,email, teams);
    }
}
