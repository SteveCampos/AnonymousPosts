package apps.steve.fire.randomchat.posts;

import java.util.List;

import apps.steve.fire.randomchat.base.BaseView;
import apps.steve.fire.randomchat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 29/11/2017.
 */

public interface PostView extends BaseView<PostPresenter> {
    void showProgress();

    void hideProgress();

    void showEmptyView();

    void hideEmptyView();

    void addPost(Post post);

    void changePost(Post post);

    void deletePost(Post post);

    void addPostList(List<Post> postList);
}
