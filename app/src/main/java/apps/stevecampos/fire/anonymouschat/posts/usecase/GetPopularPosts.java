package apps.stevecampos.fire.anonymouschat.posts.usecase;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;

/**
 * Created by Steve on 3/12/2017.
 */

public class GetPopularPosts extends UseCase<GetPopularPosts.RequestValues, GetPopularPosts.ResponseValue> {

    private UserRepository repository;

    public GetPopularPosts(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        repository.getPopularPosts(new UserDataSource.Callback<Post>() {
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
