package ua.gwm.sponge_plugin.crates.drop;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;

public abstract class Drop {

    protected Drop() {
    }

    public Drop(ConfigurationNode node) {
    }

    public abstract void apply(Player player);
}
