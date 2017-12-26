package apps.stevecampos.fire.anonymouschat.main.usecase;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by @stevecampos on 21/12/2017.
 */

public class UpdateUserCoins extends UseCase<UpdateUserCoins.RequestValues, UpdateUserCoins.ResponseValue> {
    public static final String TAG = UpdateUserCoins.class.getSimpleName();
    private UserRepository repository;

    public UpdateUserCoins(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "updateUserCoins onSuccess");
        repository.updateUserCoins(requestValues.getUser(), requestValues.getCoins(), new UserDataSource.Callback<User>() {
            @Override
            public void onSucess(User user) {
                getUseCaseCallback().onSuccess(new ResponseValue(user));
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final User user;
        private final long coins;

        public RequestValues(User user, long coins) {
            this.user = user;
            this.coins = coins;
        }

        public User getUser() {
            return user;
        }

        public long getCoins() {
            return coins;
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
