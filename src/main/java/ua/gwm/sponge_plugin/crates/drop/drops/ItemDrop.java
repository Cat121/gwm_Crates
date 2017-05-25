package ua.gwm.sponge_plugin.crates.drop.drops;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import ua.gwm.sponge_plugin.crates.drop.Drop;

public class ItemDrop implements Drop {

    private ItemStack item;

    public ItemDrop(ItemStack item) {
        this.item = item;
    }

    @Override
    public void apply(Player player) {
        player.getInventory().offer(item);
    }
}
