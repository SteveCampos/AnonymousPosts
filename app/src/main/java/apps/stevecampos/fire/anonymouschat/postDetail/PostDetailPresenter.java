package apps.stevecampos.fire.anonymouschat.postDetail;

import apps.stevecampos.fire.anonymouschat.base.BaseFragmentPresenter;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public interface PostDetailPresenter extends BaseFragmentPresenter<PostDetailView> {
    void setPost(Post post);

    void publishCommentBtnClicked(String commentText);
}
