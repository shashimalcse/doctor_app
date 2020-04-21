package com.s17131890.carelite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.s17131890.carelite.databinding.FragmentPatientBinding;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends Fragment {

    SharedViewModel model;
    FragmentPatientBinding binding;
    RecyclerView recyclerView;
    List<CheckupHistory> historyList;
    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPatientBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        navController = Navigation.findNavController(view);
        recyclerView=binding.recycleview2;
        historyList = Objects.requireNonNull(model.currentPatient.getHistory());
        Log.d("HISTORIES",Integer.toString(historyList.size()));
        PatientHistoryAdapter patientHistoryAdapter = new PatientHistoryAdapter(getContext(),historyList,model);

        recyclerView.setAdapter(patientHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_patientFragment_to_checkupHistoryFragment);
            }
        });

    }
}
