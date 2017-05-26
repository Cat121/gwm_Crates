package ua.gwm.sponge_plugin.crates.key.keys;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import ua.gwm.sponge_plugin.crates.key.Key;
import ua.gwm.sponge_plugin.crates.util.GWMCratesUtils;

public class ItemKey extends Key {

    private ItemStack item;

    public ItemKey(ConfigurationNode node) {
        ConfigurationNode item_node = node.getNode("ITEM");
        if (item_node.isVirtual()) {
            throw new RuntimeException("ITEM node does not exist!");
        }
        item = GWMCratesUtils.parseItem(item_node);
    }

    public ItemKey(ItemStack item) {
        this.item = item;
    }

    @Override
    public void add(Player player, int amount) {
        if (amount > 0) {
            //TODO improve that algorithm.
            ItemStack copy = item.copy();
            copy.setQuantity(amount);
            player.getInventory().offer(copy);
        } else if (amount < 0) {
            Inventory inventory = player.getInventory();
            //TODO write algorithm that deletes %amount% of %item% from %inventory%.
        }
    }

    @Override
    public int get(Player player) {
        Inventory inventory = player.getInventory();
        //TODO write algorithm that calculates amount of %item% in %inventory%
        return 0;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
