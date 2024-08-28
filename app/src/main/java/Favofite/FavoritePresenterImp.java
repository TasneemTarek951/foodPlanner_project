package Favofite;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import Home.Meal;
import db.Repository;

public class FavoritePresenterImp implements FavoritePresenter{
    Context context;
    LifecycleOwner owner;
    Repository repo;
    FavoriteView favoriteView;

    public FavoritePresenterImp(Context con,LifecycleOwner own,FavoriteView view){
        context = con;
        owner = own;
        favoriteView = view;
        repo = new Repository(context);
        Observer<List<Meal>> observer = new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> productList) {
                view.ShowData(productList);
            }
        };
        LiveData<List<Meal>> observable = repo.getStoredmeals();
        observable.observe(owner,observer);

    }

    public List<Meal> Showfav(){
        return repo.Showfavmeals();
    }

    @Override
    public void removefromFavorite(Meal meal) {
          repo.delete(meal);
          repo.deletfromfav(meal);
    }
}
