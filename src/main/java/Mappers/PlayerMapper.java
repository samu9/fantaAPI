package Mappers;

import Controllers.PlayerStatsController;
import Models.Player;
import Models.PlayerStats;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.YEARS;

public class PlayerMapper extends Mapper {

    public long mapPlayerId(Vertex president){ return this.mapLong(president,"player id"); }

    public Player VertexToPlayer(Vertex player, List<Vertex> statList, Vertex team, Vertex prosecutor, boolean showStats){

        long id = this.mapPlayerId(player);
        String name = this.mapName(player);
        String birthplace = this.mapBirthplace(player);
        LocalDate birthdate = this.mapBirthdate(player);
        String nationality = this.mapNationality(player);
        long age = YEARS.between(birthdate, today);
        String height = this.mapString(player,"height");
        String position = this.mapString(player,"role");
        String mainfoot = this.mapString(player,"mainFoot");
        String img = this.mapImg(player);
        long quot = 0;
        try{
            quot = this.mapLong(player,"quot");
        }catch (Exception e){
            System.out.println("Error id: " + id);
        }
        String teamName = this.mapName(team);
        long teamId = this.mapLong(team,"team id");
        String prosecutorName = this.mapName(prosecutor);
        long prosecutorId = this.mapLong(prosecutor,"prosecutor id");

        PlayerStats[] stats;
        if(showStats){
            PlayerStatsController statsController = new PlayerStatsController();
            stats = statsController.getStatsFromPlayerVertex(player);
        } else {
            stats = null;
        }
        return new Player(id,name,birthplace,birthdate,nationality,age,height,position,mainfoot,stats, quot, teamName, teamId, prosecutorName,prosecutorId,img);
    }
}
