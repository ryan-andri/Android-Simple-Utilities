package rf.ryanandri.simpleutilities.fragments;

import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rf.ryanandri.simpleutilities.R;
import rf.ryanandri.simpleutilities.utils.frags.UtilsMiscellaneous;

/**
 * Created by Ryan Andri on 8/17/2019.
 */
public class Miscellaneous extends Fragment implements
        AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private final String TCP_DIR = "/proc/sys/net/ipv4";

    private Spinner spnTcp;
    private TextView tvSelinuxStatus;
    private Switch swSelinux;

    private View fView;

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

        // set id for some global var
        fView = view;
        spnTcp = view.findViewById(R.id.spnTcp);
        swSelinux = view.findViewById(R.id.swSelinux);
        tvSelinuxStatus = view.findViewById(R.id.selinuxStatus);

        // set Listener
        spnTcp.setOnItemSelectedListener(this);
        swSelinux.setOnCheckedChangeListener(this);

        new AsyncMiscellaneousFragment(this).execute();

        return view;
    }

    private static class AsyncMiscellaneousFragment extends AsyncTask<Void, Void, Void> {
        private WeakReference<Miscellaneous> fragmentWeakReference;

        private List<String> listTcp = new ArrayList<>();
        private String selinuxMode;

        private AsyncMiscellaneousFragment(Miscellaneous miscellaneous) {
            fragmentWeakReference = new WeakReference<>(miscellaneous);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Miscellaneous miscAsync = fragmentWeakReference.get();

            String TCP_AVAILABLE_CONGESTIONS = miscAsync.TCP_DIR + "/tcp_available_congestion_control";

            listTcp = UtilsMiscellaneous.getTcpAvailableCongestions(TCP_AVAILABLE_CONGESTIONS);
            selinuxMode = UtilsMiscellaneous.getSelinuxInfo();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Miscellaneous miscAsync = fragmentWeakReference.get();

            // set adapter TCP Spinner
            ArrayAdapter<String> arrTcp = new ArrayAdapter<>(miscAsync.fView.getContext(),
                    R.layout.simple_spinner_layout, listTcp);
            arrTcp.setDropDownViewResource(R.layout.simple_dropdown_layout);
            miscAsync.spnTcp.setAdapter(arrTcp);

            miscAsync.swSelinux.setChecked(selinuxMode.equals("Enforcing"));
            miscAsync.tvSelinuxStatus.setText(selinuxMode);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!init_listener) {
            init_listener = true;
        } else {
            if (adapterView.getId() == R.id.spnTcp) {
                String tcp = adapterView.getItemAtPosition(i).toString();
                UtilsMiscellaneous.setTcpCongestion(TCP_DIR + "/tcp_congestion_control", tcp);
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
