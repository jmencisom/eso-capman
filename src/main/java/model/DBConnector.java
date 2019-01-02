package model;

import capman.Capman;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edisonarango
 */
public class DBConnector {
    
    private static final int DB_VERSION = 4;
    private static final DBConnector INSTANCE = new DBConnector();
    private static final String DBPath = Capman.DATA_DIRECTORY + File.separator + "capman.db";
    private Connection connection;

    private DBConnector() {
    }

    public static DBConnector getInstance() {
        return INSTANCE;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+DBPath);
            if (connection != null) {
                System.out.println("Connected");
                upgradeDB();
            }
        } catch (Exception ex) {
            System.err.println("Unable to connect to database\n" + ex.getMessage());
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    public void upgradeDB(){       
        int currentVersion;
        try {
            PreparedStatement pv;
            pv = connection.prepareStatement("select dataValue from capman where dataName = ?");
            pv.setString(1, "version");
            ResultSet result = pv.executeQuery();
            result.next();
            currentVersion = Integer.valueOf(result.getString("dataValue"));
            result.close();
            connection.close();
        } catch (SQLException ex) {
            currentVersion = 0;
        }
        
        try {
            for (int i = currentVersion; i < DB_VERSION; i++) {
                URL upgradeURL = DBConnector.class.getResource("/db/capman-" + i + ".sql");
                String upgradeSQL = Resources.toString(upgradeURL, Charsets.UTF_8);
                connection = DriverManager.getConnection("jdbc:sqlite:"+DBPath);
                Statement st = connection.createStatement();
                st.executeUpdate(upgradeSQL);
                connection.close();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:"+DBPath);
            if (currentVersion != DB_VERSION) {
                PreparedStatement pv = connection.prepareStatement("update capman set dataValue = ? where dataName = 'version'");
                pv.setString(1, DB_VERSION+"");
                pv.executeUpdate();
            }
            Statement st = connection.createStatement();
            st.executeUpdate("PRAGMA foreign_keys = ON");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
