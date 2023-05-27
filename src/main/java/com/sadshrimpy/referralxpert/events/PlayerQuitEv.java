package com.sadshrimpy.referralxpert.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class PlayerQuitEv implements Listener {

    @EventHandler
    public EventExecutor executor() {
        return (listener, eventPassed) -> {
            PlayerQuitEvent event = (PlayerQuitEvent) eventPassed;
            if (sadLibrary.getOnlineMap().isEmpty()) return;

            final HashMap<UUID, Date> oMap = sadLibrary.getOnlineMap();
            long diff = (sadLibrary.date().getDefaultTimeInMills() - oMap.get(event.getPlayer().getUniqueId()).getTime()) / 1000;
        };
    }

}
