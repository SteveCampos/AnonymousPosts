package apps.stevecampos.fire.anonymouschat.messages;

import apps.stevecampos.fire.anonymouschat.base.BaseView;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;

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
