package apps.steve.fire.randomchat.postDetail;

import apps.steve.fire.randomchat.base.BaseFragmentPresenter;
import apps.steve.fire.randomchat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public interface PostDetailPresenter extends BaseFragmentPresenter<PostDetailView> {
    void setPost(Post post);

    void publishCommentBtnClicked(String commentText);
}
