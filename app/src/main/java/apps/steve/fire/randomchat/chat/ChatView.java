package apps.steve.fire.randomchat.chat;

import apps.steve.fire.randomchat.base.BaseView;
import apps.steve.fire.randomchat.main.ui.entity.Message;

/**
 * Created by Steve on 13/12/2017.
 */

public interface ChatView extends BaseView<ChatPresenter> {
    void showProgress();

    void hideProgress();

    void addMessage(Message message);

    void hideSoftboard();

    void cleanEdtMessage();
}
