package apps.steve.fire.randomchat.intro;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apps.steve.fire.randomchat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvatarSlideFragment extends Fragment {


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
        return view;
    }

}
