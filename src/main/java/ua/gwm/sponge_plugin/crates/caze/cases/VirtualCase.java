package ua.gwm.sponge_plugin.crates.caze.cases;

import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.GWMCrates;
import ua.gwm.sponge_plugin.crates.caze.Case;

public class VirtualCase implements Case {

    private String virtual_name;

    public VirtualCase(String virtual_name) {
        this.virtual_name = virtual_name;
    }

    @Override
    public void add(Player player, int amount) {
        GWMCrates.getInstance().getVirtualCasesConfig().
                getNode(player.getUniqueId().toString(), virtual_name).setValue(get(player) + amount);
    }

    @Override
    public int get(Player player) {
        return GWMCrates.getInstance().getVirtualCasesConfig().
                getNode(player.getUniqueId().toString(), virtual_name).getInt(0);
    }
}
