package ua.gwm.sponge_plugin.crates.drop.drops;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import ua.gwm.sponge_plugin.crates.drop.Drop;
import ua.gwm.sponge_plugin.crates.util.GWMCratesUtils;

public class ItemDrop extends Drop {

    private ItemStack item;

    public ItemDrop(ConfigurationNode node) {
        ConfigurationNode item_node = node.getNode("ITEM");
        if (item_node.isVirtual()) {
            throw new RuntimeException("ITEM node does not exist!");
        }
        item = GWMCratesUtils.parseItem(item_node);
    }

    public ItemDrop(ItemStack item) {
        this.item = item;
    }

    @Override
    public void apply(Player player) {
        player.getInventory().offer(item);
    }
}
