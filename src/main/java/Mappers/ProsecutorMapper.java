package Mappers;

import Models.Prosecutor;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class ProsecutorMapper extends Mapper {

    public long mapProsecutorId(Vertex prosecutor){ return this.mapLong(prosecutor,"prosecutor id"); }

    public Prosecutor VertexToEntity(Vertex prosecutor, Vertex player){
        String name = this.mapName(prosecutor);
        long id = this.mapProsecutorId(prosecutor);
        String playerName = this.mapName(player);
        long playerId = this.mapLong(player, "player id");

        return new Prosecutor(id,name,playerName,playerId);
    }
}
