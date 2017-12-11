package apps.steve.fire.randomchat.data.source;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import apps.steve.fire.randomchat.data.source.local.UserLocalDataSource;
import apps.steve.fire.randomchat.data.source.remote.UserRemoteDataSource;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Message;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UserRepository implements UserDataSource {

    private UserLocalDataSource localDataSource;
    private UserRemoteDataSource remoteDataSource;

    public UserRepository(UserLocalDataSource localDataSource, UserRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    /*
    @Override
    public void updateUser(FirebaseUser user, AvatarUi avatar, String gender, Callback<Boolean> callback) {
        localDataSource.updateUser(user, avatar, gender, callback);
        remoteDataSource.updateUser(user, avatar, gender, callback);
    }*/

    @Override
    public void publishPost(Post post, Callback<Post> callback) {
        localDataSource.publishPost(post, callback);
        remoteDataSource.publishPost(post, callback);
    }

    @Override
    public void getPopularPosts(Callback<Post> callback) {
        localDataSource.getPopularPosts(callback);
        remoteDataSource.getPopularPosts(callback);
    }

    @Override
    public void getRecentPosts(Callback<Post> callback) {
        localDataSource.getRecentPosts(callback);
        remoteDataSource.getRecentPosts(callback);
    }

    @Override
    public void publishComment(Comment comment, Callback<Comment> callback) {
        localDataSource.publishComment(comment, callback);
        remoteDataSource.publishComment(comment, callback);
    }

    @Override
    public void getPostComments(Post post, Callback<Comment> callback) {
        localDataSource.getPostComments(post, callback);
        remoteDataSource.getPostComments(post, callback);
    }

    @Override
    public void removePostCommentsListener(Post post) {
        localDataSource.removePostCommentsListener(post);
        remoteDataSource.removePostCommentsListener(post);
    }

    @Override
    public void getUser(String id, Callback<User> callback) {
        localDataSource.getUser(id, callback);
        remoteDataSource.getUser(id, callback);
    }

    @Override
    public void updateUser(User user, Callback<User> callback) {
        localDataSource.updateUser(user, callback);
        remoteDataSource.updateUser(user, callback);
    }

    @Override
    public void sendMessage(User sender, User receiver, Message message, Callback<Message> callback) {
        localDataSource.sendMessage(sender, receiver, message, callback);
        remoteDataSource.sendMessage(sender, receiver, message, callback);
    }

    @Override
    public void getMessages(String chatId, Callback<Message> callback) {
        localDataSource.getMessages(chatId, callback);
        remoteDataSource.getMessages(chatId, callback);
    }

    @Override
    public void removeMessagesListener(String chatId) {
        localDataSource.removeMessagesListener(chatId);
        remoteDataSource.removeMessagesListener(chatId);
    }

    @Override
    public void getMessagesFromInbox(User user, Callback<Message> callback) {
        localDataSource.getMessagesFromInbox(user, callback);
        remoteDataSource.getMessagesFromInbox(user, callback);
    }

    @Override
    public void removeInboxMessageListener(User user) {
        localDataSource.removeInboxMessageListener(user);
        remoteDataSource.removeInboxMessageListener(user);
    }
}
