package vn.edu.usth.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    private ViewPager pager;
    private Adapter adapter;

    private View parent_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPagerControl();
    }

    private void addPagerControl() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new Adapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    public void controlClick(View v) {
        parent_view = this.findViewById(R.id.parent_view);
        int id = v.getId();
        switch (id) {
            case R.id.btn_repeat: {
                toggleButtonColor((ImageButton) v);
                Snackbar.make(parent_view, "Repeat", Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_shuffle: {
                toggleButtonColor((ImageButton) v);
                Snackbar.make(parent_view, "Shuffle", Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_prev: {
                Snackbar.make(parent_view, "Previous", Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_next: {
                Snackbar.make(parent_view, "Next", Snackbar.LENGTH_SHORT).show();
                break;
            }
        }
    }
    private boolean toggleButtonColor(ImageButton bt) {
        String selected = (String) bt.getTag(bt.getId());
        if (selected != null) {
            bt.setColorFilter(getResources().getColor(R.color.colorBrick), PorterDuff.Mode.SRC_ATOP);
            bt.setTag(bt.getId(), null);
            return false;
        } else {
            bt.setTag(bt.getId(), "selected");
            bt.setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
            return true;
        }
    }

}
