package rf.ryanandri.simpleutilities.utils.frags;

import android.os.Build;

import java.lang.reflect.Field;

import rf.ryanandri.simpleutilities.utils.RootUtils;
import rf.ryanandri.simpleutilities.utils.Utils;

/**
 * Modified by Ryan Andri on 8/16/2019.
 * Base of Utilities kernel Adiutor by willi on 30.12.15.
 */
public class UtilsDeviceInfo {
    private static UtilsDeviceInfo deviceInfoInstance;

    public String linuxVersion;
    public String deviceName;
    public String deviceCodeName;
    public String deviceManufacture;
    public String deviceArch;
    public String androidVersion;
    public String androidCodename;
    public String androidSdk;
    public String buildFingerPrint;
    public String buildId;
    public String totalRam;

    public static UtilsDeviceInfo getInstance() {
        if (deviceInfoInstance == null) {
            deviceInfoInstance = new UtilsDeviceInfo();
        }
        return deviceInfoInstance;
    }

    private UtilsDeviceInfo() {
        linuxVersion = getLinuxVersion();
        deviceName = Build.MODEL;
        deviceCodeName = Build.DEVICE;
        deviceManufacture = Build.MANUFACTURER;
        androidVersion = Build.VERSION.RELEASE;
        androidCodename = getCodename();
        androidSdk = String.valueOf(Build.VERSION.SDK_INT);
        buildFingerPrint = Build.FINGERPRINT;
        buildId = Build.ID;
        deviceArch = RootUtils.runCommand("uname -m");
        totalRam = String.valueOf(totalRam());
    }

    private String getLinuxVersion() {
        String version = Utils.readFile("/proc/version", true);

        if (version != null)
            return version;

        return "No Data";
    }

    private String getCodename() {
        String codeName = "";
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;
            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException | IllegalAccessException | NullPointerException ignored) {
            }
            if (fieldValue == Build.VERSION.SDK_INT) {
                codeName = fieldName;
                break;
            }
        }
        return codeName;
    }

    private long totalRam() {
        try {
            String meminfo = Utils.readFile("/proc/meminfo");
            for (String line : meminfo.split("\\r?\\n")) {
                if (line.startsWith("MemTotal")) {
                    String result = line.split(":")[1].trim();
                    return Long.parseLong(result.replaceAll("[^\\d]", "")) / 1024L;
                }
            }
        } catch (Exception ignored) {
        }
        return 0;
    }
}
