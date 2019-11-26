package Models;

public class Prosecutor {
    private final long id;
    private final String name;
    private final String playerName;
    private final long playerId;

    public Prosecutor(long id, String name,String playerName, long playerId){
        this.id = id;
        this.name = name;
        this.playerName = playerName;
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public long getPlayerId() {
        return playerId;
    }
}
