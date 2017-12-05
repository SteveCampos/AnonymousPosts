package apps.steve.fire.randomchat.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import apps.steve.fire.randomchat.R;

public class ChatActivity extends AppCompatActivity {

    private static final String ARG_USER_ID = "ARG_USER_ID";
    private static final String ARG_RECEPTOR_ID = "ARG_RECEPTOR_ID";

    public static void startChatActivity(Context context, String userId, String receptorId) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userId);
        bundle.putString(ARG_RECEPTOR_ID, receptorId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
