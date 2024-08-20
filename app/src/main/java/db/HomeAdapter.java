package db;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner_project.MainActivity2;
import com.example.foodplanner_project.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import Home.Meal;
import Home.onMealClickListener;
import MyPlan.MealPlan;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private Context context;
    private List<Meal> meals;
    private onMealClickListener listener;
    private Lifecycle lifecycle;
    private String selectedDate;
    private String selectedMeal;
    private MealPlan mealPlan = new MealPlan();

    public HomeAdapter(Context con,List<Meal> mealList,onMealClickListener li,Lifecycle life){
        context = con;
        meals = mealList;
        listener = li;
        lifecycle = life;
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

        holder.strYoutube.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = extractYouTubeVideoId(meal.getStrYoutube());;
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
        lifecycle.addObserver(holder.strYoutube);

        //mealPlan.setDay(selectedDate);
        mealPlan.setStrMeal(meal.getStrMeal());
        mealPlan.setBritish(meal.getstrArea());
        mealPlan.setStrInstructions(meal.getStrInstructions());
        mealPlan.setStrIngredient1(meal.getStrIngredient1());
        mealPlan.setStrIngredient2(meal.getStrIngredient2());
        mealPlan.setStrIngredient3(meal.getStrIngredient3());
        mealPlan.setStrIngredient4(meal.getStrIngredient4());
        mealPlan.setStrIngredient5(meal.getStrIngredient5());
        mealPlan.setStrIngredient6(meal.getStrIngredient6());
        mealPlan.setStrIngredient7(meal.getStrIngredient7());
        mealPlan.setStrIngredient8(meal.getStrIngredient8());
        mealPlan.setStrYoutube(meal.getStrYoutube());
        mealPlan.setStrMealThumb(meal.getStrMealThumb());

        holder.addtofav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnmealclickListener(meal);
            }
        });

       // listener.clickListener(mealPlan);
        holder.addtoplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMealSelectionDialog();

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
        YouTubePlayerView strYoutube;
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
            if(MainActivity2.type.equals("Guest")){
                addtofav.setVisibility(itemView.GONE);
                addtoplan.setVisibility(itemView.GONE);

            }
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

    private void showMealSelectionDialog() {
        // Create a list of meal options
        final String[] mealOptions = {"Breakfast", "Lunch", "Dinner"};

        // Show an AlertDialog to choose a meal
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Meal");
        builder.setSingleChoiceItems(mealOptions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedMeal = mealOptions[which];
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectedMeal != null) {
                    // After selecting a meal, show the DatePickerDialog
                    showDatePickerDialog();
                } else {
                    Toast.makeText(context, "Please select a meal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDatePickerDialog() {
        // Get the current date to initialize the DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the calendar
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Format the date to include the day name
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy", Locale.getDefault());
                        selectedDate = dateFormat.format(calendar.getTime());

                        // Combine selected meal and date
                        String result = selectedMeal + " on " + selectedDate;

                        // Display the result in the button or wherever you need
                        mealPlan.setDay(result);
                        listener.clickListener(mealPlan);

                    }
                }, year, month, day);

        // Show the dialog
        datePickerDialog.show();
    }
}
