package apps.steve.fire.randomchat.data.source;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.intro.entity.AvatarUi;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public interface UserDataSource {
    interface Callback<T> {
        void onSucess(T success);
    }

    void updateUser(FirebaseUser user, AvatarUi avatar, String gender, Callback<Boolean> callback);
}
