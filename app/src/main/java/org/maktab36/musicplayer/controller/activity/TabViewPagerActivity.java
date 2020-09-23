package org.maktab36.musicplayer.controller.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.maktab36.musicplayer.R;
import org.maktab36.musicplayer.controller.fragment.AlbumListFragment;
import org.maktab36.musicplayer.controller.fragment.ArtistListFragment;
import org.maktab36.musicplayer.controller.fragment.SongListFragment;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class TabViewPagerActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks {
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private TabLayout mTabLayout;
    private ViewPager2 mTabViewPager;
    private FragmentStateAdapter mViewPagerAdapter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TabViewPagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view_pager);
        String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            findViews();
            updateUI();
            connectViewPagerWithTabLayout();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_rational),
                    EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, perms);
        }

    }

    private void findViews() {
        mTabLayout = findViewById(R.id.tab_layout);
        mTabViewPager = findViewById(R.id.view_pager_tabs);
    }

    private void updateUI() {
        mViewPagerAdapter = new SongViewPagerAdapter(this);
        mTabViewPager.setAdapter(mViewPagerAdapter);

    }

    private void connectViewPagerWithTabLayout() {
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults,
                this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(requestCode==EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            findViews();
            updateUI();
            connectViewPagerWithTabLayout();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(requestCode==EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE){
            finish();
        }
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
                    return SongListFragment.newInstance(null, null);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}