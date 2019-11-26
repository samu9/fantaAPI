package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

public class TeamDAO extends BaseDAO{

    public Path getTeamPathById(long id){
        return this.g.V().has("team id", id).as("team").in("trains").as("coach")
                .out("trains").has("team id",id).in("owns").as("president")
                .out("owns").has("team id",id).out("plays in").as("stadium").path().next();
    }

    public List<Object> getIdList(){
        return super.getIdList("team id");
    }

    public Vertex addTeam(String name, String logo, long id){
        return this.g.addV("team").property("name", name)
                .property("logo", logo)
                .property("team id", id).next();
    }

    public boolean addPlayerToTeam(Vertex team, Vertex player){
        this.g.V(player).as("a").V(team).addE("plays for").from("a").next();
        return true;
    }
}
