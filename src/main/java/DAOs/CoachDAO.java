package DAOs;

import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class CoachDAO extends BaseDAO {

    public Path getCoachPathById(long id){
        return this.g.V().has("coach id", id).as("coach")
                .out("trains").as("team").path().next();
    }

    public Vertex addCoach(){
        return g.addV(Node.COACH).property(Property.NAME[0], coach_name)
                .property(Property.BIRTHDATE[0], coach_data.split(" ")[0])
                .property(Property.BIRTHPLACE[0], coach_place)
                .property(Property.NATIONALITY[0], coach_nat).property(Property.MODULE[0], coach_schema)
                .property(Property.COACH_ID[0], coach_counter).next();
    }

    public boolean addTeamToCoach(Vertex coach,Vertex team){
        this.addEdge(coach,team,"trains");
        return true;
    }

    public boolean addPresidentToCoach(Vertex coach,Vertex president){
        this.addEdge(coach,president,"is commissioned by");
        return true;
    }
}
