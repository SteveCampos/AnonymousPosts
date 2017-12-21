package apps.steve.fire.randomchat.main;

import android.support.annotation.DrawableRes;

import apps.steve.fire.randomchat.base.BaseView;
import apps.steve.fire.randomchat.main.ui.entity.Item;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;
import apps.steve.fire.randomchat.posts.PostView;

/**
 * Created by Steve on 11/11/2017.
 */

public interface MainView extends BaseView<MainPresenter> {
    /**/
    boolean checkPlayServicesAvaliability();

    /*Profile Views*/
    void showName(String name);

    void showCity(String city);

    void showAvatar(@DrawableRes int avatar);

    void showSplashScreen();

    void hideSplashScreen();

    void showPublishDialog();

    void hidePublishDialog();

    void openNavigation();

    void closeNavigation();


    void close();

    void toogleMenuItems(Item old, Item selected);

    void showFab();

    void hideFab();

    void changeTitle(String title);

    void showFabExtras();

    void hideFabExtras();

    void addPost(Post post);

    void startChat(String userId, String receptorId);

    void startIntro();

    void logout();

    void showError(String error);

    int getBackStackEntryCount();

    void popBackStack();

    void showPostPager();

    void showProfile(User user, boolean editable);

    void showPostDetail(Post post);

    void showMessages(User user);

    void showPostsWithTag(Post post, String tag);

    void showAppInfo();
}
