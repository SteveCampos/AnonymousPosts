package apps.steve.fire.randomchat.main;

import android.support.annotation.DrawableRes;

import apps.steve.fire.randomchat.base.BaseView;
import apps.steve.fire.randomchat.main.ui.entity.Item;
import apps.steve.fire.randomchat.main.ui.entity.Post;
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


    void superOnBackPressed();

    void toogleMenuItems(Item old, Item selected);

    void showFabExtras();
    void hideFabExtras();

    void addPost(Post post);

    void startChat();

    void startIntro();

    void logout();

    void showError(String error);
}
