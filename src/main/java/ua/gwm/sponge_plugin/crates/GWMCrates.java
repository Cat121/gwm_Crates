package ua.gwm.sponge_plugin.crates;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import ua.gwm.sponge_plugin.crates.caze.Case;
import ua.gwm.sponge_plugin.crates.drop.Drop;
import ua.gwm.sponge_plugin.crates.key.Key;
import ua.gwm.sponge_plugin.crates.manager.Manager;
import ua.gwm.sponge_plugin.crates.open_manager.OpenManager;
import ua.gwm.sponge_plugin.crates.util.Config;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Plugin(
        id = "gwm_crates",
        name = "GWMCrates",
        version = "1.0beta",
        description = "Universal crates plugin for your server!",
        authors = {"GWM"/*My contacts: Skype(nk_gwm), Discord(Nazar 'GWM' Kalinovskiy#2192)*/})
public class GWMCrates {

    private static GWMCrates instance;

    public static GWMCrates getInstance() {
        if (instance == null) {
            throw new RuntimeException("GWMCrates not initialized!");
        }
        return instance;
    }

    private Cause default_cause = Cause.of(NamedCause.of("Plugin", this));

    private Set<Case> cases = new HashSet<Case>();
    private Set<Key> keys = new HashSet<Key>();
    private Set<Drop> drop = new HashSet<Drop>();
    private Set<OpenManager> open_managers = new HashSet<OpenManager>();
    private Set<Manager> created_managers = new HashSet<Manager>();

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path config_directory;

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    private Config config;
    private Config language_config;
    private Config managers_config;
    private Config virtual_cases_config;
    private Config virtual_keys_config;
    private Config timed_keys_cooldowns_config;

    @Listener
    public void onConstruct(GameConstructionEvent event) {
        instance = this;
    }

    public Path getConfigDirectory() {
        return config_directory;
    }

    public Logger getLogger() {
        return logger;
    }

    public Config getConfig() {
        return config;
    }

    public Config getLanguageConfig() {
        return language_config;
    }

    public Cause getDefaultCause() {
        return default_cause;
    }

    public Config getManagersConfig() {
        return managers_config;
    }

    public Config getVirtualCasesConfig() {
        return virtual_cases_config;
    }

    public Config getVirtualKeysConfig() {
        return virtual_keys_config;
    }

    public Config getTimeKeysCooldownsConfig() {
        return timed_keys_cooldowns_config;
    }
}
