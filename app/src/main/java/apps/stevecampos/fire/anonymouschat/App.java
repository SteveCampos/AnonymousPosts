package apps.stevecampos.fire.anonymouschat;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;

/**
 * Created by @stevecampos on 21/11/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        String addmob_app_id = BuildConfig.admob_app_id;
        MobileAds.initialize(this, addmob_app_id);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
