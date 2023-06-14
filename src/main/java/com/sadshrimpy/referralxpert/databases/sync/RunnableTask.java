package com.sadshrimpy.referralxpert.databases.sync;

import java.sql.Connection;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class RunnableTask implements Runnable {

    @Override
    public void run() {
        sadLibrary.database().open();
        Connection connection = sadLibrary.database().getConnection();

        // TODO: 14/06/2023 Update DB
    }
}
