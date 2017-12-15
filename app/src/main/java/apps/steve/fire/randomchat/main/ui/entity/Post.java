package apps.steve.fire.randomchat.main.ui.entity;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Steve on 26/11/2017.
 */
@Parcel
public class Post {
    String id;
    List<String> hashtags;
    String contentText;
    String location;
    User user;
    long timestamp;
    long favoriteCount;
    long dislikeCount;
    long commentCount;
    boolean popular;

    public Post() {
    }

    public Post(String id, List<String> hashtags, String content, String location, User user, long timestamp, long favoriteCount, long dislikeCount, long commentCount) {
        this.id = id;
        this.hashtags = hashtags;
        this.contentText = content;
        this.location = location;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object obj) {
        boolean success = false;
        if (obj instanceof Post) {
            Post post = (Post) obj;
            if (post.getId() != null && post.getId().equals(id)) {
                success = true;
            }
        }
        return success;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", hashtags=" + hashtags +
                ", contentText='" + contentText + '\'' +
                ", location='" + location + '\'' +
                ", user=" + user +
                ", timestamp=" + timestamp +
                ", favoriteCount=" + favoriteCount +
                ", dislikeCount=" + dislikeCount +
                ", commentCount=" + commentCount +
                ", popular=" + popular +
                '}';
    }
}
