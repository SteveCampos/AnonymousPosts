package apps.stevecampos.fire.anonymouschat.data.source.remote.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public class Fire {
    protected DatabaseReference mDatabase;

    public Fire() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

}
