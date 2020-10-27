package sg.edu.ntu.gg4u.pfa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.reactivex.disposables.CompositeDisposable;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfileDao;
import sg.edu.ntu.gg4u.pfa.ui.InitViewModel;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.category.CategoryActivity;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.JobField;
import sg.edu.ntu.gg4u.pfa.ui.guide.GuideActivity;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileActivity;

import sg.edu.ntu.gg4u.pfa.ui.category.CategoryActivity;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileActivity;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;


public class MainActivity extends AppCompatActivity {

    private InitViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_record,
                R.id.navigation_target, R.id.navigation_report)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        loadPreferenceFile();
        checkPreferenceFile();
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferenceFile();

        // Database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(InitViewModel.class);

        Log.d("MainActivity", JobField.OTHERS.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_guide:
                open(GuideActivity.class);
                break;
            case R.id.menu_profile:
                open(ProfileActivity.class);
                break;
            case R.id.menu_category:
                open(CategoryActivity.class);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void open(Class<?> toOpen) {
        Intent intent = new Intent(this, toOpen);
        startActivity(intent);
    }

    private SharedPreferences mPreferences;
    private final String sharedPrefFile = "sg.edu.ntu.gg4u.pfa.sharedPrefFile";
    private final String IS_FIRST_LAUNCH_KEY = "sg.edu.ntu.gg4u.pfa.IS_FIRST_LAUNCH";
    private void loadPreferenceFile() {
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    private void checkPreferenceFile() {
        if (mPreferences.getBoolean(IS_FIRST_LAUNCH_KEY, true)) {
            whenFirstLaunch();
        }
    }

    private void savePreferenceFile() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(IS_FIRST_LAUNCH_KEY, false);
        preferencesEditor.apply();
    }

    private void whenFirstLaunch() {
        open(GuideActivity.class);
        
        // Do manual insertion...
        InitDataBase();
    }

    private void InitDataBase() {

    }
}