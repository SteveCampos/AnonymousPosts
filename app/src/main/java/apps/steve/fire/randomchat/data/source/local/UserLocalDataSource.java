package apps.steve.fire.randomchat.data.source.local;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UserLocalDataSource implements UserDataSource {
    public UserLocalDataSource() {
    }

    @Override
    public void updateUser(FirebaseUser user, AvatarUi avatar, String gender, Callback callback) {

    }

    @Override
    public void publishPost(Post post, Callback<Post> callback) {

    }

    @Override
    public void getPopularPosts(Callback<Post> callback) {

    }
}
