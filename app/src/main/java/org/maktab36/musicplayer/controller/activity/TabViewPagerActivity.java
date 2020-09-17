package org.maktab36.musicplayer.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.controller.fragment.AlbumListFragment;
import org.maktab36.musicplayer.controller.fragment.ArtistListFragment;
import org.maktab36.musicplayer.controller.fragment.SongListFragment;

public class TabViewPagerActivity extends AppCompatActivity {
    public static final String DIALOG_FRAGMENT_TAG = "activityDialog";
    private TabLayout mTabLayout;
    private ViewPager2 mTabViewPager;
    private FragmentStateAdapter mViewPagerAdapter;
//    private TaskRepository mRepository;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TabViewPagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view_pager);
//        mRepository = TaskRepository.getInstance(this);

        findViews();
        updateUI();
        setListener();

        new TabLayoutMediator(mTabLayout, mTabViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 1:
                        tab.setText("Artists");
                        break;
                    case 2:
                        tab.setText("Albums");
                        break;
                    default:
                        tab.setText("Songs");
                        break;
                }
            }
        }).attach();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        updateFragments();
    }*/

    private void findViews() {
        mTabLayout = findViewById(R.id.tab_layout);
        mTabViewPager = findViewById(R.id.view_pager_tabs);
    }

    private void setListener() {
    }

    /*public void updateFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof TaskListFragment) {
                TaskListFragment listFragment = (TaskListFragment) fragment;
                listFragment.updateUI();
            }
        }
    }*/

    private void updateUI() {
        mViewPagerAdapter = new SongViewPagerAdapter(this);
        mTabViewPager.setAdapter(mViewPagerAdapter);
    }

    private class SongViewPagerAdapter extends FragmentStateAdapter {
        public SongViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return ArtistListFragment.newInstance();
                case 2:
                    return AlbumListFragment.newInstance();
                default:
                    return SongListFragment.newInstance();
            }
        }
        @Override
        public int getItemCount() {
            return 3;
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tasks_list, menu);
        setMenuItemVisibility(menu);
        return true;
    }

    private void setMenuItemVisibility(Menu menu) {
        MenuItem item= menu.findItem(R.id.menu_admin_panel);
        UserRepository repository=UserRepository.getInstance(this);
        if(repository.getCurrentUser().equals(repository.getAdmin())){
            item.setVisible(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user_delete_all:
                showDeleteDialog();
                return true;
            case R.id.menu_user_log_out:
                startLoginActivity();
                finish();
                return true;
            case R.id.menu_admin_panel:
                startAdminActivity();
                return true;
            case R.id.menu_search:
                startSearchActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /*private void showDeleteDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_delete_all_title)
                .setMessage(R.string.dialog_delete_all_message)
                .setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mRepository.deleteAll(UserRepository.
                                getInstance(TabViewPagerActivity.this).
                                getCurrentUser().
                                getUUID());
                        updateFragments();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.show();
    }*/
    /*private void startSearchActivity(){
        Intent intent= SearchActivity.newIntent(this);
        startActivity(intent);
    }*/
}