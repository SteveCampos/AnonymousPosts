package apps.steve.fire.randomchat.data.source.remote.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Steve on 30/11/2017.
 */
@IgnoreExtraProperties
public class Post {
    private String id;
    private List<String> hashtags = new ArrayList<>();
    private String contentText;
    private String location;
    private String userId;
    private long timestamp;
    private long favoriteCount;
    private long dislikeCount;
    private long commentCount;
    private boolean popular;

    public Post() {
    }

    public Post(String id, List<String> hashtags, String contentText, String location, String userId, long timestamp, long favoriteCount, long dislikeCount, long commentCount) {
        this.id = id;
        this.hashtags = hashtags;
        this.contentText = contentText;
        this.location = location;
        this.userId = userId;
        this.timestamp = timestamp;
        this.favoriteCount = favoriteCount;
        this.dislikeCount = dislikeCount;
        this.commentCount = commentCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("contentText", contentText);
        result.put("location", location);
        result.put("userId", userId);
        result.put("timestamp", timestamp);
        result.put("favoriteCount", favoriteCount);
        result.put("dislikeCount", dislikeCount);
        result.put("commentCount", commentCount);
        result.put("popular", popular);
        if (!hashtags.isEmpty()) {
            for (String hashtag :
                    hashtags) {
                result.put("hashtag/" + hashtag, true);
            }
        }
        return result;
    }
}
