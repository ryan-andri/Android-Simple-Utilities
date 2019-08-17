package rf.ryanandri.simpleutilities.utils.frags;

import android.os.Build;

import java.lang.reflect.Field;

import rf.ryanandri.simpleutilities.utils.Utils;

/**
 * Modified by Ryan Andri on 8/16/2019.
 * Base of Utilities kernel Adiutor by willi on 30.12.15.
 */
public class UtilsDeviceInfo {
    public static String getLinuxVersion() {
        String version = Utils.readFile("/proc/version", true);
        if (version != null) return version;
        return "Unknown";
    }

    public static long totalRam() {
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

    public static String getCodename() {
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
}
