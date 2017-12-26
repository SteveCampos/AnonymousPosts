package apps.stevecampos.fire.anonymouschat.base;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface BaseFragmentPresenter<T extends BaseView> extends BasePresenter<T> {
    void onAttach();
    void onCreateView();
    void onViewCreated();
    void onDestroyView();
    void onDetach();
}
