package com.sadshrimpy.referralxpert.databases.queries;

import com.sadshrimpy.referralxpert.referral.MultiansType;
import com.sadshrimpy.referralxpert.referral.Referral;
import com.sadshrimpy.referralxpert.referral.subt.Period;
import com.sadshrimpy.referralxpert.referral.subt.Usages;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.sadshrimpy.referralxpert.ReferralXpert.cache;

public class DBQueries {

    private DBPreStmt DBStmts;
    private DBExecStmt DBExecute;
    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet result;

    public DBQueries(Connection connection) {
        this.connection = connection;
        this.DBStmts = new DBPreStmt();
        this.DBExecute = new DBExecStmt(connection, DBStmts);
    }

    /** Querys to CHECK && RETURN */

    /**
     * @implNote Check if a player is registered in the database.
     * @return -1 if the player isn't found, -2 the exception, else > 0 (seconds of playtime) if the player is found
     * */
    public long findPlayer(UUID uuid) {
        try {
            stmt = connection.prepareStatement(DBStmts.getFindPlayer());
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
            stmt = connection.prepareStatement(DBStmts.getFindCode());
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
                stmt = connection.prepareStatement(DBStmts.getUpdatePlayersTime());
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
                stmt = connection.prepareStatement(DBStmts.getRegisterPlayers());
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
            Long mysql_periodId = getMysql_Id_T(errorMessages, referral, 1, "RNR ID (t: period)", "RNR (t: period)");
            if (mysql_periodId < 0) return;

            Long mysql_usagesId = getMysql_Id_T(errorMessages, referral, 2, "Register New Referral ID (t: usages) >> " + referral, "Register New Referral (t: usages) >> " + referral);
            if (mysql_usagesId < 0) return;

            if (DBExecute.updateRegisteredReferrals(errorMessages, referral, mysql_periodId, mysql_usagesId)) return;

            cache.DB_allReferrals().add(referral.getCode());
        });
    }

    /** Get the MySQL id (period & usages) */
    private long getMysql_Id_T(List<String> errorMessages, Referral referral, int type, String e1, String e2) {
        long readedId = -1;
        try {
            readedId = type == 2 ? registerUsage(referral.getUsages()) : registerPeriod(referral.getPeriod());
            if  (readedId < 0) {
                errorMessages.add(e1);
                return readedId;
            }
        } catch (SQLException e) {
            errorMessages.add(e2);
            return readedId;
        }
        return readedId;
    }

    /** Register into USAGES table */
    private long registerUsage(Usages usages) throws SQLException {
        stmt = connection.prepareStatement(DBStmts.getRegisterUsage(), Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, String.valueOf(usages.getOnce() == MultiansType.YES ? -1 : usages.getPeriod()));
        stmt.setString(2, String.valueOf(usages.getOnce()));
        int affRows = stmt.executeUpdate();
        return generatedKey(affRows);
    }

    /** Register into USAGES table */
    private long registerPeriod(Period period) throws SQLException {
        stmt = connection.prepareStatement(DBStmts.getRegisterPeriod(), Statement.RETURN_GENERATED_KEYS);
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
