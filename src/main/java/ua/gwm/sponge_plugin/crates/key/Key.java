package ua.gwm.sponge_plugin.crates.key;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;

public abstract class Key {

    protected Key() {
    }

    public Key(ConfigurationNode node) {
    }

    public abstract void add(Player player, int amount);

    public abstract int get(Player player);
}
