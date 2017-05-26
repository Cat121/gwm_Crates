package ua.gwm.sponge_plugin.crates.caze;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;

public abstract class Case {

    protected Case() {
    }

    public Case(ConfigurationNode node) {
    }

    public abstract void add(Player player, int amount);

    public abstract int get(Player player);

    public boolean canOpen(Player player) {
        return true;
    }
}
