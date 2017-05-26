package ua.gwm.sponge_plugin.crates.key.keys;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.GWMCrates;
import ua.gwm.sponge_plugin.crates.key.Key;

public class VirtualKey extends Key {

    private String virtual_name;

    public VirtualKey(ConfigurationNode node) {
        ConfigurationNode virtual_name_node = node.getNode("VIRTUAL_NAME");
        if (virtual_name_node.isVirtual()) {
            throw new RuntimeException("VIRTUAL_NAME node does not exist!");
        }
        virtual_name = virtual_name_node.getString();
    }

    public VirtualKey(String virtual_name) {
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
