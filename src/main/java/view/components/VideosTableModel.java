package view.components;

import capman.Capman;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.Video;
import view.MainFrame;

/**
 *
 * @author edisonarango
 */

class NamedURL {
    
    private URL url;
    private String name;
    
    public NamedURL(String name, String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
    
    public URI toURI() throws URISyntaxException{
        return url.toURI();
    }
    
    @Override
    public String toString(){
        return name;
    }
    
}

public class VideosTableModel extends AbstractTableModel implements TableReorderable {
    private static final String[] columnNames = {"Title", "ID", "Captions", "ID", "Captions", "Actions"};
    private ArrayList<Video> videos;
    private MainFrame mainframe;
        
    public VideosTableModel(MainFrame mainframe){
        this.mainframe = mainframe;
        videos = new ArrayList<>();
    }
    
    public void setVideos(ArrayList<Video> videos) throws MalformedURLException{
        this.videos = videos;
        this.fireTableDataChanged();
    }
    
    public void downloadVideoCaptions(int row){
        Video video = this.videos.get(row);
        mainframe.downloadVideoCaptions(video);
    }
    
    public void refreshVideo(int row){
        Video video = this.videos.get(row);
        mainframe.refreshVideo(video);
    }
    
    public void editVideo(int row){
        Video video = this.videos.get(row);
        mainframe.editVideoAction(video);
    }
    
    public void deleteVideo(int row){
        Video video = this.videos.get(row);
        mainframe.deleteVideo(video);
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
        
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return NamedURL.class;
            case 2:
                return String.class;
            case 3:
                return NamedURL.class;
            case 4:
                return String.class;
            default:
                return super.getColumnClass(column);
        }
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return columnIndex == 5;
    }

    @Override
    public int getRowCount() {
        return videos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Video video = videos.get(rowIndex);
        switch(columnIndex){
            case 0:
                return video.getTitle();
            case 1:
                String siteURL = video.getSite().getVideoBaseURL() + video.getIdVideoSite();
                return new NamedURL(video.getIdVideoSite(), siteURL);
            case 2:
                return video.getSiteCaptions();
            case 3:
                String youtubeURL = Capman.getYoutubeBaseURL() + video.getIdVideoYoutube();
                return new NamedURL(video.getIdVideoYoutube(), youtubeURL);
            case 4:
                return video.getYoutubeCaptions();
            default:
                return null;
        }
    }

    @Override
    public void reorder(int fromIndex, int toIndex) {
        Video toMove = videos.remove(fromIndex);
        if (fromIndex < toIndex) {
            toIndex--;
        }
        videos.add(toIndex, toMove);
        mainframe.updateVideosWeight(videos);
    }
}
