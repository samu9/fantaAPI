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

    public boolean addStatsToPlayer(Vertex player, Vertex stats){
        this.g.V(player).as("a").V(stats).addE("stats").from("a").next();
        return true;
    }
}
