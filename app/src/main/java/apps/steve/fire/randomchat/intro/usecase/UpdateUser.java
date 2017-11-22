package apps.steve.fire.randomchat.intro.usecase;

import com.google.firebase.auth.FirebaseUser;

import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;

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
        repository.updateUser(requestValues.getUser(), requestValues.getAvatar(), requestValues.getGender(), new UserDataSource.Callback<Boolean>() {
            @Override
            public void onSucess(Boolean success) {
                getUseCaseCallback().onSuccess(new ResponseValue(success));
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final AvatarUi avatar;
        private final String gender;
        private final FirebaseUser user;

        public RequestValues(AvatarUi avatar, String gender, FirebaseUser user) {
            this.avatar = avatar;
            this.gender = gender;
            this.user = user;
        }

        public AvatarUi getAvatar() {
            return avatar;
        }

        String getGender() {
            return gender;
        }

        FirebaseUser getUser() {
            return user;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final boolean sucess;

        ResponseValue(boolean sucess) {
            this.sucess = sucess;
        }

        public boolean isSucess() {
            return sucess;
        }
    }
}
