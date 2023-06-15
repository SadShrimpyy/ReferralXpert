package com.sadshrimpy.referralxpert.databases.sync;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class DBSyncronization {

    private BukkitTask task;

    private final Runnable runnable;

    public DBSyncronization() {
        this.runnable = new RunnableTask();
        this.task = null;
    }

    public void registerTimer() {
        String SType = sadLibrary.configurations().getConfig().getString("database.sync.mode");
        if (DBSyncType.ASYNC.value().equals(SType))
            startSyncRunnable();
        else if (DBSyncType.SYNC.value().equals(SType))
            startASyncRunnable();
        else
            sadLibrary.chat().viaConsole(true, sadLibrary.configurations().getMessages().getString("plugin-error.sync-not-recognised"));
    }

    /**
     * Runnable SYNC and A-SYNC
     * */
    private void startASyncRunnable() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(
                sadLibrary.generics().getPlugin(),
                runnable,
                20L,
                sadLibrary.configurations().getConfig().getInt("database.sync.time") * 60L * 20L);
    }

    private void startSyncRunnable() {
        task = Bukkit.getScheduler().runTaskTimer(sadLibrary.generics().getPlugin(),
                runnable,
                20L,
                sadLibrary.configurations().getConfig().getInt("database.sync.time") * 60L * 20L);
    }

    /** Getters */
    public BukkitTask getTask() {
        return task;
    }
    public Runnable getRunnable() {
        return runnable;
    }

    /** Setters */
    public void setTask(BukkitTask task) {
        this.task = task;
    }

    /** Killers */
    /**
     * kill the runnable task. If is sync or async.
     * */
    public void killTask() {
        task.cancel();
    }
}
