package apps.steve.fire.randomchat;

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
        String SAMPLE_ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";
        MobileAds.initialize(this, SAMPLE_ADMOB_APP_ID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
