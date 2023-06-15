package com.sadshrimpy.referralxpert.databases.procedures;

import java.sql.*;
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

    /** Querys to ALTER TABLE(S) */

    /**
     * @implNote Register a player in the database.
     * @return -2 the exception, 0 if the update didn't success, else 1 if the update success
     * */
    public byte registerNewPlayer(UUID uuid, long time) {
        try {
            stmt = connection.prepareStatement(new StringBuilder(80)
                    .append("INSERT INTO player (streak, online_time, uuid, last_code) ")
                    .append("VALUES ")
                    .append("(?, ?, ?, ?);").toString());
            stmt.setString(1, String.valueOf(0));
            stmt.setString(2, String.valueOf(time));
            stmt.setString(3, String.valueOf(uuid));
            stmt.setString(4, "");
            return (byte) stmt.executeUpdate();
        } catch (SQLException e) {
            return -2;
        }
    }

    /**
     * @implNote Register a player in the database.
     * @return -2 the exception, 0 if the update didn't success, else 1 if the update success
     * */
    public byte updateTime(UUID uuid, Long time) {
        try {
            stmt = connection.prepareStatement(new StringBuilder(80)
                    .append("UPDATE player ")
                    .append("SET online_time = online_time + ? ")
                    .append("WHERE uuid = ?;").toString());
            stmt.setString(1, String.valueOf(time));
            stmt.setString(2, String.valueOf(uuid));
            return (byte) stmt.executeUpdate();
        } catch (SQLException e) {
            return -2;
        }
    }
}
