package apps.steve.fire.randomchat.main;

import android.support.annotation.DrawableRes;

import apps.steve.fire.randomchat.base.BaseView;

/**
 * Created by Steve on 11/11/2017.
 */

public interface MainView extends BaseView<MainPresenter> {
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
}
