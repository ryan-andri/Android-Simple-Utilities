package rf.ryanandri.simpleutilities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rf.ryanandri.simpleutilities.R;

/**
 * Created by Ryan Andri on 8/17/2019.
 */
public class DisplayCalibration extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_calibration, container, false);

        return view;
    }
}