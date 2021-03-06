package apps.stevecampos.fire.anonymouschat.main;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.ads.reward.RewardItem;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.Utils;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCase;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseHandler;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Comment;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Item;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Post;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;
import apps.stevecampos.fire.anonymouschat.main.usecase.GetUserInboxState;
import apps.stevecampos.fire.anonymouschat.main.usecase.UpdateUserCoins;
import apps.stevecampos.fire.anonymouschat.main.usecase.GetUser;
import apps.stevecampos.fire.anonymouschat.main.usecase.PublishPost;
import apps.stevecampos.fire.anonymouschat.main.usecase.UpdateUserInboxState;

import static apps.stevecampos.fire.anonymouschat.main.ui.entity.Item.*;

/**
 * Created by Steve on 25/11/2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private Resources res;
    private UseCaseHandler handler;
    private PublishPost useCasePublishPost;
    private FirebaseUser firebaseUser;
    private GetUser useCaseGetUser;
    private UpdateUserCoins updateUserCoins;
    private GetUserInboxState getUserInboxState;
    private UpdateUserInboxState updateUserInboxState;

    private MainView view;

    public MainPresenterImpl(FirebaseUser firebaseUser, Resources res, UseCaseHandler handler, PublishPost publishPost, GetUser useCaseGetUser, UpdateUserCoins updateUserCoins, GetUserInboxState getUserInboxState, UpdateUserInboxState updateUserInboxState) {
        this.firebaseUser = firebaseUser;
        this.res = res;
        this.handler = handler;
        this.useCasePublishPost = publishPost;
        this.useCaseGetUser = useCaseGetUser;
        this.updateUserCoins = updateUserCoins;
        this.getUserInboxState = getUserInboxState;
        this.updateUserInboxState = updateUserInboxState;
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
        //getPopularPosts();
        getUser();
        listenUserMessagesState(true);
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
                            showCoins();
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

    private void listenUserMessagesState(boolean listen) {
        Log.d(TAG, "listenUserMessagesState");
        if (firebaseUser == null) return;
        User user = new User();
        user.setId(firebaseUser.getUid());

        handler.execute(
                getUserInboxState,
                new GetUserInboxState.RequestValues(user, listen),
                new UseCase.UseCaseCallback<GetUserInboxState.ResponseValue>() {
                    @Override
                    public void onSuccess(GetUserInboxState.ResponseValue response) {
                        boolean state = response.isIncommingState();
                        Log.d(TAG, "listenUserInboxState onSuccess state: " + state);
                        if (state) showError(res.getString(R.string.global_mssg_newmessages));
                        updateMenuMessage(state);
                    }

                    @Override
                    public void onError() {
                        Log.d(TAG, "listenUserInboxState onError");

                    }
                }
        );
    }

    private void updateMenuMessage(boolean state) {
        if (view != null) {
            @DrawableRes int drawable = R.drawable.ic_chat_teal_24dp;
            String notificationText = "";
            if (state) {
                notificationText = res.getString(R.string.global_mssg_newmessages);
            }
            view.updateMenuItem(new Item(MENU_MESSAGES, drawable, res.getString(R.string.fragment_messages_title), false, notificationText));
        }
    }

    private void updateUserCoins(long coins) {
        Log.d(TAG, "updateUserCoins");
        if (currentUser == null) return;
        handler.execute(
                updateUserCoins,
                new UpdateUserCoins.RequestValues(currentUser, coins),
                new UseCase.UseCaseCallback<UpdateUserCoins.ResponseValue>() {
                    @Override
                    public void onSuccess(UpdateUserCoins.ResponseValue response) {
                        Log.d(TAG, "updateUserCoins onSuccess");
                        currentUser = response.getUser();
                        if (currentUser != null) {
                            showCoins();
                            showName(currentUser.getReadableName(res));
                            showAvatarDrawable(currentUser.getAvatarDrawable());
                        }
                    }

                    @Override
                    public void onError() {
                        showError("updateUserCoins Error Getting User!");
                    }
                }
        );
    }

    private void showCoins() {
        if (view != null) {
            view.showCoins(currentUser.getCoins());
        }
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
        listenUserMessagesState(false);
        view = null;
    }

    @Override
    public void onBurgerIconClicked() {
        Log.d(TAG, "onBurgerIconClicked");
        openNav();
    }

    /*
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
    }*/

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
        post.setHashtags(normalizeTags(tagList));
        post.setTimestamp(new Date().getTime());
        post.setPopular(kingPostSelected);
        post.setUser(currentUser);

        hidePublishDialog();
        publishPost(post);
    }

    private List<String> normalizeTags(List<String> tagList) {
        List<String> normalizedTags = new ArrayList<>();
        if (tagList != null && !tagList.isEmpty()) {
            for (String tag :
                    tagList) {
                String normalizedTag = Utils.normalizeTag(tag);
                if (!TextUtils.isEmpty(normalizedTag)) {
                    normalizedTags.add(normalizedTag);
                }
            }
        }
        return normalizedTags;
    }

    private void publishPost(final Post post) {
        Log.d(TAG, "publishPost");
        handler.execute(
                useCasePublishPost,
                new PublishPost.RequestValues(post),
                new UseCase.UseCaseCallback<PublishPost.ResponseValue>() {
                    @Override
                    public void onSuccess(PublishPost.ResponseValue response) {
                        addPost(response.getPost());
                        if (post.isPopular()) {
                            getUser();
                        }
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
        if (isPublishDialogVisible) return;

        if (!isFabExtrasVisible) {
            showFabs();
        } else {
            hideFabs();
        }
    }

    private void showFabs() {
        if (view != null) {
            view.showPostBtns();
        }
        isFabExtrasVisible = true;
    }

    private void hideFabs() {
        if (view != null) {
            view.hidePostBtns();
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
        hideFab();
        hideFabs();
        switch (item.getId()) {
            case MENU_POSTS:
                int backStackCount = view.getBackStackEntryCount();
                if (backStackCount > 1) {
                    for (int i = 0; i < backStackCount - 1; i++) {
                        view.popBackStack();
                    }
                }
                showFab();
                break;
            case MENU_PROFILE:
                showProfile(currentUser, true);
                break;
            case MENU_COINS:
                showCoinFragment();
                break;
            case MENU_USERS:
                break;
            case MENU_MESSAGES:
                updateUserInboxState();
                showMessages(currentUser);
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

    private void showCoinFragment() {
        if (view != null) {
            view.showCoinFragment();
        }
    }

    private void updateUserInboxState() {
        Log.d(TAG, "updateUserInboxState");
        if (currentUser == null) return;
        handler.execute(
                updateUserInboxState,
                new UpdateUserInboxState.RequestValues(currentUser, false),
                null);
    }

    private void showPostPager() {
        if (view != null) {
            view.showPostPager();
        }
    }

    private void showMessages(User user) {
        if (view != null) {
            view.showMessages(user);
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
        if (currentUser.getCoins() <= 0) {
            showError(res.getString(R.string.fragment_coin_needed));
            hideFabs();
            hideFabs();
            showCoinFragment();
            return;
        }
        showPostDialog();
    }

    private void showPostDialog() {
        kingPostSelected = true;
        hideFabs();
        tooglePostDialog();
    }

    private void showConfirmDialogToSeeRewardVideo() {
        if (view != null) {
            view.showConfirmDialogToSeeRewardVideo();
        }
    }

    private void tooglePostDialog() {
        if (!isPublishDialogVisible) {
            if (kingPostSelected) {
                showPublishKingImg();
            } else {
                hidePublishKingImg();
            }
            showPublishDialog();
        } else {
            hidePublishDialog();
        }
    }

    private void hidePublishKingImg() {
        if (view != null) {
            view.hidePublishKingImg();
        }
    }

    private void showPublishKingImg() {
        if (view != null) {
            view.showPublishKingImg();
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
//        changeTitle(post.getUser().getReadableName(res));
        hideFab();
        hideFabs();
        showPostDetail(post);
    }


    @Override
    public void onUserSelected(User user) {
        hideFab();
        hideFabs();
        showProfile(user, false);
    }

    @Override
    public void onCommentSelected(Comment comment) {

    }

    @Override
    public void onSendMessageClicked(User user) {
        startChat(currentUser.getId(), user.getId());
    }

    @Override
    public void coinsRewardClicked() {
        Log.d(TAG, "coinsRewardClicked");
        showRewardVideo();
    }

    @Override
    public void onConfirmedToShowRewardVideo() {
        Log.d(TAG, "");
        showRewardVideo();
    }

    @Override
    public void onInboxMessageClicked(Message message) {
        Log.d(TAG, "onInboxMessageClicked: " + message.toString());
        startChat(currentUser.getId(), message.getUser().getId());
    }

    @Override
    public void onNavDragEnd(boolean isMenuOpened) {
        isNavOpen = isMenuOpened;
    }

    private void showRewardVideo() {
        if (view != null) {
            view.showRewardVideo();
        }
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

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d(TAG, "onRewardedVideoAdLoaded");
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Log.d(TAG, "onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
        Log.d(TAG, "onRewardedVideoStarted");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Log.d(TAG, "onRewardedVideoAdClosed");
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Log.d(TAG, "onRewarded rewardItem amount: " + rewardItem.getAmount() + ", type: " + rewardItem.getType());
        updateUserCoins(rewardItem.getAmount());
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d(TAG, "onRewardedVideoAdLeftApplication");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.d(TAG, "onRewardedVideoAdFailedToLoad");
    }
}
