package apps.stevecampos.fire.anonymouschat.data.source.remote.firebase;

import apps.stevecampos.fire.anonymouschat.data.source.remote.callback.Callback;
import apps.stevecampos.fire.anonymouschat.data.source.remote.entity.Comment;
import apps.stevecampos.fire.anonymouschat.data.source.remote.entity.Post;
import apps.stevecampos.fire.anonymouschat.data.source.remote.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public interface FireUserContract {

    void updateUser(User user, Callback<User> callback);

    void publishPost(Post post, Callback<Post> callback);

    void commentPost(Comment comment, Callback<Comment> callback);

    void likeComment(User user, Comment comment, Callback<Comment> callback);

}
