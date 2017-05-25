package ua.gwm.sponge_plugin.crates.key;

import org.spongepowered.api.entity.living.player.Player;

public interface Key {

    void add(Player player, int amount);

    int get(Player player);
}
