package apps.stevecampos.fire.anonymouschat.intro.usecase;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UpdateUser extends UseCase<UpdateUser.RequestValues, UpdateUser.ResponseValue> {

    private static final String TAG = UpdateUser.class.getSimpleName();
    private UserRepository repository;

    public UpdateUser(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        repository.updateUser(requestValues.getUser(), new UserDataSource.Callback<User>() {
            @Override
            public void onSucess(User user) {
                if (user != null) {
                    getUseCaseCallback().onSuccess(new ResponseValue(user));
                } else {
                    getUseCaseCallback().onError();
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final User user;

        public RequestValues(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final User user;

        public ResponseValue(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}
