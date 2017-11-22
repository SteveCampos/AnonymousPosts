package apps.steve.fire.randomchat.data.source.remote;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.data.source.remote.entity.User;
import apps.steve.fire.randomchat.data.source.remote.firebase.FireUser;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UserRemoteDataSource implements UserDataSource {

    private FireUser fireUser;

    public UserRemoteDataSource(FireUser fireUser) {
        this.fireUser = fireUser;
    }

    @Override
    public void updateUser(FirebaseUser firebaseUser, AvatarUi avatar, String gender, final Callback<Boolean> callback) {

        User user = new User();
        user.setName(avatar.getName());
        user.setAvatar(avatar.getAvatarName());
        user.setCoins(5);
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
}
