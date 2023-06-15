package com.sadshrimpy.referralxpert.databases.sync;

import com.sadshrimpy.referralxpert.databases.procedures.DBQueries;

import java.util.*;

import static com.sadshrimpy.referralxpert.ReferralXpert.cache;
import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class RunnableTask implements Runnable {

    public void TEST() {
        this.run();
    }

    @Override
    public void run() {
        sadLibrary.database().open();
        DBQueries queries = new DBQueries(sadLibrary.database().getConnection());
        Iterator<Map.Entry<UUID, Long>> iterator = cache.playersTimes().entrySet().iterator();
        long res;
        while (iterator.hasNext()) {
            Map.Entry<UUID, Long> entry = iterator.next();
            UUID uuid = entry.getKey();
            Long time = entry.getValue();
            res = queries.findPlayer(uuid);
            if (res == -1)
                registerPlayer(queries, iterator, uuid, time);
            else if (res == -2)
                sendError("SQLException", "-2 : Find Player");
            else {
                res = queries.updateTime(uuid, time);
                if (res == -2)
                    sendError("SQLException", "-2 : Update Player Time");
                else if (res == 1)
                    iterator.remove();
            }
        }
    }

    /**
     * @implNote Register the player into the database, with all their data
     * */
    private void registerPlayer(DBQueries querys, Iterator<Map.Entry<UUID, Long>> iterator, UUID uuid, Long time) {
        long resTime = querys.registerNewPlayer(uuid, time);
        if (resTime == -2)
            sendError("SQLException", "-2 : Register New Player >> " + uuid);
        else if (resTime == 0)
            sendError("Update Database", "Register New Player >> " + uuid);
        else
            iterator.remove();
        }


    private void sendError(String e, String t) {
        sadLibrary.chat().viaConsole(true, sadLibrary.configurations().getMessages().getString("plugin-error.could-not-query-db")
                .replace("%db-exec%", e)
                .replace("%db-type%", t));
    }
}
