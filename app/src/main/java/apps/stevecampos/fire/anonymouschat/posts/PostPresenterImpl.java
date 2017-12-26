package apps.stevecampos.fire.anonymouschat.posts;

import android.os.Bundle;
import android.util.Log;

import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseHandler;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;
import apps.stevecampos.fire.anonymouschat.posts.usecase.GetPopularPosts;
import apps.stevecampos.fire.anonymouschat.posts.usecase.GetPostWithTag;
import apps.stevecampos.fire.anonymouschat.posts.usecase.GetRecentPosts;

import static apps.stevecampos.fire.anonymouschat.posts.PostsFragment.EXTRA_TAG;
import static apps.stevecampos.fire.anonymouschat.posts.PostsFragment.EXTRA_TYPE;
import static apps.stevecampos.fire.anonymouschat.posts.PostsFragment.TYPE_HASHTAG;
import static apps.stevecampos.fire.anonymouschat.posts.PostsFragment.TYPE_POPULAR;
import static apps.stevecampos.fire.anonymouschat.posts.PostsFragment.TYPE_RECENTS;

/**
 * Created by @stevecampos on 20/12/2017.
 */

public class PostPresenterImpl implements PostPresenter {
    private static final String TAG = PostPresenter.class.getSimpleName();
    PostView view;
    private Post post;
    private UseCaseHandler handler;
    private GetPopularPosts getPopularPosts;
    private GetRecentPosts getRecentPosts;
    private GetPostWithTag getPostWithTag;

    public PostPresenterImpl(UseCaseHandler handler, GetPopularPosts getPopularPosts, GetRecentPosts getRecentPosts, GetPostWithTag getPostWithTag) {
        this.handler = handler;
        this.getPopularPosts = getPopularPosts;
        this.getRecentPosts = getRecentPosts;
        this.getPostWithTag = getPostWithTag;
    }

    @Override
    public void attachView(PostView view) {
        Log.d(TAG, "attachView");
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        view = null;
    }

    @Override
    public void onAttach() {
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreateView() {
        Log.d(TAG, "onCreateView");
    }

    @Override
    public void onViewCreated() {
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
    }


    private void showProgress() {
        if (view != null) {
            view.showProgress();
        }
    }

    private void hideProgress() {
        if (view != null) {
            view.hideProgress();
        }
    }

    private int type = TYPE_RECENTS;
    private String tag;

    @Override
    public void setExtras(Bundle arguments) {
        if (arguments != null) {
            type = arguments.getInt(EXTRA_TYPE);
            tag = arguments.getString(EXTRA_TAG);
        }
        getPosts();
    }

    private void getPosts() {
        Log.d(TAG, "getPosts type: " + type);
        switch (type) {
            case TYPE_RECENTS:
                getRecentPosts();
                break;
            case TYPE_POPULAR:
                getPopularPosts();
                break;
            case TYPE_HASHTAG:
                getPostWithTag(tag);
                break;
        }
    }

    private void getPopularPosts() {
        handler.execute(
                getPopularPosts,
                new GetPopularPosts.RequestValues(),
                new UseCase.UseCaseCallback<GetPopularPosts.ResponseValue>() {
                    @Override
                    public void onSuccess(GetPopularPosts.ResponseValue response) {
                        Post post = response.getPost();
                        if (post != null) {
                            addPost(post);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    private void getRecentPosts() {
        handler.execute(
                getRecentPosts,
                new GetRecentPosts.RequestValues(),
                new UseCase.UseCaseCallback<GetRecentPosts.ResponseValue>() {
                    @Override
                    public void onSuccess(GetRecentPosts.ResponseValue response) {
                        Post post = response.getPost();
                        if (post != null) {
                            addPost(post);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    private void getPostWithTag(String tag) {
        Log.d(TAG, "getPostWithTag");
        if (tag == null) return;
        handler.execute(
                getPostWithTag,
                new GetPostWithTag.RequestValues(tag),
                new UseCase.UseCaseCallback<GetPostWithTag.ResponseValue>() {
                    @Override
                    public void onSuccess(GetPostWithTag.ResponseValue response) {
                        Post post = response.getPost();
                        if (post != null) {
                            addPost(post);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }


    private void addPost(Post post) {
        if (view != null) {
            hideEmptyView();
            hideProgress();
            view.addPost(post);
        }
    }

    private void hideEmptyView() {
        if (view != null) {
            view.hideEmptyView();
        }
    }
    /*
    private void getPostComments() {
        getComments(false);
    }

    private void getComments(boolean stop) {
        if (post == null) return;
        handler.execute(
                useCaseGetPostComments,
                new GetPostComments.RequestValues(post, stop),
                new UseCase.UseCaseCallback<GetPostComments.ResponseValue>() {
                    @Override
                    public void onSuccess(GetPostComments.ResponseValue response) {
                        Comment comment = response.getComment();
                        if (comment != null) {
                            addComment(comment);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    private void stopCommentsListener() {
        getComments(true);
    }


    private void addComment(Comment comment) {
        if (view != null) {
            view.hideProgress();
            view.addComment(comment);
        }
    }*/
}
