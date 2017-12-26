package apps.stevecampos.fire.anonymouschat.posts.usecase;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 3/12/2017.
 */

public class GetRecentPosts extends UseCase<GetRecentPosts.RequestValues, GetRecentPosts.ResponseValue> {
    private UserRepository repository;

    public GetRecentPosts(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        repository.getRecentPosts(new UserDataSource.Callback<Post>() {
            @Override
            public void onSucess(Post post) {
                if (post != null) {
                    getUseCaseCallback().onSuccess(new ResponseValue(post));
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

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
