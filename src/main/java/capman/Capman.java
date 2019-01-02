package capman;

import controller.CRUDController;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.DBConnector;
import view.MainFrame;

/**
 *
 * @author edisonarango
 */
public class Capman {
    
    public static final String DATA_DIRECTORY = System.getProperty("user.home") + File.separator + ".capman";
    public static final String CREDENTIALS_DIRECTORY = DATA_DIRECTORY + File.separator + "credentials";
    public static final String CAPTION_FILE_FORMAT = "srt";
    
    public static String getYoutubeBaseURL() {
        try {
            Connection connection = DBConnector.getInstance().getConnection();
            PreparedStatement pv;
            pv = connection.prepareStatement("select dataValue from capman where dataName = ?");
            pv.setString(1, "youtubeVideoBaseURL");
            ResultSet result = pv.executeQuery();
            result.next();
            return result.getString("dataValue");
        } catch (SQLException ex) {
            return "https://www.youtube.com/watch?v=";
        }
    }
    
    public static String getWorkingDirectory() {
        return System.getProperty("user.home") + File.separator + "Capman-Captions";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new File(DATA_DIRECTORY).mkdirs();
        DBConnector.getInstance().connect();
         /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (System.getProperty("os.name").contains("Mac")) {
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                }
                CRUDController crud = new CRUDController();
                MainFrame mainframe = new MainFrame(crud);
                mainframe.setVisible(true);
                mainframe.loadAccount();
            }
        });
    }
    
}
