package apps.steve.fire.randomchat.intro.usecase;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class UpdateUser extends UseCase<UpdateUser.RequestValues, UpdateUser.ResponseValue> {

    private UserRepository repository;

    public UpdateUser(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
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
