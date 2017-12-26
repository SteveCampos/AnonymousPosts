package apps.stevecampos.fire.anonymouschat.intro;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.intro.adapter.AvatarAdapter;
import apps.stevecampos.fire.anonymouschat.intro.entity.AvatarUi;
import apps.stevecampos.fire.anonymouschat.intro.listener.AvatarListener;
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
    @BindView(R.id.progress)
    ProgressBar progress;


    public AvatarSlideFragment() {
        // Required empty public constructor
    }

    public static AvatarSlideFragment newInstance() {
        return new AvatarSlideFragment();
    }

    private AvatarListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AvatarListener) {
            listener = (AvatarListener) context;
        } else {
            throw new ClassCastException(AvatarSlideFragment.class.getSimpleName() + "" +
                    "must implement AvatarListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slide_avatar, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAdapter();
    }

    public void showProgress(){
        progress.setVisibility(View.VISIBLE);
    }
    public void hideProgress(){
        progress.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (listener != null) {
            listener.onAvatarSlideResume();
        }
    }

    private AvatarAdapter adapter;

    private void setupAdapter() {
        adapter = new AvatarAdapter(new ArrayList<AvatarUi>(), listener);
        recyclerAvatar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerAvatar.setAdapter(adapter);
    }

    public void showAvatarList(List<AvatarUi> avatarList) {
        adapter.setAvatarList(avatarList);
    }

    public void updateAvatar(AvatarUi avatarUi) {
        adapter.updateAvatar(avatarUi);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void toggleAvatars(AvatarUi old, AvatarUi actual) {
        adapter.toggleAvatars(old, actual);
    }
}
