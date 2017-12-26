package apps.stevecampos.fire.anonymouschat.main.usecase;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/12/2017.
 */

public class UpdateUserChatInboxState extends UseCase<UpdateUserChatInboxState.RequestValues, UpdateUserChatInboxState.ResponseValue> {
    public static final String TAG = UpdateUserChatInboxState.class.getSimpleName();
    private UserRepository repository;

    public UpdateUserChatInboxState(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        repository.updateUserChatInboxState(requestValues.getUser(),
                requestValues.getReceiver(),
                requestValues.isState());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final User user;
        private final User receiver;
        private final boolean state;

        public RequestValues(User user, User receiver, boolean state) {
            this.user = user;
            this.receiver = receiver;
            this.state = state;
        }

        public User getUser() {
            return user;
        }

        public User getReceiver() {
            return receiver;
        }

        public boolean isState() {
            return state;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
    }

}
