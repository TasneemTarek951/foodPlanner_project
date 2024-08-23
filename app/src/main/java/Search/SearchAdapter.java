package Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner_project.R;
import com.example.foodplanner_project.SearchFragment;
import com.example.foodplanner_project.SearchFragmentDirections;

import java.util.List;

import db.Category;
import db.Country;
import db.Ingredient;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    List<Country> countries;
    String str;


    public SearchAdapter(List<Country> countries){
        this.countries = countries;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country =  countries.get(position);
        holder.textView.setText(country.getStrArea());

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
        return countries != null ? countries.size() : 0;
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
    public void updateList(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }
}
