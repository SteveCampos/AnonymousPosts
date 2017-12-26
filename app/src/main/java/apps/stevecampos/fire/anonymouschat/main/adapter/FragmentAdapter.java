package apps.stevecampos.fire.anonymouschat.main.adapter;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import apps.stevecampos.fire.anonymouschat.main.adapter.entity.PageFragment;

/**
 * Created by @stevecampos on 6/12/2017.
 */

public class FragmentAdapter {
    private List<PageFragment> pageFragments = new ArrayList<>();

    public FragmentAdapter() {
    }

    public void addFragment(Fragment fragment, String tag) {
        PageFragment pageFragment = new PageFragment(tag, fragment);
        if (!pageFragments.contains(pageFragment)) {
            pageFragments.add(pageFragment);
        }
    }

    public void updateFragment(Fragment fragment, String tag) {
        PageFragment pageFragment = new PageFragment(tag, fragment);
        if (pageFragments.contains(pageFragment)) {
            int position = pageFragments.indexOf(pageFragment);
            pageFragments.set(position, pageFragment);
        }
    }

    public void removeFragment(Fragment fragment, String tag) {
        PageFragment pageFragment = new PageFragment(tag, fragment);
        if (pageFragments.contains(pageFragment)) {
            int position = pageFragments.indexOf(pageFragment);
            pageFragments.remove(position);
        }
    }

    public void clear() {
        pageFragments.clear();
    }
}
