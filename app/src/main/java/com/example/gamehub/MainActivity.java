package com.example.gamehub;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gamehub.fragment.GamesFragment;
import com.example.gamehub.fragment.HomeFragment;
import com.example.gamehub.fragment.LibraryFragment;
import com.example.gamehub.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FragmentManager fragmentManager = getSupportFragmentManager();
    private LinearLayout home_layout, games_layout, libary_layout, profile_layout;
    private ImageView iv_home, iv_games, iv_library, iv_profile;
    private TextView tv_home, tv_games, tv_library, tv_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        HomeFragment homeFragment = new HomeFragment();
        if (!(fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName()) instanceof HomeFragment)) {
            fragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment, HomeFragment.class.getSimpleName()).commit();
        }
        home_layout.setOnClickListener(this);
        games_layout.setOnClickListener(this);
        libary_layout.setOnClickListener(this);
        profile_layout.setOnClickListener(this);

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                updateActiveFragment();
            }
        });
        updateActiveFragment();
    }

    // START : BUTTOM NAVIGATION
    @Override
    public void onClick(View v) {
        if (v == home_layout) {
            onClickMenu(home_layout);
        } else if (v == games_layout) {
            onClickMenu(games_layout);
        } else if (v == libary_layout) {
            onClickMenu(libary_layout);
        } else if (v == profile_layout) {
            onClickMenu(profile_layout);
        }
    }
    public void isActiveHomeMenu(boolean isActive) {
        if (isActive) {
            iv_home.setAlpha(1.0f);
            tv_home.setAlpha(1.0f);
        } else {
            iv_home.setAlpha(0.5f);
            tv_home.setAlpha(0.5f);
        }
    }
    public void isActiveGamesMenu(boolean isActive) {
        if (isActive) {
            iv_games.setAlpha(1.0f);
            tv_games.setAlpha(1.0f);
        } else {
            iv_games.setAlpha(0.5f);
            tv_games.setAlpha(0.5f);
        }
    }
    public void isActiveLibraryMenu(boolean isActive) {
        if (isActive) {
            iv_library.setAlpha(1.0f);
            tv_library.setAlpha(1.0f);
        } else {
            iv_library.setAlpha(0.5f);
            tv_library.setAlpha(0.5f);
        }
    }
    public void isActiveProfileMenu(boolean isActive) {
        if (isActive) {
            iv_profile.setAlpha(1.0f);
            tv_profile.setAlpha(1.0f);
        } else {
            iv_profile.setAlpha(0.5f);
            tv_profile.setAlpha(0.5f);
        }
    }
    private void onClickMenu(LinearLayout linearLayout) {
        Fragment selectedFragment;
        String fragmentName = "";
        if (linearLayout == home_layout) {
            selectedFragment = new HomeFragment();
            fragmentName = HomeFragment.class.getSimpleName();
            isActiveHomeMenu(true);
            isActiveGamesMenu(false);
            isActiveLibraryMenu(false);
            isActiveProfileMenu(false);
        } else if (linearLayout == games_layout) {
            selectedFragment = new GamesFragment();
            fragmentName = GamesFragment.class.getSimpleName();
            isActiveHomeMenu(false);
            isActiveGamesMenu(true);
            isActiveLibraryMenu(false);
            isActiveProfileMenu(false);
        } else if (linearLayout == libary_layout) {
            selectedFragment = new LibraryFragment();
            fragmentName = LibraryFragment.class.getSimpleName();
            isActiveHomeMenu(false);
            isActiveGamesMenu(false);
            isActiveLibraryMenu(true);
            isActiveProfileMenu(false);
        } else if (linearLayout == profile_layout) {
            selectedFragment = new ProfileFragment();
            fragmentName = ProfileFragment.class.getSimpleName();
            isActiveHomeMenu(false);
            isActiveGamesMenu(false);
            isActiveLibraryMenu(false);
            isActiveProfileMenu(true);
        } else {
            return;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, selectedFragment, fragmentName)
                .addToBackStack(fragmentName)
                .commit();
    }
    private void initView() {
        iv_home = findViewById(R.id.iv_home);
        tv_home = findViewById(R.id.tv_home);
        iv_games = findViewById(R.id.iv_games);
        tv_games = findViewById(R.id.tv_games);
        iv_library = findViewById(R.id.iv_library);
        tv_library = findViewById(R.id.tv_library);
        iv_profile = findViewById(R.id.iv_profileNav);
        tv_profile = findViewById(R.id.tv_profileNav);
        home_layout = findViewById(R.id.home_layout);
        games_layout = findViewById(R.id.games_layout);
        libary_layout = findViewById(R.id.library_layout);
        profile_layout = findViewById(R.id.profile_layout);

        isActiveHomeMenu(true);
        isActiveGamesMenu(false);
        isActiveLibraryMenu(false);
        isActiveProfileMenu(false);
    }
    private void updateActiveFragment() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_layout);
        if (currentFragment instanceof HomeFragment) {
            isActiveHomeMenu(true);
            isActiveGamesMenu(false);
            isActiveLibraryMenu(false);
            isActiveProfileMenu(false);
        } else if (currentFragment instanceof GamesFragment) {
            isActiveHomeMenu(false);
            isActiveGamesMenu(true);
            isActiveLibraryMenu(false);
            isActiveProfileMenu(false);
        } else if (currentFragment instanceof LibraryFragment) {
            isActiveHomeMenu(false);
            isActiveGamesMenu(false);
            isActiveLibraryMenu(true);
            isActiveProfileMenu(false);
        } else if (currentFragment instanceof ProfileFragment) {
            isActiveHomeMenu(false);
            isActiveGamesMenu(false);
            isActiveLibraryMenu(false);
            isActiveProfileMenu(true);
        }
    }
    // END : BUTTOM NAVIGATION
}