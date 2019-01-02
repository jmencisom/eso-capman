package controller;

import capman.Capman;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.CaptionListResponse;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author edisonarango
 */
public class Youtube {
    
    private static YouTube youtube;
    
    public static void loadYoutube(Credential credential){
        // This object is used to make YouTube Data API requests.
        youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
            .setApplicationName(Auth.APPLICATION_NAME).build();
    }
    
    public static Channel getChannel() throws IOException {
        ChannelListResponse channels = youtube.channels().list("snippet").setMine(true).execute();
        return channels.getItems().get(0);
    }
    
    public static String getYoutubeChannelName() throws IOException {
        return getChannel().getSnippet().getTitle();
    }
    
    public static boolean isMyVideo(String videoId) throws IOException, Exception {
        YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet").setId(videoId);
        VideoListResponse listResponse = listVideosRequest.execute();
        List<Video> videoList = listResponse.getItems();
        
        if (videoList.isEmpty()) {
            throw new Exception("Video id is not valid");
        }
        
        Video video = videoList.get(0);
        return video.getSnippet().getChannelId().equals(getChannel().getId());
    }
    
    public static String getVideoTitle(String videoId) throws Exception{
        YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet").setId(videoId);
        VideoListResponse listResponse = listVideosRequest.execute();
        List<Video> videoList = listResponse.getItems();
        
        if (videoList.isEmpty()) {
            throw new Exception("Video id is not valid");
        }
        
        Video video = videoList.get(0);
        return video.getSnippet().getTitle();
    }
    
    public static int getCaptionsCount(String videoId) throws IOException {
        CaptionListResponse captionListResponse = youtube.captions().
          list("snippet", videoId).execute();
        return captionListResponse.getItems().size();
    }
    
    public static List<Caption> getCaptions(String videoID) throws IOException {
        // Call the YouTube Data API's captions.list method to
        // retrieve video caption tracks.
        CaptionListResponse captionListResponse = youtube.captions().
            list("snippet", videoID).execute();

        List<Caption> captions = captionListResponse.getItems();
        return captions;
    }
    
    public static void downloadCaption(String captionId, String path) throws IOException {
      // Create an API request to the YouTube Data API's captions.download
      // method to download an existing caption track.
      YouTube.Captions.Download captionDownload = youtube.captions().download(captionId).setTfmt(Capman.CAPTION_FILE_FORMAT);

      // Set the download type and add an event listener.
      MediaHttpDownloader downloader = captionDownload.getMediaHttpDownloader();

      downloader.setDirectDownloadEnabled(false);

      OutputStream outputFile = new FileOutputStream(path);
      // Download the caption track.
      captionDownload.executeAndDownloadTo(outputFile);
    }

}
