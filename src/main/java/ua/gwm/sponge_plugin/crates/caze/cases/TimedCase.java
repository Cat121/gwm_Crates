package ua.gwm.sponge_plugin.crates.caze.cases;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.GWMCrates;
import ua.gwm.sponge_plugin.crates.caze.Case;

public class TimedCase extends Case {

    private String virtual_name;
    private int cooldown;

    public TimedCase(ConfigurationNode node) {
        ConfigurationNode virtual_name_node = node.getNode("VIRTUAL_NAME");
        ConfigurationNode cooldown_node = node.getNode("COOLDOWN");
        if (virtual_name_node.isVirtual()) {
            throw new RuntimeException("VIRTUAL_NAME node does not exist!");
        }
        if (cooldown_node.isVirtual()) {
            throw new RuntimeException("COOLDOWN node does not exist!");
        }
        virtual_name = virtual_name_node.getString();
        cooldown = cooldown_node.getInt();
    }

    public TimedCase(String virtual_name, int cooldown) {
        this.virtual_name = virtual_name;
        this.cooldown = cooldown;
    }

    @Override
    public void add(Player player, int amount) {
        ConfigurationNode cooldown_node = GWMCrates.getInstance().getTimedKeysCooldownsConfig().
                getNode(player.getUniqueId().toString(), virtual_name);
        if (amount > 0) {
            cooldown_node.setValue(null);
        } else if (amount < 0) {
            cooldown_node.setValue(System.currentTimeMillis() + cooldown);
        }
    }

    @Override
    public int get(Player player) {
        ConfigurationNode cooldown_node = GWMCrates.getInstance().getTimedKeysCooldownsConfig().
                getNode(player.getUniqueId().toString(), virtual_name);
        if (cooldown_node.isVirtual()) {
            return 0;
        }
        int cooldown = cooldown_node.getInt();
        if (System.currentTimeMillis() >= cooldown) {
            cooldown_node.setValue(null);
            return Integer.MAX_VALUE;
        } else {
            return 0;
        }
    }
}
