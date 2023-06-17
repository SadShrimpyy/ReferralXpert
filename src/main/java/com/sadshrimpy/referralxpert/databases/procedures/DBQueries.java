package com.sadshrimpy.referralxpert.databases.procedures;

import com.sadshrimpy.referralxpert.referral.MultiansType;
import com.sadshrimpy.referralxpert.referral.Referral;
import com.sadshrimpy.referralxpert.referral.subt.Period;
import com.sadshrimpy.referralxpert.referral.subt.Usages;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DBQueries {

    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet result;

    public DBQueries(Connection connection) {
        this.connection = connection;
    }

    /** Querys to CHECK && RETURN */

    /**
     * @implNote Check if a player is registered in the database.
     * @return -1 if the player isn't found, -2 the exception, else > 0 (seconds of playtime) if the player is found
     * */
    public long findPlayer(UUID uuid) {
        try {
            stmt = connection.prepareStatement(new StringBuilder(100)
                    .append("SELECT p.online_time ")
                    .append("FROM player AS p ")
                    .append("WHERE p.uuid = ?;").toString());
            stmt.setString(1, String.valueOf(uuid));
            result = stmt.executeQuery();
            if (!result.next())
                return -1;
            else
                return result.getLong("online_time");
        } catch (SQLException e) {
            return -2;
        }
    }

    /**
     * @implNote Check if a code is registered in the database.
     * @return -1 if the code isn't found, -2 the exception, else 1 if the code is found
     * */
    public byte findCode(String str) {
        try {
            stmt = connection.prepareStatement(new StringBuilder(100)
                    .append("SELECT r.code ")
                    .append("FROM referral AS r ")
                    .append("WHERE r.code = ?;").toString());
            stmt.setString(1, str);
            result = stmt.executeQuery();
            if (!result.next())
                return -1;
            else
                return 1;
        } catch (SQLException e) {
            return -2;
        }
    }

    /** Querys to ALTER TABLE(S) */

    /**
     * Update the player's time with the given lists. In case of error, the list will be updated and printed later
     * */
    public void updatePlayersTime(List<String> errorMessages, List<UUID> uuidsToUpdate, HashMap<UUID, Long> timeMap) {
        uuidsToUpdate.forEach(uuid -> {
            try {
                stmt = connection.prepareStatement(new StringBuilder(70)
                        .append("UPDATE player ")
                        .append("SET online_time = online_time + ? ")
                        .append("WHERE uuid = ?;").toString());
                stmt.setString(1, String.valueOf(timeMap.get(uuid)));
                stmt.setString(2, String.valueOf(uuid));
                stmt.executeUpdate();
            } catch (SQLException e) {
                errorMessages.add("UP >> " + uuid);
            }
        });
    }

    /**
     * Register the players in the database. In case of error, the list will be updated and printed later
     * */
    public void registerPlayers(List<String> errorMessages, List<UUID> uuidsToRegister, HashMap<UUID, Long> timeMap) {
        uuidsToRegister.forEach(uuid -> {
            try {
                stmt = connection.prepareStatement(new StringBuilder(80)
                        .append("INSERT INTO player (streak, online_time, uuid, last_code) ")
                        .append("VALUES ")
                        .append("(?, ?, ?, ?);").toString());
                stmt.setString(1, String.valueOf(0));
                stmt.setString(2, String.valueOf(timeMap.get(uuid)));
                stmt.setString(3, String.valueOf(uuid));
                stmt.setString(4, "");
                stmt.executeUpdate();
            } catch (SQLException e) {
                errorMessages.add("RNP >> " + uuid);
            }
        });
    }

    /**
     * Register the players in the database. In case of error, the list will be updated and printed later
     * */
    public void registerReferrals(List<String> errorMessages, List<Referral> referralsToRegister) {
        referralsToRegister.forEach(referral -> {

            System.out.println("registerig : " + referral.getCode() + "  period: " + referral.getPeriod().getPeriod() + "  with infinity : " + referral.getPeriod().getInfinity());

            // TODO: 15/06/2023 insert into: period, usages and referral
            // Period table
            long mysql_r_periodId;
            try {
                mysql_r_periodId = registerPeriod(referral.getPeriod());
                if  (mysql_r_periodId < 0) {
                    errorMessages.add("RNR ID (t: period)");
                    return;
                }
            } catch (SQLException e) {
                errorMessages.add("RNR (t: period)");
                return;
            }

            // Usages table
            long mysql_r_usagesId;
            try {
                mysql_r_usagesId = registerUsage(referral.getUsages());
                if (mysql_r_usagesId < 0) {
                    errorMessages.add("Register New Referral ID (t: usages) >> " + referral);
                    return;
                }
            } catch (SQLException e) {
                errorMessages.add("Register New Referral (t: usages) >> " + referral);
                return;
            }

            System.out.println("registered ids: " + mysql_r_periodId + " - " + mysql_r_usagesId);

            try {
                stmt = connection.prepareStatement(new StringBuilder(80)
                        .append("INSERT INTO referral (IdPerFk, IdUsaFk, code, owner_uuid) ")
                        .append("VALUES ")
                        .append("(?, ?, ?, ?);").toString());
                stmt.setString(1, String.valueOf(mysql_r_periodId));
                stmt.setString(2, String.valueOf(mysql_r_usagesId));
                stmt.setString(3, String.valueOf(referral.getCode()));
                stmt.setString(4, String.valueOf(referral.getOwner_uuid()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                errorMessages.add("RNR >> " + referral.getCode());
            }
        });
    }

    /** Register into USAGES table */
    private long registerUsage(Usages usages) throws SQLException {
        stmt = connection.prepareStatement(new StringBuilder(50)
                .append("INSERT INTO usages (usages, once) ")
                .append("VALUES ")
                .append("(?, ?);").toString(), Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, String.valueOf(usages.getOnce() == MultiansType.YES ? -1 : usages.getPeriod()));
        stmt.setString(2, String.valueOf(usages.getOnce()));
        int affRows = stmt.executeUpdate();
        return generatedKey(affRows);
    }

    /** Register into USAGES table */
    private long registerPeriod(Period period) throws SQLException {
        stmt = connection.prepareStatement(new StringBuilder(60)
                .append("INSERT INTO period (period, infinity) ")
                .append("VALUES ")
                .append("(?, ?);").toString(), Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, String.valueOf(period.getInfinity() == MultiansType.YES ? -1 : period.getPeriod()));
        stmt.setString(2, String.valueOf(period.getInfinity()));
        int affRows = stmt.executeUpdate();
        return generatedKey(affRows);
    }

    /** Return the generated key */
    private long generatedKey(int affRows) throws SQLException {
        if (affRows < 0)
            return -1;
        else {
            result = stmt.getGeneratedKeys();
            return result.next() ? result.getLong(1) : -1;
        }
    }
}
