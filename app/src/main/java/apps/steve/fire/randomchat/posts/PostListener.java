package apps.steve.fire.randomchat.posts;

import apps.steve.fire.randomchat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 3/12/2017.
 */

public interface PostListener {
    void onPostSelected(Post post);

    void onTagClick(Post post, String tag);
}
