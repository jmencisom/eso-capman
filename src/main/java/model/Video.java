package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author edisonarango
 */
public class Video {
    
    private int ID;
    private String title;
    private Account account;
    private String idVideoSite;
    private Site site;
    private String siteCaptions;
    private String idVideoYoutube;
    private String youtubeCaptions;
    private int weight;

    public Video(int ID, String title, String idVideoSite, Site site, String siteCaptions, String idVideoYoutube, String youtubeCaptions) {
        this.ID = ID;
        this.title = title;
        this.idVideoSite = idVideoSite;
        this.site = site;
        this.siteCaptions = siteCaptions;
        this.idVideoYoutube = idVideoYoutube;
        this.youtubeCaptions = youtubeCaptions;
    }
    
    public Video(Account account, String title, String idVideoSite, Site site, String idVideoYoutube) {
        this.account = account;
        this.title = title;
        this.idVideoSite = idVideoSite;
        this.site = site;
        this.idVideoYoutube = idVideoYoutube;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSiteCaptions() {
        return siteCaptions;
    }

    public void setSiteCaptions(String siteCaptions) {
        this.siteCaptions = siteCaptions;
    }

    public String getYoutubeCaptions() {
        return youtubeCaptions;
    }

    public void setYoutubeCaptions(String youtubeCaptions) {
        this.youtubeCaptions = youtubeCaptions;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public Account getAccount(){
        return account;
    }

    public String getIdVideoSite() {
        return idVideoSite;
    }

    public void setIdVideoSite(String idVideoSite) {
        this.idVideoSite = idVideoSite;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getIdVideoYoutube() {
        return idVideoYoutube;
    }

    public void setIdVideoYoutube(String idVideoYoutube) {
        this.idVideoYoutube = idVideoYoutube;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public void save() throws SQLException{
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement st = connection.prepareStatement("insert into videos (idAccount, idVideoSite, idSite, idVideoYoutube, title) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, this.getAccount().getId());
        st.setString(2, this.getIdVideoSite());
        st.setInt(3, this.getSite().getID());
        st.setString(4, this.getIdVideoYoutube());
        st.setString(5, this.getTitle());
        st.execute();
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        this.setID(id);
        this.setSiteCaptions("--");
        this.setYoutubeCaptions("--");
    }
    
    public void update() throws SQLException{
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement st = connection.prepareStatement("update videos set idVideoSite = ?, idSite = ?, siteCaptions = ?, idVideoYoutube = ?, youtubeCaptions = ?, title = ? where id = ?");
        //Values to update
        st.setString(1, this.getIdVideoSite());
        st.setInt(2, this.getSite().getID());
        st.setString(3, this.getSiteCaptions());
        st.setString(4, this.getIdVideoYoutube());
        st.setString(5, this.getYoutubeCaptions());
        st.setString(6, this.getTitle());
        //Where clause
        st.setInt(7, this.getID());
        st.execute();
    }
    
    public void delete() throws SQLException {
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement st = connection.prepareStatement("delete from videos where id = ?");
        st.setInt(1, this.getID());
        st.execute();
    }
    
    public static Video find(int id) {
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv;
        try {
            pv = connection.prepareStatement("select * from videos where id = ?");
            pv.setInt(1, id);
            ResultSet result = pv.executeQuery();
            if (result.next()){
                return new Video(result.getInt("id"), result.getString("title"), result.getString("idVideoSite"), 
                        Site.find(result.getInt("idSite")), result.getString("siteCaptions"), 
                        result.getString("idVideoYoutube"), result.getString("youtubeCaptions"));
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }
    
    public static ArrayList<Video> findByAccount(int idAccount) {
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv; 
        ArrayList<Video> videos = new ArrayList<>();
        try {
            pv = connection.prepareStatement("select * from videos where idAccount = ? order by weight desc, id asc");
            pv.setInt(1, idAccount);
            ResultSet result = pv.executeQuery();
            while (result.next()){
                videos.add(new Video(result.getInt("id"), result.getString("title"), result.getString("idVideoSite"), 
                        Site.find(result.getInt("idSite")), result.getString("siteCaptions"), 
                        result.getString("idVideoYoutube"), result.getString("youtubeCaptions")));
            }
            return videos;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public void updateWeight() throws SQLException{
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement st = connection.prepareStatement("update videos set weight = ? where id = ?");
        //Values to update
        st.setInt(1, this.weight);
        //Where clause
        st.setInt(2, this.getID());
        st.execute();
    }
    
}
