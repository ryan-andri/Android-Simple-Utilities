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
    private static final String TCP_DIR = "/proc/sys/net/ipv4";

    public static List<String> listTcp;
    public static String SelinuxStatus;

    public static void init() {
        listTcp = getTcpAvailableCongestions();
        SelinuxStatus = getSelinuxInfo();
    }

    private static List<String> getTcpAvailableCongestions() {
        String TCP_AVAILABLE_CONGESTIONS = TCP_DIR + "/tcp_available_congestion_control";
        return new ArrayList<>(Arrays.asList(Utils.readFile(TCP_AVAILABLE_CONGESTIONS).split(" ")));
    }

    public static void setTcpCongestion(String tcp) {
        String TCP_CONTROL = TCP_DIR + "/tcp_congestion_control";
        Utils.writeFile(TCP_CONTROL, tcp, true, true);
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
