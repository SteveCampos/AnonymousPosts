package apps.steve.fire.randomchat.data.source.local;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UserLocalDataSource implements UserDataSource {
    public UserLocalDataSource() {
    }

    @Override
    public void updateUser(FirebaseUser user, AvatarUi avatar, String gender, Callback callback) {

    }
}
