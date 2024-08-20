package MyPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner_project.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Favofite.FavoriteAdapter;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder>{
    private Context context;
    private List<MealPlan> mealPlanList;
    private onPlanClickListener onPlanClickListener;
    private Lifecycle lifecycle;


    public PlanAdapter(Context con,List<MealPlan> mealsplan,onPlanClickListener listener,Lifecycle life){
        context = con;
        mealPlanList = mealsplan;
        onPlanClickListener = listener;
        lifecycle = life;
    }

    public void setList(List<MealPlan> mealsplan){
        mealPlanList = mealsplan;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.plane_meal,parent,false);
        PlanAdapter.ViewHolder viewHolder = new PlanAdapter.ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealPlan mealPlan = mealPlanList.get(position);
        holder.day.setText(mealPlan.getDay());
        holder.strArea.setText(mealPlan.getstrArea());
        holder.strMeal.setText(mealPlan.getStrMeal());
        holder.strInstructions.setText(mealPlan.getStrInstructions());
        holder.strIngredient1.setText(mealPlan.getStrIngredient1());
        holder.strIngredient2.setText(mealPlan.getStrIngredient2());
        holder.strIngredient3.setText(mealPlan.getStrIngredient3());
        holder.strIngredient4.setText(mealPlan.getStrIngredient4());
        holder.strIngredient5.setText(mealPlan.getStrIngredient5());
        holder.strIngredient6.setText(mealPlan.getStrIngredient6());
        holder.strIngredient7.setText(mealPlan.getStrIngredient7());
        holder.strIngredient8.setText(mealPlan.getStrIngredient8());


        Glide.with(context).load(mealPlan.getStrMealThumb()).apply(new RequestOptions().override(150,150).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)).into(holder.strMealThumb);


        holder.strYoutube.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = extractYouTubeVideoId(mealPlan.getStrYoutube());
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
        lifecycle.addObserver(holder.strYoutube);


        holder.removefromplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlanClickListener.onplanclicklistener(mealPlan);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mealPlanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView strMeal;
        TextView strArea;
        TextView strInstructions;
        ImageView strMealThumb;
        YouTubePlayerView strYoutube;
        TextView strIngredient1;
        TextView strIngredient2;
        TextView strIngredient3;
        TextView strIngredient4;
        TextView strIngredient5;
        TextView strIngredient6;
        TextView strIngredient7;
        TextView strIngredient8;
        Button removefromplan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.date);
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
            removefromplan = itemView.findViewById(R.id.remove_plan);
        }
    }

    public String extractYouTubeVideoId(String url) {
        String videoId = null;
        String regex = "v=([^&]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            videoId = matcher.group(1);
        }
        return videoId;
    }
}
