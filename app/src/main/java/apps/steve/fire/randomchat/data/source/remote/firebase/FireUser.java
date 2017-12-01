package apps.steve.fire.randomchat.data.source.remote.firebase;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apps.steve.fire.randomchat.data.source.remote.callback.Callback;
import apps.steve.fire.randomchat.data.source.remote.entity.Comment;
import apps.steve.fire.randomchat.data.source.remote.entity.Post;
import apps.steve.fire.randomchat.data.source.remote.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class FireUser extends Fire implements FireUserContract {

    public static final String PATH_USER = "/users/";
    public static final String PATH_POST = "/posts/";
    public static final String PATH_COMMENT = "/comments/";
    public static final String PATH_COMMENT_POST = "/comment-post/";
    public static final String PATH_COMMENT_LIKE = "/comment-like/";
    public static final String PATH_USER_LIKE = "/user-like/";
    public static final String PATH_USER_COMMENT = "/user-comment/";
    public static final String PATH_USER_POST = "/user-post/";
    public static final String PATH_LOCATION_POST = "/location-post/";
    public static final String PATH_POST_POPULAR = "/post-popular/";
    public static final String PATH_HASHTAG_POST = "/hashtag-post/";

    public FireUser() {
        super();
    }

    @Override
    public void updateUser(final User user, final Callback<User> callback) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = user.getId();
        Map<String, Object> userValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_USER + key, userValues);
        //childUpdates.put("/user-posts/" + userId + "/" + key, userValues);

        mDatabase
                .updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSucess(user);
                        } else {
                            callback.onSucess(null);
                        }
                    }
                });
    }

    @Override
    public void publishPost(final Post post, final Callback<Post> callback) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child(PATH_POST).push().getKey();
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_POST + key, postValues);
        childUpdates.put(PATH_USER_POST + key, postValues);
        if (post.isPopular()) {
            childUpdates.put(PATH_POST_POPULAR + key, postValues);
        }
        String location = post.getLocation();
        if (!TextUtils.isEmpty(location)) {
            childUpdates.put(PATH_LOCATION_POST + location + "/" + key, postValues);
        }
        List<String> hashtags = post.getHashtags();
        if (!hashtags.isEmpty()) {
            for (String hashtag :
                    hashtags) {
                childUpdates.put(PATH_HASHTAG_POST + hashtag + "/" + key, postValues);
            }
        }

        mDatabase
                .updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSucess(post);
                        } else {
                            callback.onSucess(null);
                        }
                    }
                });
    }

    @Override
    public void commentPost(final Comment comment, final Callback<Comment> callback) {
        // Create new comment by post
        String key = mDatabase.child(PATH_COMMENT).push().getKey();
        Map<String, Object> commentValues = comment.toMap();

        String userId = comment.getUserId();
        String postId = comment.getPostId();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_COMMENT + key, commentValues);
        childUpdates.put(PATH_COMMENT_POST + postId + "/" + key, commentValues);
        childUpdates.put(PATH_USER_COMMENT + userId + "/" + key, commentValues);


        mDatabase
                .updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSucess(comment);
                        } else {
                            callback.onSucess(null);
                        }
                    }
                });
    }

    @Override
    public void likeComment(User user, final Comment comment, final Callback<Comment> callback) {
        // Like an existent comment

        String commentId = comment.getId();
        String userId = user.getId();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_COMMENT_LIKE + commentId + "/" + userId, true);
        childUpdates.put(PATH_USER_LIKE + userId + "/" + commentId, comment.toMap());

        mDatabase
                .updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSucess(comment);
                        } else {
                            callback.onSucess(null);
                        }
                    }
                });
    }
}
