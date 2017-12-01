package apps.steve.fire.randomchat.data.source.remote.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Steve on 30/11/2017.
 */
@IgnoreExtraProperties
public class Comment {
    private String id;
    private String postId;
    private String userId;
    private String commentText;
    private long favoriteCount;
    private long dislikeCount;

    public Comment() {
    }

    public Comment(String id, String postId, String userId, String commentText, long favoriteCount, long dislikeCount) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.commentText = commentText;
        this.favoriteCount = favoriteCount;
        this.dislikeCount = dislikeCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("commentText", commentText);
        result.put("favoriteCount", favoriteCount);
        result.put("dislikeCount", dislikeCount);
        result.put("userId", userId);
        result.put("postId", postId);
        return result;
    }
}
