package apps.stevecampos.fire.anonymouschat.appinfo;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class AppinfoFragment extends Fragment {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.txtAppName)
    TextView txtAppName;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtInstagram)
    TextView txtInstagram;
    @BindView(R.id.txtFacebook)
    TextView txtFacebook;
    @BindView(R.id.txtSourceCode)
    TextView txtSourceCode;
    @BindView(R.id.txtCopyright)
    TextView txtCopyright;
    Unbinder unbinder;

    public AppinfoFragment() {
        // Required empty public constructor
    }

    public static AppinfoFragment newInstance() {
        return new AppinfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appinfo, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txtAppName, R.id.txtEmail, R.id.txtInstagram, R.id.txtFacebook, R.id.txtSourceCode, R.id.txtCredits, R.id.txtCopyright})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtAppName:
                break;
            case R.id.txtEmail:
                sendToGmail(
                        getString(R.string.dev_email),
                        getString(R.string.app_name),
                        "");
                break;
            case R.id.txtInstagram:
                launchWebsite(getString(R.string.dev_instagram));
                break;
            case R.id.txtFacebook:
                launchWebsite(getString(R.string.dev_facebook));
                break;
            case R.id.txtSourceCode:
                launchWebsite(getString(R.string.appinfo_app_cvs_url));
                break;
            case R.id.txtCredits:
                showText(R.string.appinfo_item_credits, R.string.appinfo_item_credits_description);
                break;
            case R.id.txtCopyright:
                openPlayStore();
                break;
        }
    }

    private void sendToGmail(String gmail, String subject, String body) {
        String uriText =
                "mailto:" + gmail +
                        "?subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(body);

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.global_mssg_sendemail)));
        }
    }

    private void launchWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (browserIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(browserIntent);
        }
    }

    private void showText(@StringRes int title, @StringRes int desc) {
        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(desc)
                .setPositiveButton(R.string.global_text_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public void openPlayStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=" + getContext().getPackageName()));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
