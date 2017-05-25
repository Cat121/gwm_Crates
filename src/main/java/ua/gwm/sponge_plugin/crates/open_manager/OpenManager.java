package ua.gwm.sponge_plugin.crates.open_manager;

import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.manager.Manager;

public interface OpenManager {

    void open(Player player, Manager manager);
}
