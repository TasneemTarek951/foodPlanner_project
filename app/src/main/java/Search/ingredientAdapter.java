package Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner_project.R;
import com.example.foodplanner_project.SearchFragment;
import com.example.foodplanner_project.SearchFragmentDirections;

import java.util.List;

import db.Ingredient;

public class ingredientAdapter extends RecyclerView.Adapter<ingredientAdapter.ViewHolder>{

    List<Ingredient> ingredients;
    String str;
    Context context;

    public ingredientAdapter(List<Ingredient> ingredients,Context con){
        this.ingredients = ingredients;
        context = con;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.textView.setText(ingredient.getStrIngredient());

        Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+".png").apply(new RequestOptions().override(150,150).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)).into(holder.img);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = holder.textView.getText().toString();
                SearchFragmentDirections.ActionSearchFragmentToSearchResultFragment action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(str, SearchFragment.str1);
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.meal_name);
            img = itemView.findViewById(R.id.meal_image);
        }
    }

    public void updateList(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }
}
