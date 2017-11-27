package apps.steve.fire.randomchat.main;

import java.util.List;

import apps.steve.fire.randomchat.base.BasePresenter;
import apps.steve.fire.randomchat.main.ui.entity.Item;

/**
 * Created by @stevecampos on 23/11/2017.
 */

public interface MainPresenter extends BasePresenter<MainView> {
    void onBurgerIconClicked();
    void onSubmitPost(String content, List<String> tagList);
    void onFabClicked();
    void onBackPressed();

    void onMenuItemSelected(Item item);
}
