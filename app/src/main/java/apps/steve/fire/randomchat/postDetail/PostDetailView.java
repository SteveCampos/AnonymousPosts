package apps.steve.fire.randomchat.postDetail;

import android.support.annotation.DrawableRes;

import apps.steve.fire.randomchat.base.BaseView;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 4/12/2017.
 */

public interface PostDetailView extends BaseView<PostDetailPresenter>{
    void showProgress();

    void hideProgress();

    void showUserAvatarDrawable(@DrawableRes int avatarDrawable);

    void showContentText(String contentText);

    void showFavCount(long favCount);

    void showCommentCount(long commentCount);

    void showCrown();

    void hideCrown();

    void addComment(Comment comment);

    void onUserClicked(User user);

    void showError(String error);

    void showEdtContent(String contentText);
    void hideSoftboard();
}
