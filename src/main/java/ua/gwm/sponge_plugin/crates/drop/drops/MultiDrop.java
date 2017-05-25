package ua.gwm.sponge_plugin.crates.drop.drops;

import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.drop.Drop;

import java.util.Collection;
import java.util.HashSet;

public class MultiDrop implements Drop {

    private HashSet<Drop> drops = new HashSet<Drop>();

    public MultiDrop(Collection<Drop> drops) {
        this.drops = new HashSet<Drop>(drops);
    }

    @Override
    public void apply(Player player) {
        drops.forEach(drop -> drop.apply(player));
    }
}
