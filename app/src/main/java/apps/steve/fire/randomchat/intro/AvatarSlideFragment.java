package apps.steve.fire.randomchat.intro;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.intro.adapter.AvatarAdapter;
import apps.steve.fire.randomchat.intro.entity.AvatarUi;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvatarSlideFragment extends Fragment {


    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.bottomView)
    View bottomView;
    @BindView(R.id.recyclerAvatar)
    RecyclerView recyclerAvatar;
    Unbinder unbinder;

    public AvatarSlideFragment() {
        // Required empty public constructor
    }

    public static AvatarSlideFragment newInstance() {
        return new AvatarSlideFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_avatar, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
    }

    private AvatarAdapter adapter;

    private void setupAdapter() {
        adapter = new AvatarAdapter(AvatarUi.getAvatarList());
        recyclerAvatar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerAvatar.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
