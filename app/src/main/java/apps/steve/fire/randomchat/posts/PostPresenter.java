package apps.steve.fire.randomchat.posts;

import android.os.Bundle;
import android.util.Log;

import apps.steve.fire.randomchat.base.BaseFragmentPresenter;
import apps.steve.fire.randomchat.base.BasePresenter;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.usecase.GetPostComments;
import apps.steve.fire.randomchat.main.usecase.PublishComment;
import apps.steve.fire.randomchat.postDetail.PostDetailView;

/**
 * Created by @stevecampos on 20/12/2017.
 */

public interface PostPresenter extends BaseFragmentPresenter<PostView> {
    void setExtras(Bundle arguments);
}
