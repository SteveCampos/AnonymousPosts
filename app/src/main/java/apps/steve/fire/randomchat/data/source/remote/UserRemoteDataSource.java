package apps.steve.fire.randomchat.data.source.remote;

import android.support.annotation.DrawableRes;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.data.source.remote.entity.User;
import apps.steve.fire.randomchat.data.source.remote.firebase.FirePostsCallback;
import apps.steve.fire.randomchat.data.source.remote.firebase.FireUser;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Post;

import static apps.steve.fire.randomchat.main.ui.entity.Avatar.*;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UserRemoteDataSource implements UserDataSource {

    private static final String TAG = UserRemoteDataSource.class.getSimpleName();
    private FireUser fireUser;
    private FirebaseUser firebaseUser;

    public UserRemoteDataSource(FireUser fireUser, FirebaseUser firebaseUser) {
        this.fireUser = fireUser;
        this.firebaseUser = firebaseUser;
    }

    @Override
    public void updateUser(FirebaseUser firebaseUser, AvatarUi avatar, String gender, final Callback<Boolean> callback) {

        User user = new User();
        user.setName(avatar.getName());
        user.setAvatar(avatar.getAvatarName());
        user.setCoins(0);
        user.setGender(gender);
        user.setDescription("");
        user.setId(firebaseUser.getUid());

        fireUser.updateUser(user, new apps.steve.fire.randomchat.data.source.remote.callback.Callback<User>() {
            @Override
            public void onSucess(User object) {
                boolean success = object != null;
                callback.onSucess(success);
            }
        });
    }

    @Override
    public void publishPost(final Post post, final Callback<Post> callback) {
        fireUser.publishPost(convertPost(post), new apps.steve.fire.randomchat.data.source.remote.callback.Callback<apps.steve.fire.randomchat.data.source.remote.entity.Post>() {
            @Override
            public void onSucess(apps.steve.fire.randomchat.data.source.remote.entity.Post remotePost) {
                if (remotePost != null) {
                    post.setId(remotePost.getId());
                    getUserByPost(remotePost, callback);
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    @Override
    public void getPopularPosts(final Callback<Post> callback) {
        Log.d(TAG, "getPopularPosts");
        fireUser.getPopularPost(new FirePostsCallback<apps.steve.fire.randomchat.data.source.remote.entity.Post>() {

            @Override
            public void onSuccess(apps.steve.fire.randomchat.data.source.remote.entity.Post post) {
                if (post != null) {
                    getUserByPost(post, callback);
                }
            }
        });
    }

    private void getUserByPost(final apps.steve.fire.randomchat.data.source.remote.entity.Post post, final Callback<Post> callback) {
        fireUser.getUser(post.getUserId(), new FirePostsCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    Post uiPost = convertPost(post);
                    uiPost.setUser(convertUser(user));
                    callback.onSucess(uiPost);
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    private apps.steve.fire.randomchat.main.ui.entity.User convertUser(User user) {
        apps.steve.fire.randomchat.main.ui.entity.User userUi = new apps.steve.fire.randomchat.main.ui.entity.User();
        userUi.setId(user.getId());
        userUi.setName(user.getName());

        String avatar = user.getAvatar();
        String gender = user.getGender();
        @DrawableRes int avatarDrawable = getAvatarDrawable(avatar);
        @DrawableRes int genderDrawable = getGenderDrawable(gender);

        userUi.setAvatarDrawable(avatarDrawable);
        userUi.setGenderDrawable(genderDrawable);
        userUi.setCoins(user.getCoins());
        userUi.setCommentCount(user.getCommentCount());
        userUi.setPostCount(user.getPostCount());
        return userUi;
    }

    private int getGenderDrawable(String gender) {
        @DrawableRes int genderDrawable = R.drawable.boy_casual;
        switch (gender) {
            case "WOMAN":
                genderDrawable = R.drawable.boy_casual;
                break;
        }
        return genderDrawable;
    }

    private @DrawableRes
    int getAvatarDrawable(String avatar) {
        @DrawableRes int avatarDrawable = R.drawable.boy_casual;
        switch (avatar) {
            case GIRL_CASUAL:
                avatarDrawable = R.drawable.girl_casual;
                break;
            case GIRL_CAT:
                avatarDrawable = R.drawable.girl_cat;
                break;
            case GIRL_CUTE:
                avatarDrawable = R.drawable.girl_cute;
                break;
            case GIRL_DARK:
                avatarDrawable = R.drawable.girl_dark;
                break;
            case BOY_KNIGHT:
                avatarDrawable = R.drawable.boy_knight;
                break;
            case BOY_JAPANESE:
                avatarDrawable = R.drawable.boy_japanese;
                break;
            case NB_CASUAL:
                avatarDrawable = R.drawable.nb_young;
                break;
            case NB_SAMURAI:
                avatarDrawable = R.drawable.nb_samurai;
                break;
        }
        return avatarDrawable;
    }

    private apps.steve.fire.randomchat.data.source.remote.entity.Post convertPost(Post post) {
        apps.steve.fire.randomchat.data.source.remote.entity.Post remotePost = new apps.steve.fire.randomchat.data.source.remote.entity.Post();
        remotePost.setId(post.getId());
        remotePost.setContentText(post.getContentText());
        remotePost.setHashtags(post.getHashtags());
        remotePost.setLocation(post.getLocation());
        remotePost.setPopular(post.isPopular());
        remotePost.setTimestamp(post.getTimestamp());
        remotePost.setUserId(firebaseUser.getUid());
        return remotePost;
    }

    private Post convertPost(apps.steve.fire.randomchat.data.source.remote.entity.Post remotePost) {
        Post post = new Post();
        post.setId(remotePost.getId());
        post.setContentText(remotePost.getContentText());
        post.setHashtags(remotePost.getHashtags());
        post.setLocation(remotePost.getLocation());
        post.setPopular(remotePost.isPopular());
        post.setTimestamp(remotePost.getTimestamp());
        return post;
    }
}
