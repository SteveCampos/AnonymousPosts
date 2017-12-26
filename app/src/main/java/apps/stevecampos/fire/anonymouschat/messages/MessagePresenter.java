package apps.stevecampos.fire.anonymouschat.messages;

import apps.stevecampos.fire.anonymouschat.base.BaseFragmentPresenter;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 19/12/2017.
 */

public interface MessagePresenter extends BaseFragmentPresenter<MessageView> {
    void setUser(User mainUser);
}
