package Favofite;

import android.content.Context;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Home.Meal;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    private Context context;
    private List<Meal> mealList = new ArrayList<Meal>();
    private onFavoriteClickListener onFavoriteClickListener;
    private Lifecycle lifecycle;

    public FavoriteAdapter(Context con,List<Meal> meals,onFavoriteClickListener listener,Lifecycle life){
        context = con;
        mealList = meals;
        onFavoriteClickListener = listener;
        lifecycle = life;
    }

    public void setList(List<Meal> meals){
        mealList = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.fav_meal_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = mealList.get(position);
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

        /*holder.strYoutube.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = extractYouTubeVideoId(meal.getStrYoutube());
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
        lifecycle.addObserver(holder.strYoutube);*/

        holder.strYoutube.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = extractYouTubeVideoId(meal.getStrYoutube());
                if (videoId != null) {
                    youTubePlayer.cueVideo(videoId, 0);
                } else {
                    // Handle the case where videoId is null, e.g., log an error or show a message
                    Log.e("FavoriteAdapter", "Invalid YouTube video ID");
                }
            }
        });
        lifecycle.addObserver(holder.strYoutube);



        holder.removefromfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFavoriteClickListener.OnfavClickListener(meal);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        Button removefromfav;
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
            removefromfav = itemView.findViewById(R.id.remove_fav);
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
