package apps.steve.fire.randomchat.data.source;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Message;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;

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

    void publishComment(Comment comment, Callback<Comment> callback);

    void getPostComments(Post post, Callback<Comment> callback);

    void removePostCommentsListener(Post post);

    void getUser(String id, Callback<User> callback);

    void updateUser(User user, Callback<User> callback);

    void sendMessage(User sender, User receiver, Message message, Callback<Message> callback);

    void getMessages(String chatId, Callback<Message> callback);

    void removeMessagesListener(String chatId);

    void getMessagesFromInbox(User user, Callback<Message> callback);

    void removeInboxMessageListener(User user);
}
