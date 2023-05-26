package com.sadshrimpy.referralxpert.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;

public class PlayerQuitEv implements Listener {

    @EventHandler
    public EventExecutor executor() {
        return (listener, event) -> {
            ((PlayerQuitEvent) event).getPlayer().sendMessage("TODO take time and hashmap to calc");
        };
    }

}
