package ua.gwm.sponge_plugin.crates.caze;

import org.spongepowered.api.entity.living.player.Player;

public interface Case {

    void add(Player player, int amount);

    int get(Player player);

    default boolean canOpen(Player player) {
        return true;
    }
}
