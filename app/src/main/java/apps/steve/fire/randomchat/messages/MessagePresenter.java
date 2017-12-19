package apps.steve.fire.randomchat.messages;

import apps.steve.fire.randomchat.base.BaseFragmentPresenter;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 19/12/2017.
 */

public interface MessagePresenter extends BaseFragmentPresenter<MessageView> {
    void setUser(User mainUser);
}
