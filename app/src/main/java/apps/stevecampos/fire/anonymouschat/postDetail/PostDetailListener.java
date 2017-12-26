package apps.stevecampos.fire.anonymouschat.postDetail;

import apps.stevecampos.fire.anonymouschat.main.ui.entity.Comment;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 4/12/2017.
 */

public interface PostDetailListener {
    void onUserSelected(User user);
    void onCommentSelected(Comment comment);
}
