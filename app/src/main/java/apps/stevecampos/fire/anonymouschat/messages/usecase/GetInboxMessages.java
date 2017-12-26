package apps.stevecampos.fire.anonymouschat.messages.usecase;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 19/12/2017.
 */

public class GetInboxMessages extends UseCase<GetInboxMessages.RequestValues, GetInboxMessages.ResponseValue> {
    private static final String TAG = GetInboxMessages.class.getSimpleName();
    private UserRepository repository;

    public GetInboxMessages(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        boolean stopListener = requestValues.isStopListener();
        User user = requestValues.getUser();
        if (stopListener) {
            repository.removeInboxMessageListener(user);
            return;
        }

        repository.getMessagesFromInbox(user, new UserDataSource.Callback<Message>() {
            @Override
            public void onSucess(Message message) {
                if (message != null) {
                    getUseCaseCallback().onSuccess(new ResponseValue(message));
                }
            }
        });
    }


    public static final class RequestValues implements UseCase.RequestValues {
        private User user;
        private boolean stopListener;

        public RequestValues(User user, boolean stopListener) {
            this.user = user;
            this.stopListener = stopListener;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public boolean isStopListener() {
            return stopListener;
        }

        public void setStopListener(boolean stopListener) {
            this.stopListener = stopListener;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final Message message;

        public ResponseValue(Message message) {
            this.message = message;
        }

        public Message getMessage() {
            return message;
        }
    }
}
