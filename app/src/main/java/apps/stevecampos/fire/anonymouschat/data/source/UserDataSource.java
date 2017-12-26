package apps.stevecampos.fire.anonymouschat.data.source;

import apps.stevecampos.fire.anonymouschat.main.ui.entity.Comment;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public interface UserDataSource {



    interface Callback<T> {
        void onSucess(T object);
    }

    void publishPost(Post post, Callback<Post> callback);

    void getPopularPosts(Callback<Post> callback);

    void getRecentPosts(Callback<Post> callback);

    void getPostWithTag(String tag, Callback<Post> callback);

    void publishComment(Comment comment, Callback<Comment> callback);

    void getPostComments(Post post, Callback<Comment> callback);

    void removePostCommentsListener(Post post);

    void listenUserInboxState(User user, Callback<Boolean> callback);

    void removeUserInboxStateListener(User user);

    void getUser(String id, Callback<User> callback);

    void updateUser(User user, Callback<User> callback);

    void updateUserCoins(User user, long coins, Callback<User> callback);

    void updateUserInboxState(User user, boolean state);

    void updateUserChatInboxState(User user, User receiver, boolean state);

    void sendMessage(User sender, User receiver, Message message, Callback<Message> callback);

    void getMessages(String chatId, Callback<Message> callback);

    void removeMessagesListener(String chatId);

    void getMessagesFromInbox(User user, Callback<Message> callback);

    void removeInboxMessageListener(User user);
}
