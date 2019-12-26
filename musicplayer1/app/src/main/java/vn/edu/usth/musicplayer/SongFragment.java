package vn.edu.usth.musicplayer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SongFragment extends Fragment {
    private JSONArray listSong;
    public SongFragment(JSONArray a){
        listSong = a;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scroll_view,container,false);
        if (listSong ==null){

        }
        for (int i = 0; i < listSong.length(); i++) {
            try {
                JSONObject obj = listSong.getJSONObject(i);
                obj
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("ngkn", "" + listSong);

        }

        return view;
    }
}

