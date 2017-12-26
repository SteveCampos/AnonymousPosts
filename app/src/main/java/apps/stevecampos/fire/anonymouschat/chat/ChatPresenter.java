package apps.stevecampos.fire.anonymouschat.chat;

import apps.stevecampos.fire.anonymouschat.base.BasePresenter;

/**
 * Created by Steve on 13/12/2017.
 */

public interface ChatPresenter extends BasePresenter<ChatView> {
    void setExtras(String mainUserId, String receptorId);
    void onMessageSubmit(String content);
}
