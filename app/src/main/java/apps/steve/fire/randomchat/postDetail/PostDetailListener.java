package apps.steve.fire.randomchat.postDetail;

import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 4/12/2017.
 */

public interface PostDetailListener {
    void onUserSelected(User user);
    void onCommentSelected(Comment comment);
}
