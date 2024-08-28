package Authentication;

import android.app.Activity;
import android.content.Context;
import com.google.firebase.auth.FirebaseUser;

import db.Repository;

public class LogoutpresenterImp implements LogoutPresenter{
    private Repository repo;
    private Context context;
    private LogoutView view;
    private Activity activity;

    public LogoutpresenterImp(LogoutView v,Context con,Activity act){
        activity = act;
        context = con;
        view = v;
        repo = new Repository(context);
    }
    @Override
    public void logout() {
        repo.signOut(activity, new Repository.fireCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                view.closeActivity();
            }

            @Override
            public void onFailure(String message) {
                view.closeActivity();
            }
        });
    }
}
