package vn.edu.usth.musicplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MenuFragment extends Fragment {
    View view;
    public MenuFragment() {
        //empty constructor
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu, container, false);
        Toolbar toolbar = view.findViewById(R.id.navbar_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Replace with your own action",Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();
            }
        });

        DrawerLayout drawer =  view.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_camera) {
                    Snackbar.make(view,"Camera",Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_gallery) {
                    Snackbar.make(view,"Gallery",Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_slideshow) {
                    Snackbar.make(view,"Slide",Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_manage) {
                    Snackbar.make(view,"Manage",Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_share) {
                    Snackbar.make(view,"Share",Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_send) {
                    Snackbar.make(view,"Send",Snackbar.LENGTH_LONG);
                }
                return true;
            }
        });


        return view;
    }

}
