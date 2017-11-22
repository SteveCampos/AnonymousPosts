package apps.steve.fire.randomchat.data.source;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.data.source.local.UserLocalDataSource;
import apps.steve.fire.randomchat.data.source.remote.UserRemoteDataSource;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;

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
}
