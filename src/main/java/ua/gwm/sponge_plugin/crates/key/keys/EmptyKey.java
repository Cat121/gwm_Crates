package ua.gwm.sponge_plugin.crates.key.keys;

import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.key.Key;

public class EmptyKey implements Key {

    @Override
    public void add(Player player, int amount) {
    }

    @Override
    public int get(Player player) {
        return Integer.MAX_VALUE;
    }
}
