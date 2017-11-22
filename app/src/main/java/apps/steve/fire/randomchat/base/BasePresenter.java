package apps.steve.fire.randomchat.base;

/**
 * Created by Steve on 18/11/2017.
 */

public interface BasePresenter<T extends BaseView> {

    void onAttach(T view);

    void onCreate();

    void start();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
