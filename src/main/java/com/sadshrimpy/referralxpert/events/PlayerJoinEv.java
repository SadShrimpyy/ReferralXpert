package com.sadshrimpy.referralxpert.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.EventExecutor;

public class PlayerJoinEv implements Listener {

    @EventHandler
    public EventExecutor executor() {
        return (listener, event) -> {
            ((PlayerJoinEvent) event).getPlayer().sendMessage("TODO take time and hashmap to calc");
        };
    }

}
