package apps.steve.fire.randomchat.postDetail;

import android.support.annotation.DrawableRes;
import android.util.Log;

import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import apps.steve.fire.randomchat.main.usecase.GetPostComments;
import apps.steve.fire.randomchat.main.usecase.PublishComment;

/**
 * Created by @stevecampos on 5/12/2017.
 */

public class PostDetailPresenterImpl implements PostDetailPresenter {
    public static final String TAG = PostDetailPresenterImpl.class.getSimpleName();

    PostDetailView view;
    private Post post;
    private UseCaseHandler handler;
    private GetPostComments useCaseGetPostComments;
    private PublishComment useCasePublishComment;

    public PostDetailPresenterImpl(UseCaseHandler handler, GetPostComments useCaseGetPostComments, PublishComment useCasePublishComment) {
        this.handler = handler;
        this.useCaseGetPostComments = useCaseGetPostComments;
        this.useCasePublishComment = useCasePublishComment;
    }

    @Override
    public void attachView(PostDetailView view) {
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
    }

    @Override
    public void onAttach() {
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreateView() {
        Log.d(TAG, "onCreateView");
    }

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
        stopCommentsListener();
    }

    @Override
    public void setPost(Post post) {
        Log.d(TAG, "onCreate");
        this.post = post;
        paintPost();
        getPostComments();
    }

    @Override
    public void publishCommentBtnClicked(String commentText) {
        if (post == null || post.getUser() == null) return;
        if (view != null) {
            view.showEdtContent("");
            view.hideSoftboard();
        }

        Comment comment = new Comment();
        comment.setPostId(post.getId());
        comment.setUser(post.getUser());
        comment.setCommentText(commentText);


        //showProgress();

        handler.execute(
                useCasePublishComment,
                new PublishComment.RequestValues(comment),
                new UseCase.UseCaseCallback<PublishComment.ResponseValue>() {
                    @Override
                    public void onSuccess(PublishComment.ResponseValue response) {
                        Comment commentResponse = response.getComment();
                        if (commentResponse != null) {
                            Log.d(TAG, "publishComment: " + commentResponse);
                            addComment(commentResponse);
                        }
                    }

                    @Override
                    public void onError() {
                        showError("An error ocurred publishing comment!");
                    }
                }
        );
    }

    private void showProgress() {
        if (view != null) {
            view.showProgress();
        }
    }

    private void showError(String error) {
        if (view != null) {
            view.hideProgress();
            view.showError(error);
        }
    }


    private void paintPost() {
        User user = post.getUser();
        @DrawableRes int avatarDrawable = 0;
        if (user != null) {
            avatarDrawable = user.getAvatarDrawable();
        }
        String contentText = post.getContentText();
        long commentCount = post.getCommentCount();
        long favCount = post.getFavoriteCount();
        boolean kingPost = post.isPopular();

        if (view != null) {
            view.showUserAvatarDrawable(avatarDrawable);
            view.showContentText(contentText);
            view.showCommentCount(commentCount);
            view.showFavCount(favCount);
            if (!kingPost) {
                view.hideCrown();
            }
        }
    }

}
