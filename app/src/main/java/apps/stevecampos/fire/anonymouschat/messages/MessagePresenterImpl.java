package apps.stevecampos.fire.anonymouschat.messages;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseHandler;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;
import apps.stevecampos.fire.anonymouschat.messages.usecase.GetInboxMessages;

/**
 * Created by @stevecampos on 19/12/2017.
 */

public class MessagePresenterImpl implements MessagePresenter {

    private static final String TAG = MessagePresenterImpl.class.getSimpleName();
    private MessageView view;
    private UseCaseHandler handler;
    private GetInboxMessages getInboxMessages;

    public MessagePresenterImpl(UseCaseHandler handler, GetInboxMessages getInboxMessages) {
        this.handler = handler;
        this.getInboxMessages = getInboxMessages;
    }

    @Override
    public void attachView(MessageView view) {
        Log.d(TAG, "attachView");
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
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
    }

    @Override
    public void onAttach() {
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreateView() {
        Log.d(TAG, "onCreateView");
    }

    @Override
    public void onViewCreated() {
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        view = null;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        stopMessagesListener();
    }

    private void showProgress() {
        if (view != null) {
            view.showProgress();
        }
    }

    private void hideProgress() {
        if (view != null) {
            view.hideProgress();
        }
    }

    private User user;

    @Override
    public void setUser(User user) {
        Log.d(TAG, "setUser");
        this.user = user;
        getUserMessages();
    }

    private void getUserMessages() {
        getMessages(false);
    }

    private void getMessages(boolean stop) {
        Log.d(TAG, "getMessages");
        if (user == null) return;
        handler.execute(
                getInboxMessages,
                new GetInboxMessages.RequestValues(user, stop),
                new UseCase.UseCaseCallback<GetInboxMessages.ResponseValue>() {
                    @Override
                    public void onSuccess(GetInboxMessages.ResponseValue response) {
                        Log.d(TAG, "getMessages onSuccess");
                        Message message = response.getMessage();
                        if (message != null) {
                            addMessage(message);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    private void stopMessagesListener() {
        getMessages(true);
    }


    private void addMessage(Message message) {
        Log.d(TAG, "addMessage");
        if (view != null) {
            view.hideProgress();
            view.addMessage(message);
        }
    }
}
