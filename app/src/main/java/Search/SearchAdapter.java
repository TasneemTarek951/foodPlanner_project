package Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner_project.SearchFragment;
import com.example.foodplanner_project.SearchFragmentDirections;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private List<String> itemList;
    String str;


    public SearchAdapter(List<String> itemList){
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(itemList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
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
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
    public void updateList(List<String> newList) {
        itemList = newList;
        notifyDataSetChanged();
    }
}
