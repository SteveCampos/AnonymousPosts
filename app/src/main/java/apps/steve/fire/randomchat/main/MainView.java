package apps.steve.fire.randomchat.main;

import apps.steve.fire.randomchat.base.BaseView;

/**
 * Created by Steve on 11/11/2017.
 */

public interface MainView extends BaseView<MainPresenter> {

    void showSplashScreen();

    void hideSplashScreen();

    void showPublishDialog();

    void hidePublishDialog();

}
