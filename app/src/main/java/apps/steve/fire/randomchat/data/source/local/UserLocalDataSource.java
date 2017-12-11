package apps.steve.fire.randomchat.data.source.local;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UserLocalDataSource implements UserDataSource {
    public UserLocalDataSource() {
    }

    @Override
    public void publishPost(Post post, Callback<Post> callback) {

    }

    @Override
    public void getPopularPosts(Callback<Post> callback) {

    }

    @Override
    public void getRecentPosts(Callback<Post> callback) {

    }

    @Override
    public void publishComment(Comment comment, Callback<Comment> callback) {

    }

    @Override
    public void getPostComments(Post post, Callback<Comment> callback) {

    }

    @Override
    public void removePostCommentsListener(Post post) {

    }

    @Override
    public void getUser(String id, Callback<User> callback) {

    }

    @Override
    public void updateUser(User user, Callback<User> callback) {

    }
}
