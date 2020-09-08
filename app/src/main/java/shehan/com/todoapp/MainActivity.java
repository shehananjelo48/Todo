package shehan.com.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements DoneToDosFragment.DoneToDoFragmentBackKeyPressListener{

    private static final String UID = "UID";
    private String uid;

    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener;
private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton =findViewById(R.id.floatingActionButton);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        Intent intent = getIntent();
        uid = intent.getStringExtra(UID);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterDos enterDos  = EnterDos.newInstance(uid);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,enterDos);
                transaction.addToBackStack(null);
                transaction.commit();

                floatingActionButton.setVisibility(View.GONE);
            }
        });

        navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        //TODO: display home fragment

                        HomeFragment homeFragment=HomeFragment.newInstance(uid);
                        openFragment(homeFragment,false);
                        floatingActionButton.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.navigation_done_tasks:
                        //TODO: display done fragment

                        DoneToDosFragment doneToDosFragment=DoneToDosFragment.newInstance();
                        openFragment(doneToDosFragment,true);

                        floatingActionButton.setVisibility(View.GONE);
                        return true;
                }

                return false;
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        HomeFragment homeFragment=HomeFragment.newInstance(uid);
        openFragment(homeFragment,false);
    }
    private void openFragment(Fragment fragment,boolean addToBack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onPressBackKey() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
}