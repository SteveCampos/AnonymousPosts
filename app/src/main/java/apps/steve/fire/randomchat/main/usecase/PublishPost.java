package apps.steve.fire.randomchat.main.usecase;

import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.main.ui.entity.Post;

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
        //repository.publishPost(requestValues.getPost());
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
