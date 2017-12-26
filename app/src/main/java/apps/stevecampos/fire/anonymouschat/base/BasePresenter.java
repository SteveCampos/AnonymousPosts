package apps.stevecampos.fire.anonymouschat.base;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface BasePresenter<T extends BaseView>{
    void attachView(T view);
    void onCreate();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
