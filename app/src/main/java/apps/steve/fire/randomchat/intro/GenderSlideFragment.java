package apps.steve.fire.randomchat.intro;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.intro.listener.GenderListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenderSlideFragment extends Fragment {


    private static final String TAG = GenderSlideFragment.class.getSimpleName();
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txtImgDescription)
    TextView txtImgDescription;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.bottomView)
    View bottomView;
    Unbinder unbinder;

    public GenderSlideFragment() {
        // Required empty public constructor
    }

    public static GenderSlideFragment newInstance() {
        return new GenderSlideFragment();
    }

    private GenderListener listener;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof GenderListener) {
            listener = (GenderListener) context;
        } else {
            throw new ClassCastException(GenderSlideFragment.class.getSimpleName() + "" +
                    "must be implement GenderListener!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_slide_gender, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if (listener != null) {
            listener.onGenderSlideResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        listener = null;
        super.onDetach();
    }


    public void setImg(@DrawableRes int drawable) {
        Context context = getContext();
        if (context != null) {
            Glide.with(context)
                    .asDrawable()
                    .load(drawable)
                    .into(img);
        }
    }

    public void setImgDescription(CharSequence description) {
        txtImgDescription.setText(description);
    }

    @OnClick(R.id.img)
    public void onViewClicked() {
        listener.onNextGender();
    }
}
