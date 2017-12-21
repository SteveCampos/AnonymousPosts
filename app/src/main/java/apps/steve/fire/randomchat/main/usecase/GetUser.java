package apps.steve.fire.randomchat.main.usecase;

import android.util.Log;

import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public class GetUser extends UseCase<GetUser.RequestValues, GetUser.ResponseValue> {
    private static final String TAG = GetUser.class.getSimpleName();
    private UserRepository repository;

    public GetUser(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        repository.getUser(requestValues.getUserId(), new UserDataSource.Callback<User>() {
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
        private final String userId;

        public RequestValues(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
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
