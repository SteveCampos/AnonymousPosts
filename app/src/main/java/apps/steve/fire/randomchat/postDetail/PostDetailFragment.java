package apps.steve.fire.randomchat.postDetail;


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
public class PostDetailFragment extends Fragment {


    public PostDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_comments, container, false);
        return view;
    }

}
