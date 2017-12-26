package apps.stevecampos.fire.anonymouschat.coin;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import apps.stevecampos.fire.anonymouschat.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoinFragment extends Fragment {


    @BindView(R.id.imgCoin)
    ImageView imgCoin;
    @BindView(R.id.txtCoinDesc)
    TextView txtCoinDesc;
    @BindView(R.id.btnRewardVideo)
    Button btnRewardVideo;
    Unbinder unbinder;

    private CoinFragmentListener listener;

    public CoinFragment() {
    }

    public static CoinFragment newInstance() {
        return new CoinFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CoinFragmentListener) {
            listener = (CoinFragmentListener) context;
        } else {
            throw new ClassCastException(context.getClass().getSimpleName() + "" +
                    "must implement CoinFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coins, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnRewardVideo)
    public void onRewardVideoClicked() {
        if (listener != null) {
            listener.onRewardVideoClicked();
        }
    }
}
