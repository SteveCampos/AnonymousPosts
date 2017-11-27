package apps.steve.fire.randomchat.postpager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.intro.GenderSlideFragment;
import apps.steve.fire.randomchat.postpager.adapter.MyFragmentAdapter;
import apps.steve.fire.randomchat.posts.PostsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostPagerFragment extends Fragment {


    private static final String TAG = PostPagerFragment.class.getSimpleName();
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.frameLayout)
    ConstraintLayout frameLayout;
    Unbinder unbinder;
    /*@BindView(R.id.toolbar)
    Toolbar toolbar;*/
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    public PostPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_postpager, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
    }

    private MyFragmentAdapter fragmentAdapter;

    private void setupViewPager() {
        Log.d(TAG, "setupViewPager");
        fragmentAdapter = new MyFragmentAdapter(getFragmentManager());
        fragmentAdapter.addFragment(PostsFragment.newInstance(), getString(R.string.fragment_posts_section_popular));
        fragmentAdapter.addFragment(PostsFragment.newInstance(), getString(R.string.fragment_posts_section_recent));
        viewpager.setOffscreenPageLimit(2);
        viewpager.setAdapter(fragmentAdapter);
        //viewpager.setCurrentItem(0);
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
