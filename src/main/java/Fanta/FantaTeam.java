package Fanta;

public class FantaTeam extends Team{
    private final long ownerId;

    public FantaTeam(long id, String name, long players, double avgAge, String logo, String username, long ownerId){
        super(id,name,players,avgAge,logo,username);
        this.ownerId = ownerId;
    }
}
