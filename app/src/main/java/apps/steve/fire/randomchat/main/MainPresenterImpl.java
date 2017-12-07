package apps.steve.fire.randomchat.main;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Item;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import apps.steve.fire.randomchat.main.usecase.GetPopularPosts;
import apps.steve.fire.randomchat.main.usecase.GetUser;
import apps.steve.fire.randomchat.main.usecase.PublishPost;

import static apps.steve.fire.randomchat.main.ui.entity.Item.*;

/**
 * Created by Steve on 25/11/2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private Resources res;
    private UseCaseHandler handler;
    private PublishPost useCasePublishPost;
    private FirebaseUser firebaseUser;
    private GetPopularPosts useCaseGetPopularPosts;
    private GetUser useCaseGetUser;

    private MainView view;

    public MainPresenterImpl(FirebaseUser firebaseUser, Resources res, UseCaseHandler handler, PublishPost publishPost, GetPopularPosts popularPosts, GetUser useCaseGetUser) {
        this.firebaseUser = firebaseUser;
        this.res = res;
        this.handler = handler;
        this.useCasePublishPost = publishPost;
        this.useCaseGetPopularPosts = popularPosts;
        this.useCaseGetUser = useCaseGetUser;
    }

    @Override
    public void attachView(MainView view) {
        Log.d(TAG, "attachView");
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        if (!isUserAuth()) {
            return;
        }
        getPopularPosts();
        getUser();
    }

    private User currentUser;

    private void getUser() {
        handler.execute(
                useCaseGetUser,
                new GetUser.RequestValues(firebaseUser.getUid()),
                new UseCase.UseCaseCallback<GetUser.ResponseValue>() {
                    @Override
                    public void onSuccess(GetUser.ResponseValue response) {
                        currentUser = response.getUser();
                        if (currentUser != null) {
                            showName(currentUser.getReadableName(res));
                            showAvatarDrawable(currentUser.getAvatarDrawable());
                        }
                    }

                    @Override
                    public void onError() {
                        showError("Error Getting User!");
                    }
                }
        );
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
    public void onBurgerIconClicked() {
        Log.d(TAG, "onBurgerIconClicked");
        openNav();
    }

    private void getPopularPosts() {
        handler.execute(
                useCaseGetPopularPosts,
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

    private boolean isUserAuth() {
        if (firebaseUser == null) {
            view.startIntro();
            return false;
        }
        return true;
    }

    private void showAvatarDrawable(@DrawableRes int avatarDrawable) {
        if (view != null) {
            view.showAvatar(avatarDrawable);
        }
    }

    private void showName(String name) {
        if (view != null) {
            view.showName(name);
        }
    }

    private boolean isNavOpen;

    private void openNav() {
        if (view != null) {
            view.openNavigation();
        }
        isNavOpen = true;
    }

    private void closeNav() {
        if (view != null) {
            view.closeNavigation();
        }
        isNavOpen = false;
    }

    private boolean kingPostSelected;

    @Override
    public void onSubmitPost(String content, List<String> tagList) {
        Log.d(TAG, "onSubmitPost");
        if (TextUtils.isEmpty(content)) {
            return;
        }
        Post post = new Post();
        post.setContentText(content);
        post.setHashtags(tagList);
        post.setTimestamp(new Date().getTime());
        post.setPopular(kingPostSelected);

        hidePublishDialog();
        publishPost(post);
    }

    private void publishPost(Post post) {
        Log.d(TAG, "publishPost");
        handler.execute(
                useCasePublishPost,
                new PublishPost.RequestValues(post),
                new UseCase.UseCaseCallback<PublishPost.ResponseValue>() {
                    @Override
                    public void onSuccess(PublishPost.ResponseValue response) {
                        addPost(response.getPost());
                    }

                    @Override
                    public void onError() {
                        String error = res.getString(R.string.error_publish);
                        showError(error);
                    }
                }
        );
    }

    private void showError(String error) {
        if (view != null) {
            view.showError(error);
        }
    }

    private void addPost(Post post) {
        if (view != null) {
            view.addPost(post);
        }
    }

    private boolean isFabExtrasVisible;
    private boolean isPublishDialogVisible;

    @Override
    public void onFabClicked() {
        if (!isFabExtrasVisible) {
            showFabs();
        } else {
            hideFabs();
        }
    }

    private void showFabs() {
        if (view != null) {
            view.showFabExtras();
        }
        isFabExtrasVisible = true;
    }

    private void hideFabs() {
        if (view != null) {
            view.hideFabExtras();
        }
        isFabExtrasVisible = false;
    }

    private void showPublishDialog() {
        if (view != null) {
            view.showPublishDialog();
        }
        isPublishDialogVisible = true;
    }

    private void hidePublishDialog() {
        if (view != null) {
            view.hidePublishDialog();
        }
        isPublishDialogVisible = false;
    }


    @Override
    public void onBackPressed() {
        if (isNavOpen) {
            closeNav();
            return;
        }


        if (isPublishDialogVisible) {
            hidePublishDialog();
            return;
        }
        if (popBackStackIfNeeded()) {
            return;
        }
        view.close();
    }

    private boolean popBackStackIfNeeded() {
        int backStackCount = view.getBackStackEntryCount();
        Log.d(TAG, "backStackCount: " + backStackCount);
        if (backStackCount > 1) {
            if (backStackCount == 2) {
                showFab();
            }
            view.popBackStack();
            return true;
        }
        return false;
    }

    private void showFab() {
        if (view != null) {
            view.showFab();
        }
    }


    private Item itemSelected;

    @Override
    public void onMenuItemSelected(Item item) {
        toogleItems(itemSelected, item);
        switch (item.getId()) {
            case MENU_POSTS:
                int backStackCount = view.getBackStackEntryCount();
                if (backStackCount > 1) {
                    for (int i = 0; i < backStackCount - 1; i++) {
                        view.popBackStack();
                        if (i == (backStackCount - 2)) {
                            showFab();
                        }
                    }
                }
                break;
            case MENU_PROFILE:
                showProfile(currentUser, true);
                break;
            case MENU_USERS:
                break;
            case MENU_MESSAGES:
                showMessages();
                break;
            case MENU_APPINFO:
                showAppInfo();
                break;
            case MENU_LOGOUT:
                logout();
                break;
        }
        closeNav();
    }

    private void showPostPager() {
        if (view != null) {
            view.showPostPager();
        }
    }

    private void showMessages() {
        if (view != null) {
            view.showMessages();
        }
    }

    private void showAppInfo() {
        if (view != null) {
            view.showAppInfo();
        }
    }

    private void logout() {
        if (view != null) {
            view.logout();
            view.startIntro();
        }
    }

    private void startChat(String userId, String receptorId) {
        if (view != null) {
            view.startChat(userId, receptorId);
        }
    }

    @Override
    public void onFabProClicked() {
        kingPostSelected = true;
        hideFabs();
        tooglePostDialog();
    }

    private void tooglePostDialog() {
        if (!isPublishDialogVisible) {
            showPublishDialog();
        } else {
            hidePublishDialog();
        }
    }

    @Override
    public void onFabRegularClicked() {
        kingPostSelected = false;
        hideFabs();
        tooglePostDialog();
    }

    @Override
    public void onPostSelected(Post post) {
        changeTitle(post.getUser().getReadableName(res));
        hideFab();
        showPostDetail(post);
    }


    @Override
    public void onUserSelected(User user) {
        changeTitle(res.getString(R.string.fragment_profile_title));
        showProfile(user, false);
    }

    @Override
    public void onCommentSelected(Comment comment) {

    }

    @Override
    public void onSendMessageClicked(User user) {
        startChat(currentUser.getId(), user.getId());
    }

    private void hideFab() {
        if (view != null) {
            view.hideFab();
        }
    }

    private void changeTitle(String title) {
        if (view != null) {
            view.changeTitle(title);
        }
    }

    private void showProfile(User user, boolean editable) {
        if (view != null) {
            view.showProfile(user, editable);
        }
    }

    private void showPostDetail(Post post) {
        if (view != null) {
            view.showPostDetail(post);
        }
    }

    private void toogleItems(Item old, Item selected) {
        if (old != null) old.setSelected(false);
        selected.setSelected(true);
        if (view != null) {
            view.toogleMenuItems(old, selected);
        }
        itemSelected = selected;
    }
}
