package apps.steve.fire.randomchat.data.source;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public interface UserDataSource {
    interface Callback<T> {
        void onSucess(T object);
    }

    void updateUser(FirebaseUser user, AvatarUi avatar, String gender, Callback<Boolean> callback);
    void publishPost(Post post, Callback<Post> callback);
    void getPopularPosts(Callback<Post> callback);
}
