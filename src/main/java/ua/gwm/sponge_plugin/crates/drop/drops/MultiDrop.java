package ua.gwm.sponge_plugin.crates.drop.drops;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.GWMCrates;
import ua.gwm.sponge_plugin.crates.drop.Drop;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MultiDrop extends Drop {

    private int level;
    private Set<Drop> drops;

    public MultiDrop(ConfigurationNode node) {
        ConfigurationNode level_node = node.getNode("LEVEL");
        ConfigurationNode drops_node = node.getNode("DROPS");
        if (drops_node.isVirtual()) {
            throw new RuntimeException("DROPS node does not exist");
        }
        level = level_node.getInt(1);
        for (ConfigurationNode drop_node : drops_node.getChildrenList()) {
            ConfigurationNode drop_type_node = drop_node.getNode("TYPE");
            if (drop_type_node.isVirtual()) {
                throw new RuntimeException("TYPE node does not exist!");
            }
            String drop_type = drop_type_node.getString();
            if (!GWMCrates.getInstance().getDrops().containsKey(drop_type)) {
                throw new RuntimeException("Drop type \"" + drop_type + "\" not found!");
            }
            try {
                Class<? extends Drop> drop_class = GWMCrates.getInstance().getDrops().get(drop_type);
                Constructor<? extends Drop> drop_constructor = drop_class.getConstructor(ConfigurationNode.class);
                Drop drop = drop_constructor.newInstance(drop_node);
                drops.add(drop);
            } catch (Exception e) {
                throw new RuntimeException("Exception creating drop!", e);
            }
        }
    }

    public MultiDrop(int level, Collection<Drop> drops) {
        this.level = level;
        this.drops = new HashSet<Drop>(drops);
    }

    @Override
    public void apply(Player player) {
        drops.forEach(drop -> drop.apply(player));
    }

    @Override
    public int getLevel() {
        return level;
    }
}
