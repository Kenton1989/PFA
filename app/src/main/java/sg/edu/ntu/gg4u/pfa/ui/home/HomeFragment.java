package sg.edu.ntu.gg4u.pfa.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.disposables.CompositeDisposable;
import sg.edu.ntu.gg4u.pfa.R;

import sg.edu.ntu.gg4u.pfa.MainActivity;
import sg.edu.ntu.gg4u.pfa.addIncome;
import sg.edu.ntu.gg4u.pfa.ui.ViewModelFactory;
import sg.edu.ntu.gg4u.pfa.ui.profile.ProfileViewModel;

public class HomeFragment extends Fragment {

    ListView list;
    String[] cat_in_list = {
            "Food",
            "Entertainment",
            "leisure",
            "Transportation",
            "Others"


    } ;

    String[] amount_in_list = {
            "1000",
            "2000",
            "5000",
            "6000",
            "7000"
    } ;

    private ViewModelFactory mViewModelFactory;

    private HomeViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();


}