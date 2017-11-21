package apps.steve.fire.randomchat.intro;

import android.support.v4.app.Fragment;

import apps.steve.fire.randomchat.base.BasePresesenter;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface IntroPresenter extends BasePresesenter<IntroView> {

    void onSlideChanged(Fragment oldFragment, Fragment newFragment);

    void onNextGender();

    void onAvatarSelected(AvatarUi avatar);

    void signIn();
    void signOut();
}
