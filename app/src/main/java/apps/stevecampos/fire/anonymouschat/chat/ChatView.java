package apps.stevecampos.fire.anonymouschat.chat;

import apps.stevecampos.fire.anonymouschat.base.BaseView;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;

/**
 * Created by Steve on 13/12/2017.
 */

public interface ChatView extends BaseView<ChatPresenter> {
    void showProgress();

    void hideProgress();

    void addMessage(Message message);

    void hideSoftboard();

    void cleanEdtMessage();

    void showName(String name);
}
