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

        List<UUID> uuidsToRegister = new ArrayList<>();
        List<UUID> uuidsToUpdate = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        for (Map.Entry<UUID, Long> entry : cache.playersTimes().entrySet()) {
            UUID uuid = entry.getKey();
            long res = queries.findPlayer(uuid);
            if (res == -1)
                uuidsToRegister.add(uuid);
            else if (res == -2)
                errorMessages.add("-2 : Find Player");
            else
                uuidsToUpdate.add(uuid);
        }

        queries.registerPlayers(errorMessages, uuidsToRegister, cache.playersTimes());

        queries.updatePlayersTime(errorMessages, uuidsToUpdate, cache.playersTimes());

        for (String errorMessage : errorMessages) {
            sendError("SQLException", errorMessage);
        }

        sadLibrary.database().close();
    }


    private void sendError(String e, String t) {
        sadLibrary.chat().viaConsole(true, sadLibrary.configurations().getMessages().getString("plugin-error.could-not-query-db")
                .replace("%db-exec%", e)
                .replace("%db-type%", t));
    }
}
