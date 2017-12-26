package apps.stevecampos.fire.anonymouschat.main.adapter.entity;

import android.support.v4.app.Fragment;

/**
 * Created by @stevecampos on 6/12/2017.
 */

public class PageFragment {
    private String tag;
    private Fragment fragment;

    public PageFragment(String tag, Fragment fragment) {
        this.tag = tag;
        this.fragment = fragment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public boolean equals(Object obj) {
        boolean success = false;
        if (obj instanceof PageFragment) {
            PageFragment pageFragment = (PageFragment) obj;
            if (pageFragment.getTag() != null && pageFragment.getTag().equals(tag)) {
                success = true;
            }
        }
        return success;
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }
}
