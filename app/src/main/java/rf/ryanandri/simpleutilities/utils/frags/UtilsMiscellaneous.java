package rf.ryanandri.simpleutilities.utils.frags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rf.ryanandri.simpleutilities.utils.RootUtils;
import rf.ryanandri.simpleutilities.utils.Utils;

/**
 * Created by Ryan Andri on 8/17/2019.
 */
public class UtilsMiscellaneous {
    public static List<String> getTcpAvailableCongestions(String path) {
        return new ArrayList<>(Arrays.asList(Utils.readFile(path).split(" ")));
    }

    public static void setTcpCongestion(String path, String tcp) {
        Utils.writeFile(path, tcp, true, true);
    }

    public static String getSelinuxInfo() {
        String result = RootUtils.runCommand("getenforce");
        if (result != null) {
            return result.replaceAll("[\\[\\]]", "");
        }
        return "Unknown";
    }

    public static void setSelinux(int mode) {
        RootUtils.runCommand("setenforce "+mode);
    }
}
