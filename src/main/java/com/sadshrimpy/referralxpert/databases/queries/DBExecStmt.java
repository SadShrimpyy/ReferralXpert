package com.sadshrimpy.referralxpert.databases.queries;

import com.sadshrimpy.referralxpert.referral.Referral;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.sadshrimpy.referralxpert.ReferralXpert.sadLibrary;

public class DBExecStmt {

    private Connection connection;
    private DBPreStmt DBStmts;
    private PreparedStatement stmt;
    private ResultSet set;

    public DBExecStmt(Connection connection, DBPreStmt DBStmts) {
        this.connection = connection;
        this.DBStmts = DBStmts;
    }


    /**
     * @return LinkedList<String> with all the referrals registered in the database
     * */
    public LinkedList<String> getAllRegisteredReferrals() {
        LinkedList<String> referrals = new LinkedList<>();
        try {
            stmt = connection.prepareStatement(DBStmts.getAllReferrals());
            set = stmt.executeQuery();

            while (set.next())
                referrals.add(set.getString(1));

            sadLibrary.database().close();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return referrals;
    }

    public boolean updateRegisteredReferrals(List<String> errorMessages, Referral referral, Long mysql_periodId, Long mysql_usagesId) {
        try {
            stmt = connection.prepareStatement(DBStmts.getRegisterReferral());
            stmt.setString(1, String.valueOf(mysql_periodId));
            stmt.setString(2, String.valueOf(mysql_usagesId));
            stmt.setString(3, String.valueOf(referral.getCode()));
            stmt.setString(4, String.valueOf(referral.getOwner_uuid()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            errorMessages.add("RNR >> " + referral.getCode());
            return true;
        }
        return false;
    }
}
