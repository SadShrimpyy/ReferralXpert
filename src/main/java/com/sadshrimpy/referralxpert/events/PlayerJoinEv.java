package com.sadshrimpy.referralxpert.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.EventExecutor;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class PlayerJoinEv implements Listener {

    @EventHandler
    public EventExecutor executor() {
        return (listener, eventPassed) -> {
            PlayerJoinEvent event = (PlayerJoinEvent) eventPassed;
            sadLibrary.getOnlineMap().put(event.getPlayer().getUniqueId(), sadLibrary.date().getDefaultDate());
        };
    }

}
