package apps.stevecampos.fire.anonymouschat.messages;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import apps.stevecampos.fire.anonymouschat.R;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseHandler;
import apps.stevecampos.fire.anonymouschat.base.usecase.UseCaseThreadPoolScheduler;
import apps.stevecampos.fire.anonymouschat.data.source.UserRepository;
import apps.stevecampos.fire.anonymouschat.data.source.local.UserLocalDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.remote.UserRemoteDataSource;
import apps.stevecampos.fire.anonymouschat.data.source.remote.firebase.FireUser;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.Message;
import apps.stevecampos.fire.anonymouschat.main.ui.entity.User;
import apps.stevecampos.fire.anonymouschat.messages.adapter.MessageAdapter;
import apps.stevecampos.fire.anonymouschat.messages.listener.MessagesListener;
import apps.stevecampos.fire.anonymouschat.messages.usecase.GetInboxMessages;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements MessageView {


    private static final String TAG = MessagesFragment.class.getSimpleName();
    @BindView(R.id.recyclerMessages)
    RecyclerView recyclerMessages;
    Unbinder unbinder;

    public MessagesFragment() {
        // Required empty public constructor
    }

    public static final String ARG_USER = "user";

    public static MessagesFragment newInstance(User user) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_USER, Parcels.wrap(user));
        fragment.setArguments(bundle);
        return fragment;
    }


    private User mainUser;
    private MessagesListener listener;
    private MessagePresenter presenter;
    private MessageAdapter adapter;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        if (context instanceof MessagesListener) {
            listener = (MessagesListener) context;
        } else {
            throw new ClassCastException(context.getClass().getSimpleName() + "" +
                    "must implement MessagesListener");
        }
        initPresenter();
    }

    private void initPresenter() {
        UserRepository repository = new UserRepository(
                new UserLocalDataSource(),
                new UserRemoteDataSource(new FireUser(), null)
        );
        presenter = new MessagePresenterImpl(
                new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                new GetInboxMessages(repository)
        );
        setPresenter(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (presenter != null) {
            presenter.onCreate();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        unbinder = ButterKnife.bind(this, view);
        initMessageAdapter();
        if (presenter != null) {
            presenter.onCreateView();
        }
        return view;
    }

    private void initMessageAdapter() {
        adapter = new MessageAdapter();
        adapter.setRecyclerView(recyclerMessages);
        adapter.setListener(listener);
        recyclerMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerMessages.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        getUser();
        if (presenter != null) {
            presenter.attachView(this);
            presenter.onViewCreated();
            presenter.setUser(mainUser);
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
        unbinder.unbind();
        if (presenter != null) {
            presenter.onDestroyView();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        listener = null;
        if (presenter != null) {
            presenter.onDetach();
        }
        super.onDetach();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }


    private void getUser() {
        mainUser = Parcels.unwrap(getArguments().getParcelable(ARG_USER));
        if (mainUser != null) {
            Log.d(TAG, "user id: " + mainUser.getId());
        }
    }

    @Override
    public void showProgress() {
        //progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //progress.setVisibility(View.GONE);
    }


    @Override
    public void setPresenter(MessagePresenter presenter) {
        presenter.onAttach();
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }

    @Override
    public void addMessage(Message message) {
        adapter.addMessage(message);
    }

    @Override
    public void changeMessage(Message message) {

    }

    @Override
    public void deleteMessage(Message message) {

    }
}
