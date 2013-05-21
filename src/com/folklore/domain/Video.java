package com.folklore.domain;
 
import java.io.Serializable;
 
/**
 * This is a representation of a users video off YouTube
 * @author paul.blundell
 */
public class Video implements Serializable {
    // The title of the video
    private String title;
    // A link to the video on youtube
    private String url;
    // A link to a still image of the youtube video
    private String thumbUrl;
    // Duration of the video
    private String duration;
    // User who uploaded the video
    private String user;
    // Description of the video
    private String description;
    // Rating of the video
    private int rating;
    
    public Video(String title, String url, String thumbUrl, String duration, String user,String description,int rating) {
        super();
        this.title = title;
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.duration = duration;
        this.user = user;
        this.description = description;
        this.rating = rating;
        
    }
 
    /**
     * @return the title of the video
     */
    public String getTitle(){
        return title;
    }
 
    /**
     * @return the url to this video on youtube
     */
    public String getUrl() {
        return url;
    }
 
    /**
     * @return the thumbUrl of a still image representation of this video
     */
    public String getThumbUrl() {
        return thumbUrl;
    }
    
    
    /**
     * @return the duration of a still image representation of this video
     */
    public String getDuration() {
        return duration;
    }
    
    /**
     * @return the user who uploaded this video
     */
    public String getUser() {
        return user;
    }
    
    /**
     * @return the description of this video
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @return the rating of this video
     */
    public int getRating() {
        return rating;
    }
}