package com.example.restaurantmanagement.views.user.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantmanagement.databinding.FragmentUserAddTableBookingViewBinding;
import com.example.restaurantmanagement.domain.models.core.TableBooking;
import com.example.restaurantmanagement.utils.DatePickerFragment;
import com.example.restaurantmanagement.utils.TimePickerFragment;
import com.example.restaurantmanagement.viewModels.ViewModelProviderFactory;
import com.example.restaurantmanagement.viewModels.table.TableBookingViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link DaggerFragment} subclass.
 * Use the {@link UserAddTableBookingView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAddTableBookingView extends DaggerFragment {
    private FragmentUserAddTableBookingViewBinding binding;
    private TableBookingViewModel viewModel;
    private NavController navController;

    @Inject
    ViewModelProviderFactory providerFactory;

    public UserAddTableBookingView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserAddTableBookingView.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAddTableBookingView newInstance() {
        return new UserAddTableBookingView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this,providerFactory).get(TableBookingViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserAddTableBookingViewBinding.inflate(inflater,container,false);

        binding.datePick.setOnClickListener(v -> {
            initDateTimePicker();
            Log.d("DatePicker","Date picker clicked");
        });

        binding.timePick.setOnClickListener(v->{
            initTimePicker();
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        TableBooking booking = new TableBooking();

        binding.confirm.setOnClickListener(v->{
            int tableNo = Integer.parseInt(binding.tableNo.getText().toString().trim());
            if(tableNo > 0 && validDateAndTime()){
                String date = binding.datePick.getText().toString().trim();
                String time = binding.timePick.getText().toString().trim();
                booking.setTable(binding.tableNo.getText().toString().trim());
                booking.setTime(date.concat(" ").concat(time));
                viewModel.addTableBooking(booking).observe(getViewLifecycleOwner(),tableBookingResource -> {
                    if(tableBookingResource != null){
                        switch (tableBookingResource.status){
                            case SUCCESS:
                                Toast.makeText(getContext(),"Table successfully Booked",Toast.LENGTH_SHORT).show();
                                navController.popBackStack();
                                break;
                            case ERROR:
                                Toast.makeText(getContext(),"Sorry can't add",Toast.LENGTH_SHORT).show();
                                break;
                            case LOADING:
                                break;
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void initDateTimePicker(){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getParentFragmentManager(),"DateTime");
        datePicker.isSet.observe(getViewLifecycleOwner(),dataChange->{
            if(dataChange){
                String date = datePicker.day+"/"+datePicker.month+"/"+datePicker.year;
                binding.datePick.setText(date);
            }
        });
    }

    private void initTimePicker(){
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.show(getParentFragmentManager(),"timePicker");
        timePicker.isSet.observe(getViewLifecycleOwner(),dataChange->{
            if(dataChange){
                String time = timePicker.hourOfDay+":"+timePicker.minute;
                binding.timePick.setText(time);
            }
        });
    }

    private boolean validDateAndTime(){
        return !binding.datePick.getText().toString().trim().isEmpty()
                && !binding.timePick.getText().toString().trim().isEmpty();
    }
}