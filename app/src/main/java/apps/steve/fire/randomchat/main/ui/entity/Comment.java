package apps.steve.fire.randomchat.main.ui.entity;

/**
 * Created by Steve on 30/11/2017.
 */

public class Comment {
    private String id;
    private User user;
    private String commentText;
    private long favoriteCount;
    private long dislikeCount;

    public Comment(String id, User user, String commentText, long favoriteCount, long dislikeCount) {
        this.id = id;
        this.user = user;
        this.commentText = commentText;
        this.favoriteCount = favoriteCount;
        this.dislikeCount = dislikeCount;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCommentText() {
        return commentText;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }
}
