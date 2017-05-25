package ua.gwm.sponge_plugin.crates.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import ua.gwm.sponge_plugin.crates.GWMCrates;

//I am looking for better name of this class. Really, you can ever PR it :D
public class GWMCratesRegistrationEvent implements Event {

    @Override
    public Cause getCause() {
        return GWMCrates.getInstance().getDefaultCause();
    }
}
