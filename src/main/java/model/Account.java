/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Account {
    
    private int id;
    private String email;
    private ArrayList<Video> videos;
    
    public Account(int id, String email) {
        this.id = id;
        this.email = email;
        this.setVideos(Video.findByAccount(id));
    }

    public Account(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    private void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
    
    public void addVideo(Video newVideo){
        this.videos.add(newVideo);
    }
    
    public void removeVideo(Video video){
        this.videos.remove(video);
    }
    
    public void save() throws SQLException{
        Connection connection = DBConnector.getInstance().getConnection();
        if (find(this.getEmail()) != null){
            throw new SQLException("Account already saved");
        }
        PreparedStatement st = connection.prepareStatement("insert into accounts (id, email) values (?,?)");
        st.setInt(1, this.getId());
        st.setString(2, this.getEmail());
        st.execute();
    }
    
    public void delete() throws SQLException {
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement st = connection.prepareStatement("delete from accounts where id = ?");
        st.setInt(1, this.getId());
        st.execute();
    }
    
    public static Account find(int id) {
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv;
        try {
            pv = connection.prepareStatement("select * from accounts where id = ?");
            pv.setInt(1, id);
            ResultSet result = pv.executeQuery();
            if (result.next()){
                return new Account(result.getInt("id"), result.getString("email"));
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }
    
    public static Account find(String email) {
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv;
        try {
            pv = connection.prepareStatement("select * from accounts where email = ?");
            pv.setString(1, email);
            ResultSet result = pv.executeQuery();
            if (result.next()){
                return new Account(result.getInt("id"), result.getString("email"));
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }
    
    public static ArrayList<Account> findAll(){
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv; 
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            pv = connection.prepareStatement("select * from accounts");
            ResultSet result = pv.executeQuery();
            while (result.next()){
                accounts.add(new Account(result.getInt("id"), result.getString("email")));
            }
            return accounts;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public static int getNextID() throws SQLException{
        Connection connection = DBConnector.getInstance().getConnection();
        PreparedStatement pv;
        pv = connection.prepareStatement("SELECT seq FROM SQLITE_SEQUENCE WHERE name='accounts'");
        ResultSet result = pv.executeQuery();
        result.next();
        return result.getInt("seq") + 1;
    }
    
    @Override
    public String toString(){
        return this.getEmail();
    }
    
}
