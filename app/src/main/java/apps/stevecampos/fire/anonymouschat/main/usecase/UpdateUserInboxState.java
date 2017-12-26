package apps.stevecampos.fire.anonymouschat.main.usecase;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/12/2017.
 */

public class UpdateUserInboxState extends UseCase<UpdateUserInboxState.RequestValues, UpdateUserInboxState.ResponseValue> {

    public static final String TAG = UpdateUserInboxState.class.getSimpleName();
    private UserRepository repository;

    public UpdateUserInboxState(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        repository.updateUserInboxState(requestValues.getUser(),
                requestValues.isState());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final User user;
        private final boolean state;


        public RequestValues(User user, boolean state) {
            this.user = user;
            this.state = state;
        }

        public User getUser() {
            return user;
        }

        public boolean isState() {
            return state;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
    }

}
