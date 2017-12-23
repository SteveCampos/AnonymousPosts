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
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Message;
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
    public void publishPost(Post post, final Callback<Post> callback) {
        fireUser.publishPost(convertPost(post), new apps.steve.fire.randomchat.data.source.remote.callback.Callback<apps.steve.fire.randomchat.data.source.remote.entity.Post>() {
            @Override
            public void onSucess(apps.steve.fire.randomchat.data.source.remote.entity.Post remotePost) {
                if (remotePost != null) {
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

    @Override
    public void getRecentPosts(final Callback<Post> callback) {
        Log.d(TAG, "getRecentPosts");
        fireUser.getRecentPosts(new FirePostsCallback<apps.steve.fire.randomchat.data.source.remote.entity.Post>() {

            @Override
            public void onSuccess(apps.steve.fire.randomchat.data.source.remote.entity.Post post) {
                if (post != null) {
                    getUserByPost(post, callback);
                }
            }
        });
    }

    @Override
    public void getPostWithTag(String tag, final Callback<Post> callback) {
        Log.d(TAG, "getPostWithTag");
        fireUser.getPostWithTag(tag, new FirePostsCallback<apps.steve.fire.randomchat.data.source.remote.entity.Post>() {
            @Override
            public void onSuccess(apps.steve.fire.randomchat.data.source.remote.entity.Post post) {
                if (post != null) {
                    getUserByPost(post, callback);
                }
            }
        });
    }

    @Override
    public void publishComment(Comment comment, final Callback<Comment> callback) {
        fireUser.commentPost(convertComment(comment), new apps.steve.fire.randomchat.data.source.remote.callback.Callback<apps.steve.fire.randomchat.data.source.remote.entity.Comment>() {
            @Override
            public void onSucess(apps.steve.fire.randomchat.data.source.remote.entity.Comment remoteComment) {
                if (remoteComment != null) {
                    getUserByComment(remoteComment, callback);
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    @Override
    public void getPostComments(Post post, final Callback<Comment> callback) {
        fireUser.getPostComments(post.getId(), new FirePostsCallback<apps.steve.fire.randomchat.data.source.remote.entity.Comment>() {
            @Override
            public void onSuccess(apps.steve.fire.randomchat.data.source.remote.entity.Comment remoteComment) {
                if (remoteComment != null) {
                    getUserByComment(remoteComment, callback);
                }
            }
        });
    }

    @Override
    public void removePostCommentsListener(Post post) {
        fireUser.removePostCommentsListener(post.getId());
    }

    @Override
    public void listenUserInboxState(apps.steve.fire.randomchat.main.ui.entity.User user, final Callback<Boolean> callback) {
        Log.d(TAG, "listenUserInboxState");
        fireUser.listenUserInboxState(convertUser(user), new apps.steve.fire.randomchat.data.source.remote.callback.Callback<Boolean>() {
            @Override
            public void onSucess(Boolean state) {
                callback.onSucess(state);
            }
        });
    }

    @Override
    public void removeUserInboxStateListener(apps.steve.fire.randomchat.main.ui.entity.User user) {
        Log.d(TAG, "removeUserInboxStateListener");
        fireUser.removetUserInboxStateListener(convertUser(user));
    }

    @Override
    public void getUser(String id, final Callback<apps.steve.fire.randomchat.main.ui.entity.User> callback) {
        fireUser.getUser(id, new FirePostsCallback<User>() {
            @Override
            public void onSuccess(User remoteUser) {
                if (remoteUser != null) {
                    callback.onSucess(convertUser(remoteUser));
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    @Override
    public void updateUser(apps.steve.fire.randomchat.main.ui.entity.User user, final Callback<apps.steve.fire.randomchat.main.ui.entity.User> callback) {
        fireUser.updateUser(convertUser(user), new apps.steve.fire.randomchat.data.source.remote.callback.Callback<User>() {
            @Override
            public void onSucess(User remoteUser) {
                if (remoteUser != null) {
                    getUser(remoteUser.getId(), callback);
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    @Override
    public void updateUserCoins(apps.steve.fire.randomchat.main.ui.entity.User user, long coins, final Callback<apps.steve.fire.randomchat.main.ui.entity.User> callback) {
        Log.d(TAG, "updateUserCoins");
        fireUser.updateUserCoins(convertUser(user), coins, new apps.steve.fire.randomchat.data.source.remote.callback.Callback<User>() {
            @Override
            public void onSucess(User remoteUser) {
                if (remoteUser != null) {
                    getUser(remoteUser.getId(), callback);
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    @Override
    public void updateUserInboxState(apps.steve.fire.randomchat.main.ui.entity.User user, boolean state) {
        Log.d(TAG, "updateUserInboxState");
        fireUser.updateUserInboxState(convertUser(user), state);
    }

    @Override
    public void updateUserChatInboxState(apps.steve.fire.randomchat.main.ui.entity.User user, apps.steve.fire.randomchat.main.ui.entity.User receiver, boolean state) {
        Log.d(TAG, "updateUserChatInboxState");
        fireUser.updateUserChatInboxState(convertUser(user), convertUser(receiver), state);
    }

    @Override
    public void sendMessage(apps.steve.fire.randomchat.main.ui.entity.User sender, apps.steve.fire.randomchat.main.ui.entity.User receiver, Message message, final Callback<Message> callback) {
        Log.d(TAG, "sendMessage");
        fireUser.sendMessage(convertUser(sender), convertUser(receiver), convertMessage(message), new FirePostsCallback<apps.steve.fire.randomchat.data.source.remote.entity.Message>() {
            @Override
            public void onSuccess(apps.steve.fire.randomchat.data.source.remote.entity.Message remoteMessage) {
                if (remoteMessage != null) {
                    callback.onSucess(convertMessage(remoteMessage));
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    @Override
    public void getMessages(String chatId, final Callback<Message> callback) {
        fireUser.getMessages(chatId, new FirePostsCallback<apps.steve.fire.randomchat.data.source.remote.entity.Message>() {
            @Override
            public void onSuccess(apps.steve.fire.randomchat.data.source.remote.entity.Message remoteMessage) {
                if (remoteMessage != null) {
                    callback.onSucess(convertMessage(remoteMessage));
                }
            }
        });
    }

    @Override
    public void removeMessagesListener(String chatId) {
        fireUser.removeMessagesListener(chatId);
    }

    @Override
    public void getMessagesFromInbox(apps.steve.fire.randomchat.main.ui.entity.User user, final Callback<Message> callback) {
        Log.d(TAG, "getMessagesFromInbox");
        fireUser.getMessagesFromImbox(convertUser(user), new FirePostsCallback<apps.steve.fire.randomchat.data.source.remote.entity.Message>() {
            @Override
            public void onSuccess(apps.steve.fire.randomchat.data.source.remote.entity.Message remoteMessage) {
                if (remoteMessage != null) {
                    getUserByMessage(remoteMessage, callback);
                }
            }
        });
    }

    @Override
    public void removeInboxMessageListener(apps.steve.fire.randomchat.main.ui.entity.User user) {
        fireUser.removeInboxMessageListener(convertUser(user));
    }

    private Message convertMessage(apps.steve.fire.randomchat.data.source.remote.entity.Message remoteMessage) {
        Message uiMessage = new Message();
        uiMessage.setId(remoteMessage.getId());
        uiMessage.setMessageText(remoteMessage.getMessageText());
        uiMessage.setContentType(remoteMessage.getContentType());
        uiMessage.setMediaUrl(remoteMessage.getMediaUrl());
        uiMessage.setMessageStatus(remoteMessage.getMessageStatus());
        uiMessage.setTimestamp(remoteMessage.getTimestamp());
        uiMessage.setIncommingMessage(remoteMessage.isIncommingMessage());
        apps.steve.fire.randomchat.main.ui.entity.User user = new apps.steve.fire.randomchat.main.ui.entity.User();
        user.setId(remoteMessage.getUserId());
        uiMessage.setUser(user);
        return uiMessage;
    }

    private apps.steve.fire.randomchat.data.source.remote.entity.Message convertMessage(Message uiMessage) {
        apps.steve.fire.randomchat.data.source.remote.entity.Message remoteMessage = new apps.steve.fire.randomchat.data.source.remote.entity.Message();
        remoteMessage.setId(uiMessage.getId());
        remoteMessage.setMessageText(uiMessage.getMessageText());
        remoteMessage.setUserId(uiMessage.getUser().getId());
        remoteMessage.setContentType(uiMessage.getContentType());
        remoteMessage.setMediaUrl(uiMessage.getMediaUrl());
        remoteMessage.setMessageStatus(uiMessage.getMessageStatus());
        remoteMessage.setTimestamp(uiMessage.getTimestamp());
        return remoteMessage;
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

    private void getUserByComment(final apps.steve.fire.randomchat.data.source.remote.entity.Comment remoteComment, final Callback<Comment> callback) {
        fireUser.getUser(remoteComment.getUserId(), new FirePostsCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    Comment uiComment = convertComment(remoteComment);
                    uiComment.setUser(convertUser(user));
                    callback.onSucess(uiComment);
                } else {
                    callback.onSucess(null);
                }
            }
        });
    }

    private void getUserByMessage(final apps.steve.fire.randomchat.data.source.remote.entity.Message remoteMessage, final Callback<Message> callback) {
        fireUser.getUser(remoteMessage.getUserId(), new FirePostsCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    Message uiMessage = convertMessage(remoteMessage);
                    uiMessage.setUser(convertUser(user));
                    callback.onSucess(uiMessage);
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
        @DrawableRes int avatarDrawable = new AvatarUi(avatar).getAvatarDrawable();
        @DrawableRes int genderDrawable = getGenderDrawable(gender);

        userUi.setDescription(user.getDescription());
        userUi.setAvatarDrawable(avatarDrawable);
        userUi.setGenderDrawable(genderDrawable);
        userUi.setCoins(user.getCoins());
        userUi.setCommentCount(user.getCommentCount());
        userUi.setPostCount(user.getPostCount());
        return userUi;
    }

    private User convertUser(apps.steve.fire.randomchat.main.ui.entity.User uiUser) {
        User remoteUser = new User();
        remoteUser.setId(uiUser.getId());
        remoteUser.setName(uiUser.getName());
        remoteUser.setDescription(uiUser.getDescription());
        remoteUser.setGender(uiUser.getGender());
        remoteUser.setAvatar(uiUser.getAvatar());
        return remoteUser;
    }

    private int getGenderDrawable(String gender) {
        @DrawableRes int genderDrawable = R.drawable.boy_casual;
        if (gender != null) {
            switch (gender) {
                case "WOMAN":
                    genderDrawable = R.drawable.boy_casual;
                    break;
            }
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
            case NB_YOUNG:
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
        remotePost.setHashtagList(post.getHashtags());
        remotePost.setLocation(post.getLocation());
        remotePost.setPopular(post.isPopular());
        remotePost.setTimestamp(post.getTimestamp());
        remotePost.setUserId(post.getUser().getId());
        remotePost.setCommentCount(post.getCommentCount());
        remotePost.setFavoriteCount(post.getFavoriteCount());
        remotePost.setDislikeCount(post.getDislikeCount());
        return remotePost;
    }

    private Post convertPost(apps.steve.fire.randomchat.data.source.remote.entity.Post remotePost) {
        Post post = new Post();
        post.setId(remotePost.getId());
        post.setContentText(remotePost.getContentText());

        post.setHashtags(remotePost.getHashtagList());

        post.setLocation(remotePost.getLocation());
        post.setPopular(remotePost.isPopular());
        post.setCommentCount(remotePost.getCommentCount());
        post.setFavoriteCount(remotePost.getFavoriteCount());
        post.setDislikeCount(remotePost.getDislikeCount());
        post.setTimestamp(remotePost.getTimestamp());
        return post;
    }

    private apps.steve.fire.randomchat.data.source.remote.entity.Comment convertComment(Comment comment) {
        apps.steve.fire.randomchat.data.source.remote.entity.Comment remoteComment = new apps.steve.fire.randomchat.data.source.remote.entity.Comment();
        remoteComment.setId(comment.getId());
        remoteComment.setCommentText(comment.getCommentText());
        remoteComment.setPostId(comment.getPostId());
        remoteComment.setUserId(comment.getUser().getId());
        remoteComment.setDislikeCount(comment.getDislikeCount());
        remoteComment.setFavoriteCount(comment.getFavoriteCount());
        return remoteComment;
    }

    private Comment convertComment(apps.steve.fire.randomchat.data.source.remote.entity.Comment comment) {
        Comment uiComment = new Comment();
        uiComment.setId(comment.getId());
        uiComment.setCommentText(comment.getCommentText());
        uiComment.setPostId(comment.getPostId());
        uiComment.setDislikeCount(comment.getDislikeCount());
        uiComment.setFavoriteCount(comment.getFavoriteCount());
        return uiComment;
    }
}
