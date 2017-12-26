package apps.stevecampos.fire.anonymouschat.posts;

import java.util.List;

import apps.stevecampos.fire.anonymouschat.base.BaseView;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;

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
