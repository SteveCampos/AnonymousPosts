package apps.stevecampos.fire.anonymouschat.main.ui.entity;

/**
 * Created by Steve on 30/11/2017.
 */

public class Comment {
    private String id;
    private User user;
    private String postId;
    private String commentText;
    private long favoriteCount;
    private long dislikeCount;

    public Comment() {
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setFavoriteCount(long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object obj) {
        boolean success = false;
        if (obj instanceof Comment) {
            Comment comment = (Comment) obj;
            if (comment.getId() != null && comment.getId().equals(id)) {
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
        return "Comment{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", postId='" + postId + '\'' +
                ", commentText='" + commentText + '\'' +
                ", favoriteCount=" + favoriteCount +
                ", dislikeCount=" + dislikeCount +
                '}';
    }
}
