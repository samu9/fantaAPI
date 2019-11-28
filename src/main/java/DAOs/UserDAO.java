package DAOs;

import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.List;

public class UserDAO extends BaseDAO {

    public long getNewUserId(){ return super.getNewId("user"); }

    public List<Object> getIdList(){
        return getTraversalByProp("user id").order().by("username").values("user id").toList();
    }

    public Vertex addUser(String username, String email){
        long id = this.getNewUserId();
        return this.g.addV("user").property("username",username).property("user id", id).property("email",email).next();
    }
}
