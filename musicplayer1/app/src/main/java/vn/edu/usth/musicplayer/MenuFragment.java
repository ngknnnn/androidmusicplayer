package vn.edu.usth.musicplayer;

import android.app.DownloadManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.database.MatrixCursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import vn.edu.usth.musicplayer.searchBar.Controller;
import vn.edu.usth.musicplayer.searchBar.IconToSimpleLine;
import vn.edu.usth.musicplayer.searchBar.JSearchView;

import static androidx.core.content.ContextCompat.getSystemService;


public class MenuFragment extends Fragment {
    View view;
    private List<String> lastSearches;
    private SearchView search;
    private JSearchView searchView;
    private FragmentTransaction ft;
    private SimpleCursorAdapter myAdapter;
    private RequestQueue  requestQueue;
    ArrayList<String> listSong = new ArrayList(5);

    String[] list = {

    };


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
        ft = getActivity().getSupportFragmentManager().beginTransaction();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();


                if (id == R.id.nav_favorite) {
                    ft.replace(R.id.content_frag, new FavoriteFragment());
                    ft.addToBackStack(null);
                    Snackbar.make(view, "Favorite", Snackbar.LENGTH_LONG);
                } else if (id == R.id.nav_home) {
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
        search = view.findViewById(R.id.edit_text);
        final IconToSimpleLine sv1 = new IconToSimpleLine();

        //Search Bar

        searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sv1.getState() == Controller.STATE_ANIM_NONE) {
                        searchView.startAnim();
                        search.setVisibility(View.VISIBLE);
                        search.bringToFront();
                        search.setIconifiedByDefault(true);
                        search.setOnCloseListener(new SearchView.OnCloseListener() {
                            @Override
                            public boolean onClose() {
                                searchView.resetAnim();
                                search.setVisibility(View.INVISIBLE);
                                return false;
                            }
                        });
                        //                    ft.replace(R.id.content_frag,new SearchFragment())
                        //                    ft.addToBackStack(null);
                    } else if (sv1.getState() == Controller.STATE_ANIM_STOP) {
                        search.setVisibility(View.INVISIBLE);
                        searchView.resetAnim();
                    }

                }

            });
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    jsonParse(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });

            return view;
        }

        private void jsonParse(String key_search){
            String baseURL = "https://api.napster.com/v2.2/search/verbose?query=";
            Log.i("data", key_search);
            String url = baseURL + key_search + "&type=track&per_type_limit=10";
            Log.i("data", url);
            JsonObjectRequest request =
                    new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {

                                        Log.i("data", "response" + response);
                                        JSONObject obj = response.getJSONObject("search");
                                        JSONObject data = obj.getJSONObject("data");
                                        JSONArray track = data.getJSONArray("tracks");
                                        ft.replace(R.id.content_frag, new SongFragment(track));



//                                    JSONObject links = track.getJSONObject("links");
//                                    String name = track.getString("name");
//                                    listSong.add(name);
//                                    Log.i("date","\n" +name);
//                                    JSONObject albums = obj.getJSONObject("albums");
//                                    String album =  albums.getString("href");
//                                    listSong.add(album);

//                                    for (int i = 0; i <track.length() ; i++) {
//
//                                    }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("apikey", "YjkxYzdlZGEtNzllMy00OGE4LTg4M2EtMGEzZTU4ODZlOGQ2");
                            return headers;
                        }
                    };
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(request);


        }


    }
