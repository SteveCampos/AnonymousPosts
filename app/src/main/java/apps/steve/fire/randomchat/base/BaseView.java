package apps.steve.fire.randomchat.base;

/**
 * Created by @stevecampos on 20/11/2017.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
