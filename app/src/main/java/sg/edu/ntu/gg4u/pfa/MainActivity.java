package sg.edu.ntu.gg4u.pfa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDateTime;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sg.edu.ntu.gg4u.pfa.persistence.Category.Category;
import sg.edu.ntu.gg4u.pfa.persistence.Dataloader;
import sg.edu.ntu.gg4u.pfa.persistence.Record.Record;
import sg.edu.ntu.gg4u.pfa.persistence.Target.Target;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.Gender;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.UserProfile;
import sg.edu.ntu.gg4u.pfa.ui.InitViewModel;
import sg.edu.ntu.gg4u.pfa.ui.Injection;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.category.CategoryActivity;
import sg.edu.ntu.gg4u.pfa.persistence.UserProfile.JobField;
import sg.edu.ntu.gg4u.pfa.ui.guide.GuideActivity;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileActivity;


public class MainActivity extends AppCompatActivity {

    private InitViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Database stuff
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory)
                .get(InitViewModel.class);

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

    private void printToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void open(Class<?> toOpen) {
        Intent intent = new Intent(this, toOpen);
        startActivity(intent);
    }

    private SharedPreferences mPreferences;
    private final String sharedPrefFile = "sg.edu.ntu.gg4u.pfa.sharedPrefFile";
    private final String IS_FIRST_LAUNCH_KEY = "sg.edu.ntu.gg4u.pfa.IS_FIRST_LAUNCH";
    @RequiresApi(api = Build.VERSION_CODES.R)
    private void loadPreferenceFile() {
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void whenFirstLaunch() {
        // Do manual insertion...
        Log.d("MainActivity", "Start init database");
        initDataBase();

        open(GuideActivity.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void initDataBase() {
        updateGovLocalDatabase();
        insertUserProfile();
        insertCategory();
        insertRecord();
        insertTarget();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertTarget() {
        insertSingleTarget(new Target("Clothing", 150));
        insertSingleTarget(new Target("Food", 360));
    }

    private void insertSingleTarget(Target target) {
        mDisposable.add(mViewModel.updateTarget(target)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void insertCategory() {
        insertSingleCategory(new Category("Food"));
        insertSingleCategory(new Category("Transportation"));
        insertSingleCategory(new Category("Clothing"));
        insertSingleCategory(new Category("Entertainment"));

//        Category from government dataset
//        for (String categoryName : Dataloader.categorySet) {
//            mDisposable.add(mViewModel.updateCategory(new Category(categoryName))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe());
//        }
    }
    private void insertSingleCategory(Category category) {
        mDisposable.add(mViewModel.updateCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    private void insertRecord() {
        int month = LocalDateTime.now().getMonth().getValue();
        insertSingleRecord(new Record(LocalDateTime.of(2020, month-1, 13, 13, 12),
                "Food", 5.30));
        insertSingleRecord(new Record(LocalDateTime.of(2020,month - 1,28,18,50),
                "Food", 4.80));
        insertSingleRecord(new Record(LocalDateTime.of(2020,month,1,12,9),
                "Food", 3.30));
        insertSingleRecord(new Record(LocalDateTime.of(2020,month,3,9,45),
                "Food", 2.40));
        insertSingleRecord(new Record(LocalDateTime.of(2020,month,24,9,10),
                "Transportation", 16.20));
        insertSingleRecord(new Record(LocalDateTime.of(2020, month - 1, 4, 22, 10),
                "Transportation", 2.70));
        insertSingleRecord(new Record(LocalDateTime.of(2020, month - 1, 17, 15, 39),
                "Transportation", 7.30));
        insertSingleRecord(new Record(LocalDateTime.of(2020, month,16, 17, 30),
                "Clothing", 58.80));
        insertSingleRecord(new Record(LocalDateTime.of(2020, month - 1,24, 10, 1),
                "Entertainment", 16.80));
    }

    private void insertSingleRecord(Record record) {
        mDisposable.add(mViewModel.updateRecord(record)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    private void insertUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setName("Wen Zhengyu");
        userProfile.setAge(19);
        userProfile.setFamilySize(5);
        userProfile.setGender(Gender.MALE);
        userProfile.setJobField(JobField.NOT_WORKING);
        userProfile.setIncome(500.00);

        mDisposable.add(mViewModel.updateUserProfile(userProfile)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> Log.d("MainActivity", "Database Initialization Done...")));
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void updateGovLocalDatabase() {
        try {
            Dataloader.loadAllData();
        } catch (IOException e) {
            printToast("Failed to load data from data.gov.sg");
        } catch (JSONException e) {
            printToast("Failed to parse data from data.gov.sg");
        }
    }
}