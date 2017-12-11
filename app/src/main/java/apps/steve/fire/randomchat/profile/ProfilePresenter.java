package apps.steve.fire.randomchat.profile;

import apps.steve.fire.randomchat.base.BaseFragmentPresenter;
import apps.steve.fire.randomchat.base.BasePresenter;
import apps.steve.fire.randomchat.main.ui.entity.User;

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
