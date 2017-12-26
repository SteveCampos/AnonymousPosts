package apps.stevecampos.fire.anonymouschat.chat;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.util.Date;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseHandler;
import apps.stevecampos.fire.anonymouschat.chat.usecase.GetChatMessages;
import apps.stevecampos.fire.anonymouschat.chat.usecase.SendMessage;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;
import apps.stevecampos.fire.anonymouschat.main.usecase.GetUser;
import apps.stevecampos.fire.anonymouschat.main.usecase.UpdateUserChatInboxState;

/**
 * Created by Steve on 16/12/2017.
 */

public class ChatPresenterImpl implements ChatPresenter {
    private static final String TAG = ChatPresenterImpl.class.getSimpleName();
    private ChatView view;
    private Resources res;
    private UseCaseHandler handler;
    private GetUser useCaseGetMainUser;
    private GetUser useCaseGetUserReceptor;
    private SendMessage useCaseSendMessage;
    private GetChatMessages useCaseGetChatMessages;
    private UpdateUserChatInboxState updateUserChatInboxState;

    public ChatPresenterImpl(Resources res, UseCaseHandler handler, GetUser useCaseGetMainUser, GetUser useCaseGetUserReceptor, SendMessage useCaseSendMessage, GetChatMessages useCaseGetChatMessages, UpdateUserChatInboxState updateUserChatInboxState) {
        this.res = res;
        this.handler = handler;
        this.useCaseGetMainUser = useCaseGetMainUser;
        this.useCaseGetUserReceptor = useCaseGetUserReceptor;
        this.useCaseSendMessage = useCaseSendMessage;
        this.useCaseGetChatMessages = useCaseGetChatMessages;
        this.updateUserChatInboxState = updateUserChatInboxState;
    }

    @Override
    public void attachView(ChatView view) {
        Log.d(TAG, "attachView");
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    private User mainUser;
    private User receptor;

    private void getMainUser() {
        Log.d(TAG, "getMainUser");
        if (mainUserId == null) return;
        handler.execute(
                useCaseGetMainUser,
                new GetUser.RequestValues(mainUserId),
                new UseCase.UseCaseCallback<GetUser.ResponseValue>() {
                    @Override
                    public void onSuccess(GetUser.ResponseValue response) {
                        Log.d(TAG, "getMainUser onSuccess");
                        mainUser = response.getUser();
                        getMessages(false);
                        updateUserChatInboxState();
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "getMainUser onError");
                        showError("Error Getting User!");
                    }
                }
        );
    }

    private void getMessages(boolean stop) {
        Log.d(TAG, "getMessages");
        if (mainUser == null || receptor == null) return;

        handler.execute(
                useCaseGetChatMessages,
                new GetChatMessages.RequestValues(mainUser, receptor, stop),
                new UseCase.UseCaseCallback<GetChatMessages.ResponseValue>() {
                    @Override
                    public void onSuccess(GetChatMessages.ResponseValue response) {
                        Log.d(TAG, "getMessages onSuccess");
                        addMessage(response.getMessage());
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "getMessages onError");
                        showError("Error getting messages!");
                    }
                }
        );
    }

    private void updateUserChatInboxState() {
        Log.d(TAG, "updaterUserChatInboxState");
        if (mainUser == null || receptor == null) return;
        handler.execute(updateUserChatInboxState,
                new UpdateUserChatInboxState.RequestValues(mainUser, receptor, false),
                null);
    }

    private void addMessage(Message message) {
        Log.d(TAG, "addMessage");
        if (view != null) {
            view.addMessage(message);
        }
    }

    private void showError(String error) {
    }

    private void getReceptor() {
        Log.d(TAG, "getReceptor");
        if (receptorId == null) return;
        handler.execute(
                useCaseGetUserReceptor,
                new GetUser.RequestValues(receptorId),
                new UseCase.UseCaseCallback<GetUser.ResponseValue>() {
                    @Override
                    public void onSuccess(GetUser.ResponseValue response) {
                        Log.d(TAG, "getReceptor onSuccess: " + response.getUser().toString());
                        receptor = response.getUser();
                        String receptorName = receptor.getReadableName(res);
                        showReceptorName(receptorName);
                        getMessages(false);
                        updateUserChatInboxState();
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "getReceptor onSuccess");
                        showError("Error Getting User!");
                    }
                }
        );
    }

    private void showReceptorName(String name) {
        if (view != null && !TextUtils.isEmpty(name)) {
            view.showName(name);
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        getMainUser();
        getReceptor();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        getMessages(true);
        view = null;
    }

    String mainUserId;
    String receptorId;

    @Override
    public void setExtras(String mainUserId, String receptorId) {
        Log.d(TAG, "setExtras");
        this.mainUserId = mainUserId;
        this.receptorId = receptorId;
        Log.d(TAG, "mainUserId: " + mainUserId);
        Log.d(TAG, "receptorId: " + receptorId);
    }

    @Override
    public void onMessageSubmit(String content) {
        if (TextUtils.isEmpty(content)) return;

        Message message = new Message();
        message.setMessageText(content);
        message.setUser(mainUser);
        message.setContentType("text");
        message.setMessageStatus(100);//SENDED
        message.setTimestamp(new Date().getTime());

        hideSoftboard();
        cleanEdt();

        sendMessage(message);
    }

    private void cleanEdt() {
        if (view != null) {
            view.cleanEdtMessage();
        }

    }

    private void hideSoftboard() {
        if (view != null) {
            view.hideSoftboard();
        }
    }

    private void sendMessage(Message message) {
        Log.d(TAG, "sendMessage");
        if (mainUser == null) return;
        handler.execute(
                useCaseSendMessage,
                new SendMessage.RequestValues(message, mainUser, receptor),
                new UseCase.UseCaseCallback<SendMessage.ResponseValue>() {
                    @Override
                    public void onSuccess(SendMessage.ResponseValue response) {
                        addMessage(response.getMessage());
                    }

                    @Override
                    public void onError() {
                        showError("Error sending message");
                    }
                }
        );
    }
}
