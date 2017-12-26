package apps.stevecampos.fire.anonymouschat.posts.usecase;

import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.data.source.UserDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;

/**
 * Created by @stevecampos on 20/12/2017.
 */

public class GetPostWithTag extends UseCase<GetPostWithTag.RequestValues, GetPostWithTag.ResponseValue> {

    private static final String TAG = GetPostWithTag.class.getSimpleName();
    private UserRepository repository;

    public GetPostWithTag(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        Log.d(TAG, "executeUseCase");
        repository.getPostWithTag(requestValues.getTag(), new UserDataSource.Callback<Post>() {
            @Override
            public void onSucess(Post post) {
                if (post != null) {
                    getUseCaseCallback().onSuccess(new ResponseValue(post));
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String tag;

        public RequestValues(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
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
