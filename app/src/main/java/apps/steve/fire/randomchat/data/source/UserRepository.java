package apps.steve.fire.randomchat.data.source;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import apps.steve.fire.randomchat.data.source.local.UserLocalDataSource;
import apps.steve.fire.randomchat.data.source.remote.UserRemoteDataSource;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Post;

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

    @Override
    public void updateUser(FirebaseUser user, AvatarUi avatar, String gender, Callback<Boolean> callback) {
        localDataSource.updateUser(user, avatar, gender, callback);
        remoteDataSource.updateUser(user, avatar, gender, callback);
    }

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
}
