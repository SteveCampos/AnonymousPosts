package apps.steve.fire.randomchat.main;

import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.List;

import apps.steve.fire.randomchat.base.BasePresenter;
import apps.steve.fire.randomchat.main.ui.entity.Comment;
import apps.steve.fire.randomchat.main.ui.entity.Item;
import apps.steve.fire.randomchat.main.ui.entity.Message;
import apps.steve.fire.randomchat.main.ui.entity.Post;
import apps.steve.fire.randomchat.main.ui.entity.User;

/**
 * Created by @stevecampos on 23/11/2017.
 */

public interface MainPresenter extends BasePresenter<MainView>, RewardedVideoAdListener {
    void onBurgerIconClicked();
    void onSubmitPost(String content, List<String> tagList);
    void onFabClicked();
    void onBackPressed();

    void onMenuItemSelected(Item item);

    void onFabProClicked();

    void onFabRegularClicked();

    void onPostSelected(Post post);

    void onUserSelected(User user);

    void onCommentSelected(Comment comment);

    void onSendMessageClicked(User user);

    void coinsRewardClicked();

    void onConfirmedToShowRewardVideo();

    void onInboxMessageClicked(Message message);

    void onNavDragEnd(boolean isMenuOpened);
}
