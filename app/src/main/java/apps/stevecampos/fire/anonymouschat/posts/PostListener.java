package apps.stevecampos.fire.anonymouschat.posts;

import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 3/12/2017.
 */

public interface PostListener {
    void onPostSelected(Post post);

    void onTagClick(Post post, String tag);
}
