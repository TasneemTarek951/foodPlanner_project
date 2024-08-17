package db;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner_project.R;

import java.util.List;

import Home.Meal;
import Home.onMealClickListener;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private Context context;
    private List<Meal> meals;
    private onMealClickListener listener;

    public HomeAdapter(Context con,List<Meal> mealList,onMealClickListener li){
        context = con;
        meals = mealList;
        listener = li;
    }
    public void SetList(List<Meal> mealList){
        meals = mealList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.meal_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.strMeal.setText(meal.getStrMeal());
        holder.strArea.setText(meal.getstrArea());
        holder.strInstructions.setText(meal.getStrInstructions());
        holder.strIngredient1.setText(meal.getStrIngredient1());
        holder.strIngredient2.setText(meal.getStrIngredient2());
        holder.strIngredient3.setText(meal.getStrIngredient3());
        holder.strIngredient4.setText(meal.getStrIngredient4());
        holder.strIngredient5.setText(meal.getStrIngredient5());
        holder.strIngredient6.setText(meal.getStrIngredient6());
        holder.strIngredient7.setText(meal.getStrIngredient7());
        holder.strIngredient8.setText(meal.getStrIngredient8());

        Glide.with(context).load(meal.getStrMealThumb()).apply(new RequestOptions().override(150,150).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)).into(holder.strMealThumb);


        Uri videoUri = Uri.parse(meal.getStrYoutube().replace("watch?v=", "embed/"));
        holder.strYoutube.setVideoURI(videoUri);
        holder.strYoutube.start();

        holder.addtofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnmealclickListener(meal);
            }
        });


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView strMeal;
        TextView strArea;
        TextView strInstructions;
        ImageView strMealThumb;
        VideoView strYoutube;
        TextView strIngredient1;
        TextView strIngredient2;
        TextView strIngredient3;
        TextView strIngredient4;
        TextView strIngredient5;
        TextView strIngredient6;
        TextView strIngredient7;
        TextView strIngredient8;
        Button addtofav;
        Button addtoplan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            strMeal = itemView.findViewById(R.id.meal_name);
            strArea = itemView.findViewById(R.id.meal_country);
            strInstructions = itemView.findViewById(R.id.meal_instructions);
            strIngredient1 = itemView.findViewById(R.id.ing_1);
            strIngredient2 = itemView.findViewById(R.id.ing_2);
            strIngredient3 = itemView.findViewById(R.id.ing_3);
            strIngredient4 = itemView.findViewById(R.id.ing_4);
            strIngredient5 = itemView.findViewById(R.id.ing_5);
            strIngredient6 = itemView.findViewById(R.id.ing_6);
            strIngredient7 = itemView.findViewById(R.id.ing_7);
            strIngredient8 = itemView.findViewById(R.id.ing_8);
            strMealThumb = itemView.findViewById(R.id.meal_image);
            strYoutube = itemView.findViewById(R.id.video_view);
            addtofav = itemView.findViewById(R.id.add_fav);
            addtoplan = itemView.findViewById(R.id.add_plan);
        }
    }
}
