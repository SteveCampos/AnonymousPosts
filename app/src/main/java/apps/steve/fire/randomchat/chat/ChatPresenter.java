package apps.steve.fire.randomchat.chat;

import apps.steve.fire.randomchat.base.BasePresenter;

/**
 * Created by Steve on 13/12/2017.
 */

public interface ChatPresenter extends BasePresenter<ChatView> {
    void setExtras(String mainUserId, String receptorId);
    void onMessageSubmit(String content);
}
