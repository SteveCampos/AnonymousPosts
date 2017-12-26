package apps.stevecampos.fire.anonymouschat.main.usecase;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Comment;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public class PublishComment extends UseCase<PublishComment.RequestValues, PublishComment.ResponseValue> {

    private UserRepository repository;

    public PublishComment(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        repository.publishComment(requestValues.getComment(), new UserDataSource.Callback<Comment>() {
            @Override
            public void onSucess(Comment comment) {
                if (comment != null) {
                    getUseCaseCallback().onSuccess(new ResponseValue(comment));
                } else {
                    getUseCaseCallback().onError();
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final Comment comment;

        public RequestValues(Comment comment) {
            this.comment = comment;
        }

        public Comment getComment() {
            return comment;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final Comment comment;

        public ResponseValue(Comment comment) {
            this.comment = comment;
        }

        public Comment getComment() {
            return comment;
        }
    }
}
