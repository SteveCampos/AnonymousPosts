package apps.steve.fire.randomchat.base;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface BaseFragmentPresenter<T extends BaseView> extends BasePresesenter<T> {
    void onAttach();
    void onCreateView();
    void onViewCreated();
    void onDestroyView();
    void onDetach();
}
