package shehan.com.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private List<Todo> todoList;
    private String UID;
    Todo todo;

    public ItemListAdapter(List<Todo> todoList,String UID) {
        this.todoList = todoList;
        this.UID = UID;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_list_layout,parent,false);
        ItemListViewHolder viewHolder = new ItemListViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        todo= todoList.get(position);
        holder.title.setText(todo.getTitle());
        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done(todo.getTitle());
            }
        });


    }
    public void done(String title){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(UID).
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageButton done;
        ImageButton delete;
        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);

            title =itemView.findViewById(R.id.title);
            done = itemView.findViewById(R.id.done);
            delete= itemView.findViewById(R.id.delete);
        }
    }
}
