package controller;

import capman.Capman;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import model.Site;
import model.Video;

/**
 *
 * @author edisonarango
 */
public class SiteController {
    
    public static boolean siteVideoExist(Site site, String videoId) throws MalformedURLException, IOException {
        String url = site.getVideoBaseURL() + videoId;
        return isUrlUp(url);
    }
    
    public static int getCaptionsCount(Video video, String[] languages) throws IOException{
        int count = 0;
        for (String language : languages) {
            String url = video.getSite().getCaptionsBaseURL() + video.getIdVideoSite() 
                    + language + "." + Capman.CAPTION_FILE_FORMAT;           
            if(isUrlUp(url)){
                count++;
            }
        }
        return count;
    }
    
    public static boolean captionExist(Video video, String language) throws IOException {
        String url = video.getSite().getCaptionsBaseURL() + video.getIdVideoSite() 
                    + language + "." + Capman.CAPTION_FILE_FORMAT;
        return isUrlUp(url);
    }
    
    public static boolean isUrlUp(String urlString) throws MalformedURLException, IOException{
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.connect();
        return 200 == connection.getResponseCode();
    }
    
}
