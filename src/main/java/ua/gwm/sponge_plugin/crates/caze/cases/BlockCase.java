package ua.gwm.sponge_plugin.crates.caze.cases;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import ua.gwm.sponge_plugin.crates.caze.Case;
import ua.gwm.sponge_plugin.crates.util.GWMCratesUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BlockCase extends Case {

    private Set<Location<World>> locations;

    public BlockCase(ConfigurationNode node) {
        ConfigurationNode locations_node = node.getNode("LOCATIONS");
        if (locations_node.isVirtual()) {
            throw new RuntimeException("LOCATIONS node does not exist!");
        }
        locations = new HashSet<Location<World>>();
        for (ConfigurationNode location_node : locations_node.getChildrenList()) {
            locations.add(GWMCratesUtils.parseLocation(location_node));
        }
    }

    public BlockCase(Collection<Location<World>> locations) {
        this.locations = new HashSet<Location<World>>(locations);
    }

    @Override
    public void add(Player player, int amount) {
    }

    @Override
    public int get(Player player) {
        return Integer.MAX_VALUE;
    }

    public Set<Location<World>> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location<World>> locations) {
        this.locations = locations;
    }
}
