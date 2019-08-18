package rf.ryanandri.simpleutilities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rf.ryanandri.simpleutilities.R;
import rf.ryanandri.simpleutilities.utils.frags.UtilsMiscellaneous;

/**
 * Created by Ryan Andri on 8/17/2019.
 */
public class Miscellaneous extends Fragment implements
        AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private TextView tvSelinuxStatus;

    /*
     * work arround
     * https://stackoverflow.com/questions/5624825/spinner-onitemselected-executes-inappropriately/5918177
     */
    private boolean init_listener = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_miscellaneous, container, false);

        Spinner spnTcp = view.findViewById(R.id.spnTcp);
        Switch swSelinux = view.findViewById(R.id.swSelinux);
        tvSelinuxStatus = view.findViewById(R.id.selinuxStatus);

        // set adapter TCP Spinner
        ArrayAdapter<String> arrTcp = new ArrayAdapter<>(view.getContext(),
                R.layout.simple_spinner_layout, UtilsMiscellaneous.listTcp);
        arrTcp.setDropDownViewResource(R.layout.simple_dropdown_layout);
        spnTcp.setAdapter(arrTcp);

        String selnx = UtilsMiscellaneous.SelinuxStatus;
        swSelinux.setChecked(selnx.equals("Enforcing"));
        tvSelinuxStatus.setText(selnx);

        // set Listener
        spnTcp.setOnItemSelectedListener(this);
        swSelinux.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!init_listener) {
            init_listener = true;
        } else {
            if (adapterView.getId() == R.id.spnTcp) {
                String tcp = adapterView.getItemAtPosition(i).toString();
                UtilsMiscellaneous.setTcpCongestion(tcp);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Nothing TODO
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (compoundButton.getId() == R.id.swSelinux) {
            UtilsMiscellaneous.setSelinux(checked ? 1 : 0);
            tvSelinuxStatus.setText(UtilsMiscellaneous.getSelinuxInfo());
        }
    }
}
