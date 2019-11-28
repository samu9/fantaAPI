package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class PresidentDAO extends BaseDAO {

    public Path getPresidentPathById(long id){
        return this.g.V().has("president id", id).as("president").out("owns").as("team").path().next();
    }

    public Vertex addPresident(long id, String name, String birthdate, String birthplace, String nationality){
        return this.g.addV("president").property("name", name)
                .property("birthdate", birthdate)
                .property("birthplace", birthplace)
                .property("nationality", nationality).property("president id", id).next();
    }

    public boolean addTeamToPresident(Vertex president, Vertex team){
        return this.addEdge(president,team,"owns");
    }
}
