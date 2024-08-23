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
        if(country.getStrArea().equals("American")){
            holder.img.setImageResource(R.drawable.american);
        }
        if(country.getStrArea().equals("British")){
            holder.img.setImageResource(R.drawable.british);
        }
        if(country.getStrArea().equals("Canadian")){
            holder.img.setImageResource(R.drawable.canadian);
        }
        if(country.getStrArea().equals("Chinese")){
            holder.img.setImageResource(R.drawable.chinese);
        }
        if(country.getStrArea().equals("Croatian")){
            holder.img.setImageResource(R.drawable.croatian);
        }
        if(country.getStrArea().equals("Dutch")){
            holder.img.setImageResource(R.drawable.dutch);
        }
        if(country.getStrArea().equals("Egyptian")){
            holder.img.setImageResource(R.drawable.egyptian);
        }
        if(country.getStrArea().equals("Filipino")){
            holder.img.setImageResource(R.drawable.filipino);
        }
        if(country.getStrArea().equals("French")){
            holder.img.setImageResource(R.drawable.french);
        }
        if(country.getStrArea().equals("Greek")){
            holder.img.setImageResource(R.drawable.greek);
        }
        if(country.getStrArea().equals("Indian")){
            holder.img.setImageResource(R.drawable.india);
        }
        if(country.getStrArea().equals("Irish")){
            holder.img.setImageResource(R.drawable.irish);
        }
        if(country.getStrArea().equals("Italian")){
            holder.img.setImageResource(R.drawable.italian);
        }
        if(country.getStrArea().equals("Jamaican")){
            holder.img.setImageResource(R.drawable.jamaican);
        }
        if(country.getStrArea().equals("Japanese")){
            holder.img.setImageResource(R.drawable.japanese);
        }
        if(country.getStrArea().equals("Kenyan")){
            holder.img.setImageResource(R.drawable.kenyan);
        }
        if(country.getStrArea().equals("Malaysian")){
            holder.img.setImageResource(R.drawable.malaysian);
        }
        if(country.getStrArea().equals("Mexican")){
            holder.img.setImageResource(R.drawable.mexican);
        }
        if(country.getStrArea().equals("Moroccan")){
            holder.img.setImageResource(R.drawable.moroccan);
        }
        if(country.getStrArea().equals("Polish")){
            holder.img.setImageResource(R.drawable.polish);
        }
        if(country.getStrArea().equals("Portuguese")){
            holder.img.setImageResource(R.drawable.portuguese);
        }
        if(country.getStrArea().equals("Russian")){
            holder.img.setImageResource(R.drawable.russian);
        }
        if(country.getStrArea().equals("Spanish")){
            holder.img.setImageResource(R.drawable.spanish);
        }
        if(country.getStrArea().equals("Thai")){
            holder.img.setImageResource(R.drawable.thai);
        }
        if(country.getStrArea().equals("Tunisian")){
            holder.img.setImageResource(R.drawable.tunisian);
        }
        if(country.getStrArea().equals("Turkish")){
            holder.img.setImageResource(R.drawable.turkish);
        }
        if(country.getStrArea().equals("Ukrainian")){
            holder.img.setImageResource(R.drawable.ukrainian);
        }
        if(country.getStrArea().equals("Unknown")){
            holder.img.setImageResource(R.drawable.unknown);
        }
        if(country.getStrArea().equals("Vietnamese")){
            holder.img.setImageResource(R.drawable.vietnamese);
        }

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
