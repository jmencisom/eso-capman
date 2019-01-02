package controller;

import capman.Capman;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.model.Caption;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Site;
import model.Video;
import view.components.ProgressBarTask;

/**
 *
 * @author edisonarango
 */
public class CRUDController {
    
    public Account createAccount() throws Exception {
        int id = Account.getNextID();
        try {
            Credential credential = Auth.authorize("" + id);
            loadServices(credential);
            String email = Auth.getOauthEmail();
            Account newAccount = new Account(id, email);
            newAccount.save();
            return newAccount;
        } catch (SQLException ex) {
            Auth.removeCredentialFile(""+id);
            throw new SQLException(ex.getMessage());
        }
    }
    
    public ArrayList<Account> getAllAccounts(){
        return Account.findAll();
    }
    
    public void loadAccount(Account currentAccount) throws Exception {
        Credential credential = Auth.authorize(""+currentAccount.getId());
        loadServices(credential);
    }
    
    public void loadServices(Credential credential) {
        Auth.loadOauth(credential);
        Youtube.loadYoutube(credential);
    }

    public void deleteAccount(Account accountToRemove) throws SQLException {
        accountToRemove.delete();
        Auth.removeCredentialFile(""+accountToRemove.getId());
    }
    
    public ArrayList<Site> getAllSites(){
        return Site.findAll();
    }
    
    public Video createVideo(Account account, String title, String idVideoSite, Site site, String idVideoYoutube) throws SQLException{
        Video newVideo = new Video(account, title, idVideoSite, site, idVideoYoutube);
        newVideo.save();
        account.addVideo(newVideo);
        return newVideo;
    }
    
    public void updateVideo(Video video, String title, String idVideoSite, Site site, String idVideoYoutube) throws SQLException {
        video.setIdVideoSite(idVideoSite);
        video.setSite(site);
        video.setIdVideoYoutube(idVideoYoutube);
        video.setTitle(title);
        video.update();
    }
    
    public void updateVideoCaptions(Video video) throws IOException, SQLException {
        video.setYoutubeCaptions(""+Youtube.getCaptionsCount(video.getIdVideoYoutube()));
        
        List<Caption> captions = Youtube.getCaptions(video.getIdVideoYoutube());
        String[] languages = new String[captions.size()];
        for (int i = 0; i < languages.length; i++) {
            languages[i] = captions.get(i).getSnippet().getLanguage();
        }
        
        video.setSiteCaptions(""+SiteController.getCaptionsCount(video, languages));
        video.update();
    }
    
    public void deleteVideo(Video videoToDelete, Account account) throws SQLException {
        videoToDelete.delete();
        account.removeVideo(videoToDelete);
    }

    public void downloadVideoCaptions(Video video, Account currentAccount, ProgressBarTask progressBarTask) throws Exception {
        List<Caption> captions = Youtube.getCaptions(video.getIdVideoYoutube());
        int amount = captions.size();
        if(!video.getSiteCaptions().equals("--") && !video.getSiteCaptions().equals("--")){
            amount = Integer.valueOf(video.getYoutubeCaptions()) - Integer.valueOf(video.getSiteCaptions());
        }
        int downloaded = 0;
        progressBarTask.updateProgress(5);
        for (Caption caption : captions) {
            if(!SiteController.captionExist(video, caption.getSnippet().getLanguage())){
                String directory = Capman.getWorkingDirectory() + File.separator 
                        + currentAccount.getEmail() + File.separator + video.getIdVideoSite();
                new File(directory).mkdirs();
                String path = directory + File.separator + video.getIdVideoSite() 
                        + caption.getSnippet().getLanguage() + "." + Capman.CAPTION_FILE_FORMAT;
                Youtube.downloadCaption(caption.getId(), path);
                downloaded++;
                progressBarTask.updateProgress(downloaded * 100 / amount);
            }
        }
    }

    public void updateVideosWeight(ArrayList<Video> videos) throws SQLException {
        int size = videos.size();
        for (int i = 0; i < size; i++) {
            Video get = videos.get(i);  
            get.setWeight(size - i);
            get.updateWeight();
        }
    }
}
