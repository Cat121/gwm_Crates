package ua.gwm.sponge_plugin.crates.drop.drops;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import ua.gwm.sponge_plugin.crates.drop.Drop;

import java.util.Collection;
import java.util.HashSet;

public class CommandDrop implements Drop {

    private HashSet<Command> commands;

    public CommandDrop(Collection<Command> commands) {
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
