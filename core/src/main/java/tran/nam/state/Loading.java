package tran.nam.state;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static tran.nam.state.Loading.LOADING_DIALOG;
import static tran.nam.state.Loading.LOADING_NONE;
import static tran.nam.state.Loading.LOADING_NORMAL;

/**
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the RepoRepository classes where they return
 * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
 */

@IntDef({LOADING_NONE, LOADING_DIALOG, LOADING_NORMAL})
@Retention(RetentionPolicy.SOURCE)
public @interface Loading {
    int LOADING_NONE = 4;
    int LOADING_DIALOG = 5;
    int LOADING_NORMAL = 6;
}
