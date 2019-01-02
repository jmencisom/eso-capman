package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edisonarango
 */
public class Site {
    
    private int ID;
    private String name;
    private String videoBaseURL;
    private String captionsBaseURL;

    public Site(String name, String videoBaseURL, String captionsBaseURL) {
        this.name = name;
        this.videoBaseURL = videoBaseURL;
        this.captionsBaseURL = captionsBaseURL;
    }

    public Site(int ID, String name, String videoBaseURL, String captionsBaseURL) {
        this.ID = ID;
        this.name = name;
        this.videoBaseURL = videoBaseURL;
        this.captionsBaseURL = captionsBaseURL;
    }
   
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoBaseURL() {
        return videoBaseURL;
    }

    public void setVideoBaseURL(String videoBaseURL) {
        this.videoBaseURL = videoBaseURL;
    }

    public String getCaptionsBaseURL() {
        return captionsBaseURL;
    }

    public void setCaptionsBaseURL(String captionsBaseURL) {
        this.captionsBaseURL = captionsBaseURL;
    }
    
    public static Site find(int id) {
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv;
        try {
            pv = connection.prepareStatement("select * from sites where id = ?");
            pv.setInt(1, id);
            ResultSet result = pv.executeQuery();
            if (result.next()){
                return new Site(result.getInt("id"), result.getString("name"), 
                        result.getString("videoBaseURL"), result.getString("captionsBaseURL"));
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }
    
    public static ArrayList<Site> findAll(){
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv; 
        ArrayList<Site> sites = new ArrayList<>();
        try {
            pv = connection.prepareStatement("select * from sites");
            ResultSet result = pv.executeQuery();
            while (result.next()){
                sites.add(new Site(result.getInt("id"), result.getString("name"), 
                        result.getString("videoBaseURL"), result.getString("captionsBaseURL")));
            }
            return sites;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    @Override
    public String toString(){
        return this.getName();
    }
    
}
