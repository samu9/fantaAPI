package Mappers;

import Models.President;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.time.LocalDate;

public class PresidentMapper extends Mapper {

    public long mapPresidentId(Vertex president){ return this.mapLong(president,"president id"); }

    public President VertexToEntity(Vertex president, Vertex team){
        String name = this.mapName(president);
        long id = this.mapPresidentId(president);
        LocalDate birthdate = this.mapBirthdate(president);
        String birthplace = this.mapBirthplace(president);
        String nationality = this.mapNationality(president);
        String ownedTeamName = this.mapName(team);
        long ownedTeamId = this.mapLong(team,"team id");

        return new President(id,name,birthdate,birthplace,nationality,ownedTeamName,ownedTeamId);
    }
}
