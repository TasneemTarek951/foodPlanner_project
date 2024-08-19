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
import com.example.foodplanner_project.SearchResultFragmentDirections;

import java.util.List;

import db.HomeAdapter;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private Context context;
    private List<Image> images;

    public SearchResultAdapter(Context con,List<Image> imageList){
        context = con;
        images = imageList;
    }
    public void SetList(List<Image> imageList){
        images = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.image_layout,parent,false);
        SearchResultAdapter.ViewHolder viewHolder = new SearchResultAdapter.ViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image = images.get(position);
        holder.mealname.setText(image.getStrMeal());

        Glide.with(context).load(image.getStrMealThumb()).apply(new RequestOptions().override(150,150).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)).into(holder.mealimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResultFragmentDirections.ActionSearchResultFragmentToMealDetailsFragment action = SearchResultFragmentDirections.actionSearchResultFragmentToMealDetailsFragment(holder.mealname.getText().toString());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealname;
        ImageView mealimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealname = itemView.findViewById(R.id.meal_name);
            mealimage = itemView.findViewById(R.id.meal_image);
        }
    }
}
