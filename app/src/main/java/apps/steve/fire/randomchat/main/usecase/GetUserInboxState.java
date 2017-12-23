package apps.steve.fire.randomchat.main.usecase;

import android.util.Log;

import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 22/12/2017.
 */

public class GetUserInboxState extends UseCase<GetUserInboxState.RequestValues, GetUserInboxState.ResponseValue> {
    public static final String TAG = GetUserInboxState.class.getSimpleName();
    private UserRepository repository;

    public GetUserInboxState(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        boolean listen = requestValues.isListen();
        User user = requestValues.getUser();
        if (!listen) {
            repository.removeUserInboxStateListener(user);
            return;
        }
        repository.listenUserInboxState(
                user,
                new UserDataSource.Callback<Boolean>() {
                    @Override
                    public void onSucess(Boolean state) {
                        if (state != null) {
                            getUseCaseCallback().onSuccess(new GetUserInboxState.ResponseValue(state));
                        } else {
                            getUseCaseCallback().onError();
                        }
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final User user;
        private final boolean listen;


        public RequestValues(User user, boolean listen) {
            this.user = user;
            this.listen = listen;
        }

        public User getUser() {
            return user;
        }

        public boolean isListen() {
            return listen;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final boolean incommingState;

        public ResponseValue(boolean incommingState) {
            this.incommingState = incommingState;
        }

        public boolean isIncommingState() {
            return incommingState;
        }
    }
}
