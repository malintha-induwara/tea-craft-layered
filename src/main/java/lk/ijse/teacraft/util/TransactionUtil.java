package lk.ijse.teacraft.util;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtil {
    private static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void autoCommitFalse() throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

}

