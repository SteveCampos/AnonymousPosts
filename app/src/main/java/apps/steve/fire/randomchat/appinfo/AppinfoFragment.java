package apps.steve.fire.randomchat.appinfo;


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
public class AppinfoFragment extends Fragment {


    public AppinfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appinfo, container, false);
        return view;
    }

}
