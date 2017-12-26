package apps.stevecampos.fire.anonymouschat.main.usecase;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;

/**
 * Created by Steve on 26/11/2017.
 */

public class PublishPost extends UseCase<PublishPost.RequestValues, PublishPost.ResponseValue> {

    private UserRepository repository;

    public PublishPost(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        repository.publishPost(requestValues.getPost(), new UserDataSource.Callback<Post>() {
            @Override
            public void onSucess(Post post) {
                if (post != null) {
                    getUseCaseCallback().onSuccess(new ResponseValue(post));
                } else {
                    getUseCaseCallback().onError();
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final Post post;

        public RequestValues(Post post) {
            this.post = post;
        }

        public Post getPost() {
            return post;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final Post post;

        public ResponseValue(Post post) {
            this.post = post;
        }

        public Post getPost() {
            return post;
        }
    }
}
