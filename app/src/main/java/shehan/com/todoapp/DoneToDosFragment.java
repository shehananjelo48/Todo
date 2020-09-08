package shehan.com.todoapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DoneToDosFragment extends Fragment {

private DoneToDoFragmentBackKeyPressListener doneToDoFragmentBackKeyPressListener;
    public DoneToDosFragment() {
        // Required empty public constructor
    }


    public static DoneToDosFragment newInstance() {
        DoneToDosFragment fragment = new DoneToDosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done_to_dos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    if (i==KeyEvent.KEYCODE_BACK){
                        doneToDoFragmentBackKeyPressListener.onPressBackKey();
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DoneToDosFragment.DoneToDoFragmentBackKeyPressListener){
            doneToDoFragmentBackKeyPressListener = (DoneToDosFragment.DoneToDoFragmentBackKeyPressListener)context;
        }else{
            throw  new RuntimeException(context.toString()+"must implement DoneToDosFragment.DoneToDoFragmentBackKeyPressListener");
        }

    }

    interface DoneToDoFragmentBackKeyPressListener{
        void onPressBackKey();
    }

}