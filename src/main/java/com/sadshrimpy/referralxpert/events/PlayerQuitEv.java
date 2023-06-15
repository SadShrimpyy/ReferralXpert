package com.sadshrimpy.referralxpert.events;

import com.sadshrimpy.referralxpert.databases.procedures.DBQuerys;
import com.sadshrimpy.referralxpert.databases.sync.RunnableTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.EventExecutor;

import java.util.UUID;

import static com.sadshrimpy.referralxpert.ReferralXpert.cache;
import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class PlayerQuitEv extends RunnableTask implements Listener {

    @EventHandler
    public EventExecutor executor() {
        return (listener, eventPassed) -> {
            PlayerQuitEvent event = (PlayerQuitEvent) eventPassed;
            if (cache.getOnlineMap().isEmpty()) return;
            UUID uuid = event.getPlayer().getUniqueId();

            long seconds = (sadLibrary.date().getDefaultTimeInMills() - cache.getOnlineMap().get(uuid).getTime()) / 1000;
            cache.playersTimes().put(uuid, seconds);
            cache.getOnlineMap().remove(uuid);

            // Test the DB
            super.TEST();
        };
    }

}
