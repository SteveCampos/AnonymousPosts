package apps.stevecampos.fire.anonymouschat.chat.usecase;

import apps.stevecampos.fire.anonymouschat.Utils;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;

/**
 * Created by Steve on 16/12/2017.
 */

public class GetChatMessages extends UseCase<GetChatMessages.RequestValues, GetChatMessages.ResponseValue> {
    private UserRepository repository;

    public GetChatMessages(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        final User mainUser = requestValues.getMainUser();
        final User receptor = requestValues.getReceptor();
        final String mainUserId = mainUser.getId();
        String receptorId = receptor.getId();

        boolean stop = requestValues.isStopListener();
        String chatId = Utils.getId(mainUserId, receptorId);
        if (stop) {
            repository.removeMessagesListener(chatId);
        } else {
            repository.getMessages(chatId, new UserDataSource.Callback<Message>() {
                @Override
                public void onSucess(Message message) {
                    if (message == null) return;

                    String messageUserId = message.getUser().getId();
                    if (messageUserId.equals(mainUserId)) {
                        message.setUser(mainUser);
                        message.setMainUser(true);
                    } else {
                        message.setUser(receptor);
                    }

                    getUseCaseCallback().onSuccess(new ResponseValue(message));
                }
            });
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private User mainUser;
        private User receptor;
        private boolean stopListener;

        public RequestValues(User mainUser, User receptor, boolean stopListener) {
            this.mainUser = mainUser;
            this.receptor = receptor;
            this.stopListener = stopListener;
        }

        public User getMainUser() {
            return mainUser;
        }

        public User getReceptor() {
            return receptor;
        }

        public boolean isStopListener() {
            return stopListener;
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
