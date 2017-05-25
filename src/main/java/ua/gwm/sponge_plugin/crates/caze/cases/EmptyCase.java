package ua.gwm.sponge_plugin.crates.caze.cases;

import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.caze.Case;

public class EmptyCase implements Case {

    @Override
    public void add(Player player, int amount) {
    }

    @Override
    public int get(Player player) {
        return Integer.MAX_VALUE;
    }
}
