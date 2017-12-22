package apps.steve.fire.randomchat.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import apps.steve.fire.randomchat.R;
import apps.steve.fire.randomchat.base.usecase.UseCaseHandler;
import apps.steve.fire.randomchat.base.usecase.UseCaseThreadPoolScheduler;
import apps.steve.fire.randomchat.chat.adapter.MessageAdapter;
import apps.steve.fire.randomchat.chat.usecase.GetChatMessages;
import apps.steve.fire.randomchat.chat.usecase.SendMessage;
import apps.steve.fire.randomchat.data.source.UserRepository;
import apps.steve.fire.randomchat.data.source.local.UserLocalDataSource;
import apps.steve.fire.randomchat.data.source.remote.UserRemoteDataSource;
import apps.steve.fire.randomchat.data.source.remote.firebase.FireUser;
import apps.steve.fire.randomchat.main.ui.entity.Message;
import apps.steve.fire.randomchat.main.usecase.GetUser;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView {

    private static final String ARG_USER_ID = "ARG_USER_ID";
    private static final String ARG_RECEPTOR_ID = "ARG_RECEPTOR_ID";
    private static final String TAG = "ChatActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bgBottom)
    View bgBottom;
    @BindView(R.id.imgEmoji)
    ImageView imgEmoji;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.imgSend)
    ImageView imgSend;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.progress)
    ProgressBar progress;

    public static void startChatActivity(Context context, String userId, String receptorId) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_ID, userId);
        bundle.putString(ARG_RECEPTOR_ID, receptorId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private ChatPresenter presenter;
    private MessageAdapter adapter;
    private String userId;
    private String receptorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra(ARG_USER_ID);
        receptorId = getIntent().getStringExtra(ARG_RECEPTOR_ID);
        Log.d(TAG, "userId: " + userId);
        Log.d(TAG, "receptorId: " + receptorId);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupAdapter();
        initPresenter();
    }

    private void setupAdapter() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter();
        adapter.setRecyclerView(recycler);
        recycler.setAdapter(adapter);
    }

    @Override
    public void setPresenter(ChatPresenter presenter) {
        presenter.attachView(this);
        presenter.onCreate();
        presenter.setExtras(userId, receptorId);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    private void initPresenter() {
        presenter = (ChatPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            UserRepository repository = new UserRepository(
                    new UserLocalDataSource(),
                    new UserRemoteDataSource(new FireUser(), null)
            );
            presenter = new ChatPresenterImpl(
                    getResources(),
                    new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                    new GetUser(repository),
                    new GetUser(repository),
                    new SendMessage(repository),
                    new GetChatMessages(repository)
            );
        }
        setPresenter(presenter);
    }


    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void addMessage(Message message) {
        Log.d(TAG, "addMessage");
        hideProgress();
        adapter.addMessage(message);
    }

    @Override
    public void hideSoftboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void cleanEdtMessage() {
        editText.setText("");
    }


    @OnClick(R.id.imgSend)
    public void imgSendClicked() {
        presenter.onMessageSubmit(editText.getText().toString());
    }
}
