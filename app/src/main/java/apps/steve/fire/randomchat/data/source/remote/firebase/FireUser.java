package apps.steve.fire.randomchat.data.source.remote.firebase;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apps.steve.fire.randomchat.Utils;
import apps.steve.fire.randomchat.data.source.remote.callback.Callback;
import apps.steve.fire.randomchat.data.source.remote.entity.Comment;
import apps.steve.fire.randomchat.data.source.remote.entity.Message;
import apps.steve.fire.randomchat.data.source.remote.entity.Post;
import apps.steve.fire.randomchat.data.source.remote.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class FireUser extends Fire implements FireUserContract {

    public static final String PATH_USER = "/users/";
    public static final String PATH_POST = "/posts/";
    public static final String PATH_COMMENT = "/comments/";
    public static final String PATH_POST_COMMENTS = "/post-comments/";
    public static final String PATH_COMMENT_LIKE = "/comment-like/";
    public static final String PATH_USER_LIKE = "/user-like/";
    public static final String PATH_USER_COMMENT = "/user-comment/";
    public static final String PATH_USER_POST = "/user-post/";
    public static final String PATH_LOCATION_POST = "/location-post/";
    public static final String PATH_POST_POPULAR = "/post-popular/";
    public static final String PATH_POST_RECENTS = "/post-recents/";
    public static final String PATH_HASHTAG_POST = "/hashtag-post/";

    public static final String PATH_CHATS = "/chats/";
    public static final String PATH_USER_CHATS = "/user-chats/";
    public static final String PATH_CHAT_MESSAGES = "/chat-messages/";
    public static final String PATH_USER_INBOX = "/user-inbox/";
    public static final String PATH_USER_INBOX_STATE = "/user-inbox-state/";
    private static final String TAG = FireUser.class.getSimpleName();

    public FireUser() {
        super();
    }

    @Override
    public void updateUser(final User user, final Callback<User> callback) {
        String userId = user.getId();
        Log.d(TAG, "updateUser: " + userId);
        mDatabase
                .updateChildren(user.toMap())
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
        String postId = mDatabase.child(PATH_POST).push().getKey();
        post.setId(postId);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_POST + postId, postValues);
        childUpdates.put(PATH_USER_POST + postId, true);
        childUpdates.put(PATH_POST_RECENTS + postId, true);

        if (post.isPopular()) {
            childUpdates.put(PATH_POST_POPULAR + postId, true);
            decrementUserCoin(post.getUserId());
        }
        String location = post.getLocation();
        if (!TextUtils.isEmpty(location)) {
            childUpdates.put(PATH_LOCATION_POST + location + "/" + postId, true);
        }
        List<String> hashtags = post.getHashtagList();
        if (!hashtags.isEmpty()) {
            for (String hashtag :
                    hashtags) {
                childUpdates.put(PATH_HASHTAG_POST + hashtag + "/" + postId, true);
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

    private void decrementUserCoin(String userId) {
        User user = new User();
        user.setId(userId);
        updateUserCoins(user, -1, null);
    }


    @Override
    public void commentPost(Comment comment, final Callback<Comment> callback) {
        // Create new comment by post
        String commentId = mDatabase.child(PATH_COMMENT).push().getKey();
        comment.setId(commentId);

        Map<String, Object> commentValues = comment.toMap();

        String userId = comment.getUserId();
        String postId = comment.getPostId();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_COMMENT + commentId, commentValues);
        childUpdates.put(PATH_POST_COMMENTS + postId + "/" + commentId, commentValues);
        childUpdates.put(PATH_USER_COMMENT + userId + "/" + commentId, commentValues);

        updatePostCommentCount(postId);

        final Comment comment1 = comment;
        mDatabase
                .updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSucess(comment1);
                        } else {
                            callback.onSucess(null);
                        }
                    }
                });
    }

    private void updatePostCommentCount(String postId) {
        mDatabase.child(PATH_POST + postId)
                .runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Log.d(TAG, "doTransaction");
                        Post p = mutableData.getValue(Post.class);
                        if (p == null) {
                            return Transaction.success(mutableData);
                        }
                        p.setCommentCount(p.getCommentCount() + 1);
                        // Set value and report transaction success
                        mutableData.setValue(p);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b,
                                           DataSnapshot dataSnapshot) {
                        // Transaction completed
                        Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                    }
                });
    }

    public void updatePostFavCount(String postId) {
        mDatabase.child(PATH_POST + postId)
                .runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Log.d(TAG, "doTransaction");
                        Post p = mutableData.getValue(Post.class);
                        if (p == null) {
                            return Transaction.success(mutableData);
                        }
                        p.setFavoriteCount(p.getFavoriteCount() + 1);
                        // Set value and report transaction success
                        mutableData.setValue(p);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b,
                                           DataSnapshot dataSnapshot) {
                        // Transaction completed
                        Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                    }
                });
    }

    public void updateUserCoins(final User user, final long coins, final Callback<User> callback) {
        mDatabase.child(PATH_USER + user.getId())
                .runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        Log.d(TAG, "doTransaction");
                        User user = mutableData.getValue(User.class);
                        if (user == null) {
                            return Transaction.success(mutableData);
                        }
                        user.setCoins(user.getCoins() + coins);
                        // Set value and report transaction success
                        mutableData.setValue(user);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(DatabaseError databaseError, boolean b,
                                           DataSnapshot dataSnapshot) {
                        // Transaction completed
                        Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                        if (databaseError == null && callback != null) {
                            callback.onSucess(user);
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

    public void getUser(String userId, final FirePostsCallback<User> userCallback) {
        mDatabase.child(PATH_USER + userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("FireUser", "dataSnapshot: " + dataSnapshot);
                        User user = dataSnapshot.getValue(User.class);
                        userCallback.onSuccess(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        userCallback.onSuccess(null);
                    }
                }
        );
    }


    public void getPopularPost(FirePostsCallback<Post> callback) {
        Log.d(TAG, "getPopularPost");
        getPosts(PATH_POST_POPULAR, callback);
    }

    public void getRecentPosts(FirePostsCallback<Post> callback) {
        Log.d(TAG, "getPopularPost");
        getPosts(PATH_POST_RECENTS, callback);
    }

    public void getPostWithTag(String tag, FirePostsCallback<Post> callback) {
        Log.d(TAG, "getPostWithTag");
        getPosts(PATH_HASHTAG_POST + tag, callback);
    }

    private void getPost(String id, final FirePostsCallback<Post> callback) {
        mDatabase.child(PATH_POST + id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "getPost onDataChange dataSnapshot: " + dataSnapshot);
                parsePost(dataSnapshot, callback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError: " + databaseError);
            }
        });
    }

    private void getPosts(String path, final FirePostsCallback<Post> callback) {
        Log.d(TAG, "getPost path: " + path);
        mDatabase.child(path).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "getPosts onChildAdded dataSnapshot: " + dataSnapshot);
                if (dataSnapshot == null) return;
                String postId = dataSnapshot.getKey();
                getPost(postId, callback);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged dataSnapshot");
                //parsePost(dataSnapshot, callback);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void parsePost(DataSnapshot dataSnapshot, FirePostsCallback<Post> callback) {
        Log.d(TAG, "parsePost dataSnapshot: " + dataSnapshot);
        Post post = dataSnapshot.getValue(Post.class);
        if (post != null) {
            Log.d(TAG, "post parsed: " + post.toString());
            callback.onSuccess(post);
        }
    }

    public void getPostComments(String postId, final FirePostsCallback<Comment> commentsCallback) {
        Log.d(TAG, "getPostComments");
        this.commentsCallback = commentsCallback;
        mDatabase.child(PATH_POST_COMMENTS + postId)
                .addChildEventListener(postCommentsListener);
    }

    public void removePostCommentsListener(String postId) {
        Log.d(TAG, "removePostCommentsListener");
        commentsCallback = null;
        mDatabase.child(PATH_POST_COMMENTS + postId)
                .removeEventListener(postCommentsListener);
        postCommentsListener = null;
    }

    private FirePostsCallback<Comment> commentsCallback;
    private ChildEventListener postCommentsListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            parseComment(dataSnapshot, commentsCallback);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            parseComment(dataSnapshot, commentsCallback);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void parseComment(DataSnapshot dataSnapshot, FirePostsCallback<Comment> callback) {
        Log.d(TAG, "parseComment dataSnapshot: " + dataSnapshot);
        Comment comment = dataSnapshot.getValue(Comment.class);
        if (comment != null) {
            callback.onSuccess(comment);
        }
    }

    public void updateUserInboxState(User user, boolean state) {
        mDatabase.child(PATH_USER_INBOX_STATE + user.getId()).setValue(state);
    }

    public void updateUserChatInboxState(User mainUser, User receiver, boolean state) {
        String mainUserId = mainUser.getId();
        String chatId = Utils.getId(mainUserId, receiver.getId());
        mDatabase.child(PATH_USER_INBOX + mainUserId + "/" + chatId).setValue(state);
    }

    public void sendMessage(User sender, User receiver, final Message message, final FirePostsCallback<Message> callback) {
        String chatId = Utils.getId(sender.getId(), receiver.getId());
        String messageId = mDatabase.child(PATH_CHATS + chatId).push().getKey();

        message.setId(messageId);

        String messageText = message.getMessageText();
        String senderId = sender.getId();
        String receiverId = receiver.getId();
        String contentType = message.getContentType();
        long timestamp = message.getTimestamp();
        int messageStatus = message.getMessageStatus();
        String mediaUrl = message.getMediaUrl();

        Map<String, Object> messageValues = message.toMap();


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_CHATS + chatId + "/lastMessage", messageValues);
        childUpdates.put(PATH_USER_CHATS + senderId + "/" + chatId, true);
        childUpdates.put(PATH_USER_CHATS + receiverId + "/" + chatId, true);
        childUpdates.put(PATH_CHAT_MESSAGES + chatId + "/" + messageId, messageValues);
        childUpdates.put(PATH_USER_INBOX + receiverId + "/" + chatId + "/", true);//Agregar este chat, al inbox del usuario
        childUpdates.put(PATH_USER_INBOX_STATE + receiverId + "/", true);
        mDatabase
                .updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(message);
                        } else {
                            callback.onSuccess(null);
                        }
                    }
                });
    }

    public void getMessages(String chatId, FirePostsCallback<Message> callback) {
        Log.d(TAG, "getMessages");
        this.messageCallback = callback;
        mDatabase.child(PATH_CHAT_MESSAGES + chatId)
                .addChildEventListener(messagesListener);
    }

    private FirePostsCallback<Message> messageCallback;
    private ChildEventListener messagesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            parseMessage(dataSnapshot, messageCallback);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            parseMessage(dataSnapshot, messageCallback);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void parseMessage(DataSnapshot dataSnapshot, boolean incommingMessage, FirePostsCallback<Message> callback) {
        Log.d(TAG, "parseMessage dataSnapshot: " + dataSnapshot);
        Message message = dataSnapshot.getValue(Message.class);
        if (message != null) {
            message.setIncommingMessage(incommingMessage);
            callback.onSuccess(message);
        }
    }

    private void parseMessage(DataSnapshot dataSnapshot, FirePostsCallback<Message> callback) {
        parseMessage(dataSnapshot, false, callback);
    }

    public void removeMessagesListener(String chatId) {
        Log.d(TAG, "removeMessagesListener");
        messageCallback = null;
        mDatabase.child(PATH_CHAT_MESSAGES + chatId)
                .removeEventListener(messagesListener);
        messagesListener = null;
    }

    public void getMessagesFromImbox(User user, FirePostsCallback<Message> callback) {
        Log.d(TAG, "getMessagesFromImbox");
        this.inboxCallback = callback;
        mDatabase.child(PATH_USER_INBOX + user.getId())
                .addChildEventListener(inboxMessagesListener);
    }

    private void getLastMessage(String chatId, final Boolean incommingMessage, final FirePostsCallback<Message> callback) {
        mDatabase.child(PATH_CHATS + chatId + "/lastMessage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "getLastMessage onDataChange dataSnapshot: " + dataSnapshot);
                parseMessage(dataSnapshot, incommingMessage, callback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "databaseError: " + databaseError);
            }
        });
    }

    private FirePostsCallback<Message> inboxCallback;
    private ChildEventListener inboxMessagesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.d(TAG, "inboxMessageListener dataSnapshot: " + dataSnapshot);
            if (dataSnapshot == null) return;
            String chatId = dataSnapshot.getKey();
            Boolean incommingMessage = dataSnapshot.getValue(Boolean.class);
            getLastMessage(chatId, incommingMessage, inboxCallback);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void removeInboxMessageListener(User user) {
        Log.d(TAG, "removeInboxMessageListener");
        inboxCallback = null;
        mDatabase.child(PATH_USER_INBOX + user.getId())
                .removeEventListener(inboxMessagesListener);
        inboxMessagesListener = null;
    }

    private Callback<Boolean> userIncommingStateCallback;
    private ValueEventListener userIncommingMessageStateListener =
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "listenUserInboxState dataSnapshot: " + dataSnapshot);
                    if (dataSnapshot == null) return;
                    Boolean state = dataSnapshot.getValue(Boolean.class);
                    if (userIncommingStateCallback != null) {
                        userIncommingStateCallback.onSucess(state);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "listenUserInboxState onCancelled databaseError: " + databaseError);
                }
            };

    public void listenUserInboxState(User user, final Callback<Boolean> callback) {
        Log.d(TAG, "listenUserInboxState");
        this.userIncommingStateCallback = callback;
        mDatabase.child(PATH_USER_INBOX_STATE + user.getId())
                .addValueEventListener(userIncommingMessageStateListener);
    }

    public void removetUserInboxStateListener(User user) {
        mDatabase.child(PATH_USER_INBOX_STATE + user.getId())
                .removeEventListener(userIncommingMessageStateListener);
        userIncommingStateCallback = null;
        userIncommingStateCallback = null;
    }

}
