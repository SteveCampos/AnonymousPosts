package apps.steve.fire.randomchat.main;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.List;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.base.usecase.UseCase;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.main.ui.entity.Item;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.usecase.GetPopularPosts;
import apps.steve.fire.randomchat.main.usecase.PublishPost;

/**
 * Created by Steve on 25/11/2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private Resources res;
    private UseCaseHandler handler;
    private PublishPost useCasePublishPost;
    private FirebaseUser currentUser;
    private GetPopularPosts useCaseGetPopularPosts;

    private MainView view;

    public MainPresenterImpl(FirebaseUser currentUser, Resources res, UseCaseHandler handler, PublishPost publishPost, GetPopularPosts popularPosts) {
        this.currentUser = currentUser;
        this.res = res;
        this.handler = handler;
        this.useCasePublishPost = publishPost;
        this.useCaseGetPopularPosts = popularPosts;
    }

    @Override
    public void attachView(MainView view) {
        Log.d(TAG, "attachView");
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        getPopularPosts();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        showName();
        checkCurrentUser();
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

    private void checkCurrentUser() {
        if (currentUser == null) {
            view.startIntro();
        }
    }

    private void showName() {
        if (view != null && currentUser != null) {
            view.showName(currentUser.getDisplayName());
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
        //addPost(post);
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
        if (isPublishDialogVisible) {
            hidePublishDialog();
            return;
        }
        int backStackCount = view.getBackStackEntryCount();
        Log.d(TAG, "backStackCount: " + backStackCount);
        if (backStackCount > 1) {
            view.popBackStack();
            return;
        }
        view.close();
    }

    private static final String MENU_LOGOUT = "Cerrar Sesión";
    private static final String MENU_APPINFO = "App Info";
    private static final String MENU_MESSAGES = "Mensajes";
    private static final String MENU_USERS = "Usuarios";
    private static final String MENU_POSTS = "Publicaciones";
    private static final String MENU_PROFILE = "Mi perfil";
    private Item itemSelected;

    @Override
    public void onMenuItemSelected(Item item) {
        toogleItems(itemSelected, item);
        closeNav();
        switch (item.getName()) {
            case MENU_LOGOUT:
                logout();
                break;
        }
    }

    private void logout() {
        if (view != null) {
            view.logout();
            view.startIntro();
        }
    }

    private void startChat() {
        if (view != null) {
            view.startChat();
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

    private void toogleItems(Item old, Item selected) {
        if (old != null) old.setSelected(false);
        selected.setSelected(true);
        if (view != null) {
            view.toogleMenuItems(old, selected);
        }
        itemSelected = selected;
    }
}
