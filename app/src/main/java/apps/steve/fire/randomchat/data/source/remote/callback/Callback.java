package apps.steve.fire.randomchat.data.source.remote.callback;

/**
 * Created by @stevecampos on 22/11/2017.
 */

public interface Callback<T> {
    void onSucess(T object);
}
