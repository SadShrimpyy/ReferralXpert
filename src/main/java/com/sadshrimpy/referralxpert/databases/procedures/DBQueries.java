package com.sadshrimpy.referralxpert.databases.procedures;

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
                errorMessages.add("Update Player >> " + uuid);
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
                errorMessages.add("Register New Player >> " + uuid);
            }
        });
    }
}
