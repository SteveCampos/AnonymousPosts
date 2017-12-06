package apps.steve.fire.randomchat.main.ui.entity;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

import apps.steve.fire.randomchat.R;

/**
 * Created by Steve on 25/11/2017.
 */

public class Item {
    private int id;
    private @DrawableRes
    int icon;
    private String name;
    private boolean selected;

    public Item(int id, int icon, String name, boolean selected) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static final int MENU_POSTS = 1;
    public static final int MENU_PROFILE = 2;
    public static final int MENU_USERS = 3;
    public static final int MENU_MESSAGES = 4;
    public static final int MENU_APPINFO = 5;
    public static final int MENU_LOGOUT = 6;

    public static List<Item> getMenuList(Resources res) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(MENU_POSTS, R.drawable.ic_whatshot_accent_24dp, res.getString(R.string.fragment_posts_title), false));
        items.add(new Item(MENU_PROFILE, R.drawable.ic_person_red_24dp, res.getString(R.string.menuitem_myprofile_title), false));
        items.add(new Item(MENU_USERS, R.drawable.ic_people_orange_24dp, res.getString(R.string.fragment_users_title), false));
        items.add(new Item(MENU_MESSAGES, R.drawable.ic_chat_teal_24dp, res.getString(R.string.fragment_messages_title), false));
        items.add(new Item(MENU_APPINFO, R.drawable.ic_info_teal_24dp, res.getString(R.string.menuitem_appinfo_title), false));
        items.add(new Item(MENU_LOGOUT, R.drawable.ic_close_grey_24dp, res.getString(R.string.menuitem_logout_title), false));
        return items;
    }
}
