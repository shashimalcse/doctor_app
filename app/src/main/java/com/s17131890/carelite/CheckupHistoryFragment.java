package com.s17131890.carelite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.s17131890.carelite.databinding.FragmentCheckupHistoryBinding;
import com.s17131890.carelite.databinding.FragmentPatientBinding;


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

        Temp= (EditText) binding.temp;
        Pressure= (EditText) binding.pressure;
        HeartRate=(EditText) binding.heartrate;

        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = Temp.getText().toString();
                String pressure= Pressure.getText().toString();
                String heartrate = HeartRate.getText().toString();
                if(!temp.equals("") && !pressure.equals("") && !heartrate.equals("")){
                    model.updatePatient(new CheckupHistory(Float.parseFloat(temp),Integer.parseInt(pressure),Integer.parseInt(heartrate)));


                }
                else {
                    Toast.makeText(getContext(),"Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
