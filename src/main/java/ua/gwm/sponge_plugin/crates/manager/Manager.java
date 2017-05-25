package ua.gwm.sponge_plugin.crates.manager;

import ua.gwm.sponge_plugin.crates.caze.Case;
import ua.gwm.sponge_plugin.crates.drop.Drop;
import ua.gwm.sponge_plugin.crates.key.Key;
import ua.gwm.sponge_plugin.crates.open_manager.OpenManager;

public class Manager {

    private Case caze;
    private Key key;
    private Drop drop;
    private OpenManager open_manager;

    public Manager(Case caze, Key key, Drop drop, OpenManager open_manager) {
        this.caze = caze;
        this.key = key;
        this.drop = drop;
        this.open_manager = open_manager;
    }

    public Case getCase() {
        return caze;
    }

    public void setCase(Case caze) {
        this.caze = caze;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Drop getDrop() {
        return drop;
    }

    public void setDrop(Drop drop) {
        this.drop = drop;
    }

    public OpenManager getOpenManager() {
        return open_manager;
    }

    public void setOpenManager(OpenManager open_manager) {
        this.open_manager = open_manager;
    }
}
