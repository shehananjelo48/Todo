package shehan.com.todoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;


public class EnterDos extends Fragment {

    private static final String UID = "UID";

    private TextInputEditText title;
    private Button addTodo;
    private TextInputEditText description;
    private CalendarView calendarView;

    private String uid;


    public EnterDos() {
        // Required empty public constructor
    }


    public static EnterDos newInstance(String uid) {
        EnterDos fragment = new EnterDos();
        Bundle args = new Bundle();
        args.putString(UID, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uid = getArguments().getString(UID);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_enter_dos, container, false);
        title=inflate.findViewById(R.id.title);
        addTodo=inflate.findViewById(R.id.addTodo);
        description=inflate.findViewById(R.id.description);
        calendarView=inflate.findViewById(R.id.calendarView);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                String gtitle = title.getText().toString();
                String gdescription = description.getText().toString();
                long date = calendarView.getDate();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Todo todo = new Todo(gtitle,gdescription,true);
                db.collection(uid).document().set(todo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "successfully saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "TODO save failed: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}