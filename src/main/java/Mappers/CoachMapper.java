package Mappers;

import Models.Coach;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.time.LocalDate;

public class CoachMapper extends Mapper {

    public long mapCoachId(Vertex coach){ return this.mapLong(coach,"coach id"); }

    public Coach VertexToEntity(Vertex coach, Vertex team){
        long id = this.mapCoachId(coach);
        String name = this.mapName(coach);

        // vanno corrette le date, ora sono timestap con l'ora
        String tmp = this.mapString(coach,"birthdate");
        LocalDate birthdate = LocalDate.parse(tmp.split(" ")[0]);
        String birthplace = this.mapBirthplace(coach);
        String nationality = this.mapNationality(coach);
        String module = this.mapString(coach,"module");
        String teamName = this.mapName(team);
        long teamId = this.mapLong(team,"team id");

        return new Coach(id,name,birthdate,birthplace,nationality,module,teamName, teamId);
    }
}
