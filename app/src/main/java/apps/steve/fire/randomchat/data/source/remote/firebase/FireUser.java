package apps.steve.fire.randomchat.data.source.remote.firebase;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String TAG = FireUser.class.getSimpleName();

    public FireUser() {
        super();
    }

    @Override
    public void updateUser(final User user, final Callback<User> callback) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String userId = user.getId();
        Log.d(TAG, "updateUser: " + userId);
        /*Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(PATH_USER + userId, userValues);*/

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
        childUpdates.put(PATH_USER_POST + postId, postValues);
        childUpdates.put(PATH_POST_RECENTS + postId, postValues);

        if (post.isPopular()) {
            childUpdates.put(PATH_POST_POPULAR + postId, postValues);
        }
        String location = post.getLocation();
        if (!TextUtils.isEmpty(location)) {
            childUpdates.put(PATH_LOCATION_POST + location + "/" + postId, postValues);
        }
        List<String> hashtags = post.getHashtagList();
        if (!hashtags.isEmpty()) {
            for (String hashtag :
                    hashtags) {
                childUpdates.put(PATH_HASHTAG_POST + hashtag + "/" + postId, postValues);
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

    private void getPosts(String path, final FirePostsCallback<Post> callback) {
        mDatabase.child(path).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded dataSnapshot");
                parsePost(dataSnapshot, callback);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged dataSnapshot");
                parsePost(dataSnapshot, callback);
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

    public void sendMessage(User sender, User receiver, final Message message, final FirePostsCallback<Message> callback) {
        String chatId = getId(sender.getId(), receiver.getId());
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
        //childUpdates.put(PATH_USER_INBOX + receiverId + "/" + chatId + "/lastMessage", messageValues);
        childUpdates.put(PATH_USER_INBOX + receiverId + "/" + chatId, messageValues);
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

    public static String[] sortAlphabetical(String key1, String key2) {
        String temp = null;
        int compare = key1.compareTo(key2);//Comparing strings by their alphabetical order
        if (compare > 0) {
            temp = key2;
            key2 = key1;
            key1 = temp;
        }
        return new String[]{key1, key2};
    }

    public static String getId(String id1, String id2) {
        String[] idsOrdered = sortAlphabetical(id1, id2);
        return idsOrdered[0] + "-" + idsOrdered[1];
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

    private void parseMessage(DataSnapshot dataSnapshot, FirePostsCallback<Message> callback) {
        Log.d(TAG, "parseMessage dataSnapshot: " + dataSnapshot);
        Message message = dataSnapshot.getValue(Message.class);
        if (message != null) {
            callback.onSuccess(message);
        }
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


    private FirePostsCallback<Message> inboxCallback;
    private ChildEventListener inboxMessagesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            parseMessage(dataSnapshot, inboxCallback);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            parseMessage(dataSnapshot, inboxCallback);
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
        mDatabase.child(PATH_CHAT_MESSAGES + user.getId())
                .removeEventListener(inboxMessagesListener);
        inboxMessagesListener = null;
    }
}
