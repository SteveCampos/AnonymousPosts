package apps.steve.fire.randomchat.messages;

import apps.steve.fire.randomchat.base.BaseView;
import apps.steve.fire.randomchat.main.ui.entity.Message;

/**
 * Created by @stevecampos on 19/12/2017.
 */

public interface MessageView extends BaseView<MessagePresenter> {
    void showProgress();

    void hideProgress();

    void showEmptyView();

    void hideEmptyView();


    void addMessage(Message message);

    void changeMessage(Message message);

    void deleteMessage(Message message);

}
