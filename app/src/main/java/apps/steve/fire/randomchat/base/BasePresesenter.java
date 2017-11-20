package apps.steve.fire.randomchat.base;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface BasePresesenter<T extends BaseView>{
    void attachView(T view);
    void onCreate();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
