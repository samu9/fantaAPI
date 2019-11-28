package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

public class PlayerDAO extends BaseDAO {

    public Path getPlayerPathById(long id){
        return this.g.V().has("player id", id).as("player")
                .out("plays for").as("team")
                .in("plays for").has("player id", id).out("is assisted by").as("prosecutor")
                .path().next();
    }

    public List<Object> getIdList(){
        return super.getIdList("player id");
    }

    public Vertex addPlayer(String name, String birthdate, String birthplace, String nationality, String height, String role, String mainFoot, String img, long id, long quot){
        return this.g.addV("player")
                .property("name", name).property("birthdate", birthdate)
                .property("nationality", nationality).property("height", height)
                .property("role", role).property("mainFoot", mainFoot)
                .property("img", img).property("player id", id)
                .property("quot", quot).property("birthplace", birthplace).next();
    }

    public boolean addTeamToPlayer(Vertex player, Vertex team){
        return this.addEdge(player,team,"plays for");
    }
    public boolean addTeamToPlayer(long playerId, long teamId){
        return this.addEdge(playerId,"player id",teamId,"team id","plays for");
    }

    public boolean addStatsToPlayer(Vertex player, Vertex stats){
        return this.addEdge(player,stats,"stats");
    }

    public boolean addProsecutorToPlayer(Vertex player, Vertex prosecutor){
        return this.addEdge(player,prosecutor,"is assisted by");
    }
}
