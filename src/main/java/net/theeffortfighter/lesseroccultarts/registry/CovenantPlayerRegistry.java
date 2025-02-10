package net.theeffortfighter.lesseroccultarts.registry;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CovenantPlayerRegistry {
    private static final CovenantPlayerRegistry INSTANCE = new CovenantPlayerRegistry();
    private final Set<UUID> covenantPlayers = new HashSet<>();

    private CovenantPlayerRegistry() {} // Private constructor to prevent instantiation

    public static CovenantPlayerRegistry getInstance() {
        return INSTANCE;
    }

    public void addPlayer(UUID playerUUID) {
        covenantPlayers.add(playerUUID);
    }

    public void removePlayer(UUID playerUUID) {
        covenantPlayers.remove(playerUUID);
    }

    public boolean isPlayerRegistered(UUID playerUUID) {
        return covenantPlayers.contains(playerUUID);
    }

    public Set<UUID> getCovenantPlayers() {
        return new HashSet<>(covenantPlayers); // Return a copy to prevent modification
    }
}
