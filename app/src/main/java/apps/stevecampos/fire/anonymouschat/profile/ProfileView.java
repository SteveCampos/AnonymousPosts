package apps.stevecampos.fire.anonymouschat.profile;

import android.support.annotation.DrawableRes;

import apps.stevecampos.fire.anonymouschat.base.BaseView;

/**
 * Created by @stevecampos on 4/12/2017.
 */

public interface ProfileView extends BaseView<ProfilePresenter> {
    void showName(String name, boolean editable);

    void showAvatar(@DrawableRes int avatarDrawable, boolean editable);

    void showLocation(String location, boolean editable);

    void showCoinCount(long coinCount);

    void showDescription(String description, boolean editable);


    void showDialogEditName();


    void showDialogEditDescription();
}
