package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

public class FantaTeamDAO extends BaseDAO {

    public Path getFantaTeamPathById(long id){
        return this.g.V().has("fantateam id", id).as("team").in("fanta owns").as("user")
                .path().next();
    }
    public List<Object> getIdList(){
        return super.getIdList("fantateam id");
    }

    public Vertex addFantaTeam(long userId, String name) throws Exception {
        Vertex user = this.getVertexById("user id", userId);

        boolean check = this.g.V(user).out("fanta owns").has("name", name).hasNext();

        if (!check) {
            long newId = this.getNewId("fantateam");
            Vertex team = this.g.addV("fantateam").property("fantateam id", newId).property("name", name).next();
            this.addEdge(user,team,"fanta owns");
            return team;
        } else throw new Exception();
    }

    public boolean addPlayerToFantaTeam(long teamId, long playerId){
        Vertex team = this.getVertexById("fantateam id", teamId);
        boolean check = this.g.V(team).in("fanta plays for").has("player id", playerId).hasNext();

        if(!check) {
            Vertex player = this.getVertexById("player id", playerId);
            this.g.V(player).as("a").V(team).addE("fanta plays for").from("a").next();
            return true;
        }
        else return false;
    }

    public boolean removeFantaTeam(long teamId){
        this.g.V().has("fantateam id", teamId).drop().iterate();
        return true;
    }

    public boolean removeAllFantaTeams(){
        this.g.V().has("fantateam id").drop().iterate();
        return true;
    }

    public boolean removePlayerFromFantaTeam(long teamId, long playerId){
        GraphTraversal<Vertex, Object> e = this.g.V().has("player id",playerId).outE().as("e").inV().has("fantateam id", teamId).select("e").drop().iterate();
        return true;
    }

    public List<Object> getPlayersIdList(long id){
        return this.g.V().has("fantateam id", id).in("fanta plays for").values("player id").toList();
    }
}
