package sg.edu.ntu.gg4u.pfa;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.ui.category.CategoryActivity;
import sg.edu.ntu.gg4u.pfa.ui.guide.GuideActivity;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileActivity;


public class MainActivity extends AppCompatActivity {

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
}