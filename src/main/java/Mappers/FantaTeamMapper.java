package Mappers;

import Models.FantaTeam;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

public class FantaTeamMapper extends TeamMapper {

    public long mapFantaTeamId(Vertex fantateam){ return this.mapLong(fantateam,"fantateam id"); }

    public FantaTeam VertexToFantaTeam(Vertex fantateam, Vertex user, List<Object> playersBirthdate){

        long id = this.mapFantaTeamId(fantateam);
        String name = this.mapName(fantateam);
        long players = playersBirthdate.size();
        double avgAge = getAvgAge(playersBirthdate);
        String logo = "";
        String username = this.mapString(user,"username");
        long ownerId = this.mapLong(user,"user id");

        return new FantaTeam(id, name, players, avgAge, logo, username, ownerId);
    }
}
