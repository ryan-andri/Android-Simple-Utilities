package rf.ryanandri.basictools.fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

import rf.ryanandri.basictools.R;
import rf.ryanandri.basictools.utils.RootUtils;
import rf.ryanandri.basictools.utils.frags.UtilsDeviceInfo;

/**
 * Created by Ryan Andri on 8/17/2019.
 */
public class DeviceInfo extends Fragment {

    private TextView kernelver;
    private TextView devName;
    private TextView codename;
    private TextView dMnfcr;
    private TextView dArchi;
    private TextView dRamP;
    private TextView androidVer;
    private TextView androidCodename;
    private TextView sdkVer;
    private TextView bID;
    private TextView bFp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_info, container, false);

        kernelver = view.findViewById(R.id.kernelVersion);
        devName = view.findViewById(R.id.deviceName);
        codename = view.findViewById(R.id.deviceCodeName);
        dMnfcr = view.findViewById(R.id.deviceManufac);
        dArchi = view.findViewById(R.id.deviceArch);
        dRamP = view.findViewById(R.id.totalRam);
        androidVer = view.findViewById(R.id.androidVersion);
        androidCodename = view.findViewById(R.id.androidCodename);
        sdkVer = view.findViewById(R.id.sdkVersion);
        bID = view.findViewById(R.id.buildID);
        bFp = view.findViewById(R.id.buildFingerprint);

        new AsyncDeviceFragment(this).execute();

        return view;
    }

    private static class AsyncDeviceFragment extends AsyncTask<Void, Void, Void> {
        private WeakReference<DeviceInfo> fragDeviceWeakReference;

        private String dCodename;
        private String dName;
        private String kVersion;
        private String dManufac;
        private String dArch;
        private String dRam;
        private String andVer;
        private String andCodename;
        private String sdkVersion;
        private String buildFp;
        private String buildID;

        private AsyncDeviceFragment(DeviceInfo deviceInfo) {
            fragDeviceWeakReference = new WeakReference<>(deviceInfo);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            kVersion = UtilsDeviceInfo.getLinuxVersion();
            dName = Build.MODEL;
            dCodename = Build.DEVICE;
            dManufac = Build.MANUFACTURER;
            dArch = RootUtils.runCommand("uname -m");
            dRam = String.valueOf(UtilsDeviceInfo.totalRam());
            andVer = Build.VERSION.RELEASE;
            andCodename = UtilsDeviceInfo.getCodename();
            sdkVersion = String.valueOf(Build.VERSION.SDK_INT);
            buildID = Build.ID;
            buildFp = Build.FINGERPRINT;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DeviceInfo dFrag = fragDeviceWeakReference.get();

            dFrag.kernelver.setText(kVersion);
            dFrag.devName.setText(dName);
            dFrag.codename.setText(dCodename);
            dFrag.dMnfcr.setText(dManufac);
            dFrag.dArchi.setText(dArch);
            dFrag.dRamP.setText(dRam);
            dFrag.androidVer.setText(andVer);
            dFrag.androidCodename.setText(andCodename);
            dFrag.sdkVer.setText(sdkVersion);
            dFrag.bID.setText(buildID);
            dFrag.bFp.setText(buildFp);
        }
    }
}
