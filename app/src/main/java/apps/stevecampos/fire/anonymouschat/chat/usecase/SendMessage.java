package apps.stevecampos.fire.anonymouschat.chat.usecase;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by Steve on 16/12/2017.
 */

public class SendMessage extends UseCase<SendMessage.RequestValues, SendMessage.ResponseValue> {

    private static final String TAG = SendMessage.class.getSimpleName();
    private UserRepository repository;

    public SendMessage(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(final RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        repository.sendMessage(requestValues.getMainUser(),
                requestValues.getReceptor(),
                requestValues.getMessage(),
                new UserDataSource.Callback<Message>() {
                    @Override
                    public void onSucess(Message message) {
                        if (message == null) return;
                        User user = requestValues.getMainUser();
                        message.setUser(user);
                        getUseCaseCallback().onSuccess(new ResponseValue(message));
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final Message message;
        private final User mainUser;
        private final User receptor;

        public RequestValues(Message message, User mainUser, User receptor) {
            this.message = message;
            this.mainUser = mainUser;
            this.receptor = receptor;
        }

        public Message getMessage() {
            return message;
        }

        public User getMainUser() {
            return mainUser;
        }

        public User getReceptor() {
            return receptor;
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
