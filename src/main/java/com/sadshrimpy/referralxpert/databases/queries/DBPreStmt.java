package com.sadshrimpy.referralxpert.databases.queries;

public class DBPreStmt {
    public String getCreate(int i) {
        switch (i) {
            case 0:
                return new StringBuilder(180)
                        .append("CREATE TABLE IF NOT EXISTS player (")
                        .append("IdPlaPk UNSIGNED INT(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                        .append("streak INT(5) NOT NULL,")
                        .append("online_time BIGINT(255) NOT NULL,")
                        .append("uuid VARCHAR(37) NOT NULL,")
                        .append("last_code VARCHAR(101) NOT NULL")
                        .append(");").toString();

            case 1:
                return new StringBuilder(160)
                        .append("CREATE TABLE IF NOT EXISTS period (")
                        .append("IdPerPk UNSIGNED INT(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                        .append("period INT(9) NOT NULL,")
                        .append("infinity ENUM(\"yes\",\"no\") NOT NULL")
                        .append(");").toString();

            case 2:
                return new StringBuilder(150)
                        .append("CREATE TABLE IF NOT EXISTS usages (")
                        .append("IdUsaPk UNSIGNED INT(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                        .append("usages INT(10) NOT NULL,")
                        .append("once ENUM(\"yes\",\"no\") NOT NULL")
                        .append(");").toString();

            case 3:
                return new StringBuilder(330)
                        .append("CREATE TABLE IF NOT EXISTS referral (")
                        .append("IdRefPk UNSIGNED INT(11) AUTO_INCREMENT PRIMARY KEY NOT NULL,")
                        .append("IdPerFk UNSIGNED INT(11) NOT NULL,")
                        .append("IdUsaFk UNSIGNED INT(11) NOT NULL,")
                        .append("code VARCHAR(101) NOT NULL,")
                        .append("owner_uuid VARCHAR(37) NOT NULL,")
                        .append("FOREIGN KEY(IdPerFk) REFERENCES period(IdPerPk),")
                        .append("FOREIGN KEY(IdUsaFk) REFERENCES usages(IdUsaPk)")
                        .append(");").toString();

            case 4:
                return new StringBuilder(190)
                        .append("CREATE TABLE IF NOT EXISTS claim (")
                        .append("IdPlaFk UNSIGNED INT(11) NOT NULL,")
                        .append("IdRefFk UNSIGNED INT(11) NOT NULL,")
                        .append("FOREIGN KEY(IdPlaFk) REFERENCES player(IdPlaPk),")
                        .append("FOREIGN KEY(IdRefFk) REFERENCES referral(IdRefPk)")
                        .append(");").toString();
        }
        return "";
    }

    public String getFindPlayer() {
        return new StringBuilder(100)
                .append("SELECT p.online_time ")
                .append("FROM player AS p ")
                .append("WHERE p.uuid = ?;").toString();
    }

    public String getFindCode() {
        return new StringBuilder(100)
                .append("SELECT r.code ")
                .append("FROM referral AS r ")
                .append("WHERE r.code = ?;").toString();
    }

    public String getUpdatePlayersTime() {
        return new StringBuilder(70)
                .append("UPDATE player ")
                .append("SET online_time = online_time + ? ")
                .append("WHERE uuid = ?;").toString();
    }

    public String getRegisterPlayers() {
        return new StringBuilder(80)
                .append("INSERT INTO player (streak, online_time, uuid, last_code) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?);").toString();
    }

    public String getRegisterReferral() {
        return new StringBuilder(80)
                .append("INSERT INTO referral (IdPerFk, IdUsaFk, code, owner_uuid) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?);").toString();
    }

    public String getRegisterUsage() {
        return new StringBuilder(50)
                .append("INSERT INTO usages (usages, once) ")
                .append("VALUES ")
                .append("(?, ?);").toString();
    }

    public String getRegisterPeriod() {
        return new StringBuilder(60)
                .append("INSERT INTO period (period, infinity) ")
                .append("VALUES ")
                .append("(?, ?);").toString();
    }

    public String getAllReferrals() {
        return new StringBuilder(40)
                .append("SELECT r.code FROM referral AS r;").toString();
    }
}
