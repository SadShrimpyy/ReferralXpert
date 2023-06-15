package com.sadshrimpy.referralxpert.databases.sync;

import com.sadshrimpy.referralxpert.databases.procedures.DBQueries;
import com.sadshrimpy.referralxpert.referral.Referral;

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
        System.out.println("-=<: RUNNABLE START :>=-");
        DBQueries queries = new DBQueries(sadLibrary.database().getConnection());

        playersOperation(queries);
        referralsOperations(queries);

        sadLibrary.database().close();
    }

    private void referralsOperations(DBQueries queries) {
        LinkedList<Referral> referralsToRegister = new LinkedList<>();
        LinkedList<String> errorMessages = new LinkedList<>();

        cache.codeCreated().forEach((str, referral) -> {
            byte res = queries.findCode(str);
            if (res == -1)
                referralsToRegister.add(referral);
            else if (res == -2)
                errorMessages.add("-2 : Create New Code");
        });

        queries.registerReferrals(errorMessages, referralsToRegister);
        cache.clearReferralsCreatedCache();

        errorMessages.forEach(msg -> sendError("SQLException", msg));
    }

    /**
     * Register new players and update their online time
     * update the time of registered players
     * */
    private void playersOperation(DBQueries queries) {
        LinkedList<UUID> uuidsToRegister = new LinkedList<>();
        LinkedList<UUID> uuidsToUpdate = new LinkedList<>();
        LinkedList<String> errorMessages = new LinkedList<>();

        cache.playersTimes().forEach((uuid, value) -> {
            long res = queries.findPlayer(uuid);
            if (res == -1)
                uuidsToRegister.add(uuid);
            else if (res == -2)
                errorMessages.add("-2 : Find Player");
            else
                uuidsToUpdate.add(uuid);
        });

        queries.registerPlayers(errorMessages, uuidsToRegister, cache.playersTimes());
        queries.updatePlayersTime(errorMessages, uuidsToUpdate, cache.playersTimes());
        cache.clearPlayersTimesCache();

        errorMessages.forEach(msg -> sendError("SQLException", msg));
    }


    private void sendError(String e, String t) {
        sadLibrary.chat().viaConsole(true, sadLibrary.configurations().getMessages().getString("plugin-error.could-not-query-db")
                .replace("%db-exec%", e)
                .replace("%db-type%", t));
    }
}
