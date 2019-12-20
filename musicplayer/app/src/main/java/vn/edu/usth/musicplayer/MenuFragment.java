package vn.edu.usth.musicplayer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import vn.edu.usth.musicplayer.searchBar.Controller;
import vn.edu.usth.musicplayer.searchBar.IconToSimpleLine;
import vn.edu.usth.musicplayer.searchBar.JSearchView;


import java.util.List;


public class MenuFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener {
    View view;
    private List<String> lastSearches;
    private MaterialSearchBar searchBar;
    private EditText editText;
    private  JSearchView searchView;
    private  JSearchView mSearchview;

    public MenuFragment() {
        //empty constructor
    }

    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu, container, false);
        Toolbar toolbar = view.findViewById(R.id.navbar_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = view.findViewById(R.id.drawer_layout);

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
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();


                if (id == R.id.nav_favorite) {
                    ft.replace(R.id.content_frag, new FavoriteFragment());
                    ft.addToBackStack(null);
                    Snackbar.make(view, "Favorite", Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_home) {
                    ft.replace(R.id.content_frag, new SongFragment());
                    ft.addToBackStack(null);
                    Snackbar.make(view, "Gallery", Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_slideshow) {
                    Snackbar.make(view, "Slide", Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_manage) {
                    Snackbar.make(view, "Manage", Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_share) {
                    Snackbar.make(view, "Share", Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_send) {
                    Snackbar.make(view, "Send", Snackbar.LENGTH_LONG);
                }
                ft.commit();
                return true;
            }
        });


        searchView = view.findViewById(R.id.searchBar);
        searchView.setController(new IconToSimpleLine());
        editText = view.findViewById(R.id.edit_text);
        final IconToSimpleLine sv1 = new IconToSimpleLine();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sv1.getState() == Controller.STATE_ANIM_NONE) {
                    searchView.startAnim();
                    editText.setVisibility(View.VISIBLE);
                    editText.bringToFront();
                } else if (sv1.getState() == Controller.STATE_ANIM_START) {
                    Toast.makeText(getContext(), "正在搜索", Toast.LENGTH_LONG).show();
                }

            }

        });

        return view;
    }





    @Override
    public void onSearchStateChanged(boolean enabled) {
//        String state = b ? "enabled" : "disabled";
//        Toast.makeText(MainActivity.this, "Search " + state, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
//        Toast.makeText(this,"Searching "+ charSequence.toString()+" ......",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onButtonClicked(int buttonCode) {
//        switch (i){
//            case MaterialSearchBar.BUTTON_NAVIGATION:
//                Toast.makeText(MainActivity.this, "Button Nav " , Toast.LENGTH_SHORT).show();
//                break;
//            case MaterialSearchBar.BUTTON_SPEECH:
//                Toast.makeText(MainActivity.this, "Speech " , Toast.LENGTH_SHORT).show();
//        }

    }
}
//
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//
//        switch (id) {
//            case R.id.nav_favorite :
//
//
//            case R.id.nav_home :
//                ft.replace(R.id.content_frag,new FavoriteFragment());
//                ft.addToBackStack(null);
//
//        }
//        ft.commit();
//        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//
//
//        }
//
//    }
