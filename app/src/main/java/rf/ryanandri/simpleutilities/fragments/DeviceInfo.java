package rf.ryanandri.simpleutilities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rf.ryanandri.simpleutilities.R;
import rf.ryanandri.simpleutilities.utils.frags.UtilsDeviceInfo;

/**
 * Created by Ryan Andri on 8/17/2019.
 */
public class DeviceInfo extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_info, container, false);
        setView(view);
        return view;
    }

    private void setView(View view) {
        UtilsDeviceInfo utilInstance = UtilsDeviceInfo.getInstance();
        TextView kernelver = view.findViewById(R.id.kernelVersion);
        TextView devName = view.findViewById(R.id.deviceName);
        TextView codename = view.findViewById(R.id.deviceCodeName);
        TextView dMnfcr = view.findViewById(R.id.deviceManufac);
        TextView dArchi = view.findViewById(R.id.deviceArch);
        TextView dRamP = view.findViewById(R.id.totalRam);
        TextView androidVer = view.findViewById(R.id.androidVersion);
        TextView androidCodename = view.findViewById(R.id.androidCodename);
        TextView sdkVer = view.findViewById(R.id.sdkVersion);
        TextView bFp = view.findViewById(R.id.buildFingerprint);

        kernelver.setText(utilInstance.linuxVersion);
        devName.setText(utilInstance.deviceName);
        codename.setText(utilInstance.deviceCodeName);
        dMnfcr.setText(utilInstance.deviceManufacture);
        dArchi.setText(utilInstance.deviceArch);
        dRamP.setText(utilInstance.totalRam);
        androidVer.setText(utilInstance.androidVersion);
        androidCodename.setText(utilInstance.androidCodename);
        sdkVer.setText(utilInstance.androidSdk);
        TextView bID = view.findViewById(R.id.buildID);
        bID.setText(utilInstance.buildId);
        bFp.setText(utilInstance.buildFingerPrint);
    }
}
