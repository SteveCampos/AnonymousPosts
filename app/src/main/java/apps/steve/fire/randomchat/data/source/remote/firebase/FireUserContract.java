package apps.steve.fire.randomchat.data.source.remote.firebase;

import apps.steve.fire.randomchat.data.source.remote.callback.Callback;
import apps.steve.fire.randomchat.data.source.remote.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public interface FireUserContract {

    void updateUser(User user, Callback<User> callback);
}
