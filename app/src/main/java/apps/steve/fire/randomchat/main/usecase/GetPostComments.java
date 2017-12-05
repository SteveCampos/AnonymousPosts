package apps.steve.fire.randomchat.main.usecase;

import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.data.source.UserDataSource;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public class GetPostComments extends UseCase<GetPostComments.RequestValues, GetPostComments.ResponseValue> {
    private UserRepository repository;

    public GetPostComments(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        boolean stopListener = requestValues.isStopListener();
        Post post = requestValues.getPost();
        if (stopListener) {
            repository.removePostCommentsListener(post);
            return;
        }

        repository.getPostComments(
                post,
                new UserDataSource.Callback<Comment>() {
                    @Override
                    public void onSucess(Comment comment) {
                        if (comment != null) {
                            getUseCaseCallback().onSuccess(new ResponseValue(comment));
                        }
                    }
                });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Post post;
        private boolean stopListener;

        public RequestValues(Post post) {
            this.post = post;
        }

        public RequestValues(Post post, boolean stopListener) {
            this.post = post;
            this.stopListener = stopListener;
        }

        public Post getPost() {
            return post;
        }

        public boolean isStopListener() {
            return stopListener;
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
