package apps.steve.fire.randomchat.intro;

import java.util.List;

import apps.steve.fire.randomchat.base.BaseView;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;

/**
 * Created by Steve on 11/11/2017.
 */

public interface IntroView extends BaseView<IntroPresenter> {
    void showStatusBarTranslucent();

    /*GenderSlide*/
    void showGenderImg(int drawable);

    void showGenderImgDescr(CharSequence description);

    /*AvatarSlide*/
    void updateAvatar(AvatarUi avatar);

    void toggleAvatars(AvatarUi old, AvatarUi actual);

    void showAvatarList(List<AvatarUi> avatarList);
}
