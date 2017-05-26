package ua.gwm.sponge_plugin.crates;

import com.google.inject.Inject;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import ua.gwm.sponge_plugin.crates.caze.Case;
import ua.gwm.sponge_plugin.crates.caze.cases.*;
import ua.gwm.sponge_plugin.crates.drop.Drop;
import ua.gwm.sponge_plugin.crates.drop.drops.CommandDrop;
import ua.gwm.sponge_plugin.crates.drop.drops.ItemDrop;
import ua.gwm.sponge_plugin.crates.drop.drops.MultiDrop;
import ua.gwm.sponge_plugin.crates.event.GWMCratesRegistrationEvent;
import ua.gwm.sponge_plugin.crates.key.Key;
import ua.gwm.sponge_plugin.crates.key.keys.EmptyKey;
import ua.gwm.sponge_plugin.crates.key.keys.ItemKey;
import ua.gwm.sponge_plugin.crates.key.keys.TimedKey;
import ua.gwm.sponge_plugin.crates.key.keys.VirtualKey;
import ua.gwm.sponge_plugin.crates.manager.Manager;
import ua.gwm.sponge_plugin.crates.open_manager.OpenManager;
import ua.gwm.sponge_plugin.crates.open_manager.open_managers.FirstGuiOpenManager;
import ua.gwm.sponge_plugin.crates.open_manager.open_managers.NoGuiOpenManager;
import ua.gwm.sponge_plugin.crates.open_manager.open_managers.SecondGuiOpenManager;
import ua.gwm.sponge_plugin.crates.util.Config;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

    private HashMap<String, Class<? extends Case>> cases = new HashMap<String, Class<? extends Case>>();
    private HashMap<String, Class<? extends Key>> keys = new HashMap<String, Class<? extends Key>>();
    private HashMap<String, Class<? extends Drop>> drops = new HashMap<String, Class<? extends Drop>>();
    private HashMap<String, Class<? extends OpenManager>> open_managers = new HashMap<String, Class<? extends OpenManager>>();

    private HashSet<Manager> created_managers = new HashSet<Manager>();

    @Inject
    @ConfigDir(sharedRoot = false)
    private File config_directory;
    private File managers_directory;

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    private Config config;
    private Config language_config;
    private Config managers_config;
    private Config virtual_cases_config;
    private Config virtual_keys_config;
    private Config timed_cases_cooldowns_config;
    private Config timed_keys_cooldowns_config;

    @Listener
    public void onConstruct(GameConstructionEvent event) {
        instance = this;
    }

    @Listener
    public void onPreInitialization(GamePreInitializationEvent event) {
        if (!config_directory.exists()) {
            try {
                config_directory.createNewFile();
            } catch (Exception e) {
                logger.warn("Failed creating config directory!", e);
            }
            managers_directory = new File(config_directory, "managers");
            if (!managers_directory.exists()) {
                try {
                    managers_directory.createNewFile();
                } catch (Exception e) {
                    logger.warn("Failed creating managers config directory!", e);
                }
            }
        }
        logger.info("PreInitialization complete!");
    }

    @Listener
    public void onInitialize(GameInitializationEvent event) {
        register();
        createManagers();
        logger.info("Initialization complete!");
    }

    private void register() {
        GWMCratesRegistrationEvent registration_event = new GWMCratesRegistrationEvent();
        registration_event.getCases().put("ITEM", ItemCase.class);
        registration_event.getCases().put("BLOCK", BlockCase.class);
        registration_event.getCases().put("VIRTUAL", VirtualCase.class);
        registration_event.getCases().put("TIMED", TimedCase.class);
        registration_event.getCases().put("EMPTY", EmptyCase.class);
        registration_event.getKeys().put("ITEM", ItemKey.class);
        registration_event.getKeys().put("VIRTUAL", VirtualKey.class);
        registration_event.getKeys().put("TIMED", TimedKey.class);
        registration_event.getKeys().put("EMPTY", EmptyKey.class);
        registration_event.getDrops().put("ITEM", ItemDrop.class);
        registration_event.getDrops().put("COMMAND", CommandDrop.class);
        registration_event.getDrops().put("MULTI", MultiDrop.class);
        registration_event.getOpenManagers().put("NO_GUI", NoGuiOpenManager.class);
        registration_event.getOpenManagers().put("FIRST", FirstGuiOpenManager.class);
        registration_event.getOpenManagers().put("SECOND", SecondGuiOpenManager.class);
        Sponge.getEventManager().post(registration_event);
        for (Map.Entry<String, Class<? extends Case>> entry : registration_event.getCases().entrySet()) {
            String name = entry.getKey();
            Class<? extends Case> case_class = entry.getValue();
            String class_name = case_class.getSimpleName();
            if (cases.containsKey(name)) {
                logger.warn("Trying to add Case type " + name + " (" + class_name + ".class) which already exist!");
            } else {
                cases.put(name, case_class);
                logger.info("Successfully added Case type " + name + " (" + class_name + ".class)!");
            }
        }
        for (Map.Entry<String, Class<? extends Key>> entry : registration_event.getKeys().entrySet()) {
            String name = entry.getKey();
            Class<? extends Key> key_class = entry.getValue();
            String class_name = key_class.getSimpleName();
            if (keys.containsKey(name)) {
                logger.warn("Trying to add Key type " + name + " (" + class_name + ".class) which already exist!");
            } else {
                keys.put(name, key_class);
                logger.info("Successfully added Key type " + name + " (" + class_name + ".class)!");
            }
        }
        for (Map.Entry<String, Class<? extends Drop>> entry : registration_event.getDrops().entrySet()) {
            String name = entry.getKey();
            Class<? extends Drop> drop_class = entry.getValue();
            String class_name = drop_class.getSimpleName();
            if (drops.containsKey(name)) {
                logger.warn("Trying to add Drop type " + name + " (" + class_name + ".class) which already exist!");
            } else {
                drops.put(name, drop_class);
                logger.info("Successfully added Drop type " + name + " (" + class_name + ".class)!");
            }
        }
        for (Map.Entry<String, Class<? extends OpenManager>> entry : registration_event.getOpenManagers().entrySet()) {
            String name = entry.getKey();
            Class<? extends OpenManager> open_manager_class = entry.getValue();
            String class_name = open_manager_class.getSimpleName();
            if (open_managers.containsKey(name)) {
                logger.warn("Trying to add Open Manager type " + name + " (" + class_name + ".class) which already exist!");
            } else {
                open_managers.put(name, open_manager_class);
                logger.info("Successfully added Open Manager type " + name + " (" + class_name + ".class)!");
            }
        }
        logger.info("Registration complete!");
    }

    private void createManagers() {
        File[] managers_files = managers_directory.listFiles();
        if (managers_files.length == 0) {
            logger.warn("No one manager was found.");
        } else {
            for (File manager_file : managers_files) {
                try {
                    ConfigurationLoader<CommentedConfigurationNode> manager_configuration_loader =
                            HoconConfigurationLoader.builder().setFile(manager_file).build();
                    ConfigurationNode manager_node = manager_configuration_loader.load();
                } catch (Exception e) {
                    logger.info("Exception creating manager " + manager_file.getName() + "!", e);
                }
            }
            logger.info("All managers created!");
        }
    }

    public File getConfigDirectory() {
        return config_directory;
    }

    public Logger getLogger() {
        return logger;
    }

    public HashMap<String, Class<? extends Case>> getCases() {
        return cases;
    }

    public HashMap<String, Class<? extends Key>> getKeys() {
        return keys;
    }

    public HashMap<String, Class<? extends Drop>> getDrops() {
        return drops;
    }

    public HashMap<String, Class<? extends OpenManager>> getOpenManagers() {
        return open_managers;
    }

    public HashSet<Manager> getCreatedManagers() {
        return created_managers;
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

    public Config getTimedCasesCooldownsConfig() {
        return timed_cases_cooldowns_config;
    }

    public Config getTimedKeysCooldownsConfig() {
        return timed_keys_cooldowns_config;
    }
}
