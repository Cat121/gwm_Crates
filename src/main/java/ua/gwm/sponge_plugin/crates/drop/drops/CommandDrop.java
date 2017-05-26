package ua.gwm.sponge_plugin.crates.drop.drops;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.drop.Drop;
import ua.gwm.sponge_plugin.crates.util.GWMCratesUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CommandDrop extends Drop {

    private int level;
    private Set<Command> commands;

    public CommandDrop(ConfigurationNode node) {
        ConfigurationNode level_node = node.getNode("LEVEL");
        ConfigurationNode commands_node = node.getNode("COMMANDS");
        if (!commands_node.isVirtual()) {
            throw new RuntimeException("COMMANDS node does not exist!");
        }
        level = level_node.getInt(1);
        commands = new HashSet<Command>();
        for (ConfigurationNode command_node : commands_node.getChildrenList()) {
            commands.add(GWMCratesUtils.parseCommand(command_node));
        }
    }

    public CommandDrop(int level, Collection<Command> commands) {
        this.level = level;
        this.commands = new HashSet<Command>(commands);
    }

    @Override
    public void apply(Player player) {
        ConsoleSource console_source = Sponge.getServer().getConsole();
        for (Command command : commands) {
            String cmd = command.getCmd().replace("%PLAYER%", player.getName());
            boolean console = command.isConsole();
            Sponge.getCommandManager().process(console ? console_source : player, cmd);
        }
    }

    @Override
    public int getLevel() {
        return level;
    }

    public static class Command {

        private String cmd;
        private boolean console;

        public Command(String cmd, boolean console) {
            this.cmd = cmd;
            this.console = console;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public boolean isConsole() {
            return console;
        }

        public void setConsole(boolean console) {
            this.console = console;
        }
    }
}
