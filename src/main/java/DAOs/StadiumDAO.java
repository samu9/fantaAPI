package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class StadiumDAO extends BaseDAO {

    public Path getStadiumPathById(long id){
        return this.g.V().has("stadium id", id).as("stadium").in("plays in").as("team").path().next();
    }

    public Vertex addStadium(long id, String name, String city, long capacity, String img){
        Vertex result = this.g.addV("stadium").property("name", name)
                .property("city", city).property("capacity", capacity)
                .property("img", img).property("stadium id", id).next();
        return result;
    }
}
