package apps.steve.fire.randomchat.data.source.remote.firebase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

import apps.steve.fire.randomchat.data.source.remote.callback.Callback;
import apps.steve.fire.randomchat.data.source.remote.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class FireUser extends Fire implements FireUserContract {

    public FireUser() {
        super();
    }

    @Override
    public void updateUser(final User user, final Callback<User> callback) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = user.getId();
        Map<String, Object> userValues = user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + key, userValues);
        //childUpdates.put("/user-posts/" + userId + "/" + key, userValues);

        mDatabase
                .updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSucess(user);
                        } else {
                            callback.onSucess(null);
                        }
                    }
                });
    }
}
