package apps.stevecampos.fire.anonymouschat.posts;

import android.os.Bundle;

import apps.stevecampos.fire.anonymouschat.base.BaseFragmentPresenter;

/**
 * Created by @stevecampos on 20/12/2017.
 */

public interface PostPresenter extends BaseFragmentPresenter<PostView> {
    void setExtras(Bundle arguments);
}
