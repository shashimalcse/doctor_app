package com.s17131890.carelite;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.s17131890.carelite.databinding.FragmentCheckupHistoryBinding;
import com.s17131890.carelite.databinding.FragmentPatientBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckupHistoryFragment extends Fragment {

    FragmentCheckupHistoryBinding binding;
    SharedViewModel model;
    NavController navController;
    EditText Temp;
    EditText Pressure;
    EditText HeartRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckupHistoryBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        navController = Navigation.findNavController(view);

        binding.patientName2.setText(model.currentPatient.getName());

        Temp= (EditText) binding.temp;
        Pressure= (EditText) binding.pressure;
        HeartRate=(EditText) binding.heartrate;

        binding.time.setInputType(InputType.TYPE_NULL);
        binding.date.setInputType(InputType.TYPE_NULL);


        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(binding.date);
            }
        });
        
        binding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(binding.time);
            }
        });

        binding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_checkupHistoryFragment_to_patientFragment);
            }
        });

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = Temp.getText().toString();
                String pressure= Pressure.getText().toString();
                String heartrate = HeartRate.getText().toString();
                String Date = binding.date.getText().toString();
                String Time = binding.time.getText().toString();
                if(!temp.equals("") && !pressure.equals("") && !heartrate.equals("") && !Date.equals("") && !Time.equals("")){
                    try {
                        model.updatePatient(new CheckupHistory(Float.parseFloat(temp),pressure,Integer.parseInt(heartrate),Date,Time));
                        navController.navigate(R.id.action_checkupHistoryFragment_to_patientFragment);

                    }
                    catch (Exception e){
                        Toast.makeText(getContext(),"Not valid Values",Toast.LENGTH_SHORT).show();
                    }



                }
                else {
                    Toast.makeText(getContext(),"Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void showTimeDialog(EditText time) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
                time.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new TimePickerDialog(getActivity(),onTimeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();

    }

    private void showDateDialog(EditText date) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                date.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(getActivity(),onDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();


    }
}
