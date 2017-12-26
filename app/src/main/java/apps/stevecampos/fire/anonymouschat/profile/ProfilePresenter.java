package apps.stevecampos.fire.anonymouschat.profile;

import apps.stevecampos.fire.anonymouschat.base.BaseFragmentPresenter;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by Steve on 8/12/2017.
 */

public interface ProfilePresenter extends BaseFragmentPresenter<ProfileView> {
    void setUser(User user, boolean editable);
    void onEditNameClicked();

    void onEditNameSubmit(String name);

    void onEditDescriptionClicked();

    void onEditDescriptionSubmit(String description);
}
