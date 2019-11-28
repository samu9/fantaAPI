package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class ProsecutorDAO extends BaseDAO {

    public Path getProsecutorPathById(long id){
        return this.g.V().has("prosecutor id", id).as("prosecutor").in("is assisted by").as("player").path().next();

    }

    public Vertex addProsecutor(long id, String name){
        return this.g.addV("prosecutor").property("name", name)
                .property("prosecutor id", id).next();
    }
}
