package rf.ryanandri.basictools.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by willi on 30.12.15.
 */
public class RootUtils {

    private static SU sInstance;

    public static boolean rootAccess() {
        SU su = getSU();
        su.runCommand("echo /testRoot/");
        return !su.mDenied;
    }

    public static boolean busyboxInstalled() {
        return existBinary("busybox") || existBinary("toybox");
    }

    private static boolean existBinary(String binary) {
        String paths;
        if (System.getenv("PATH") != null) {
            paths = System.getenv("PATH");
        } else {
            paths = "/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin";
        }
        for (String path : paths.split(":")) {
            if (!path.endsWith("/")) path += "/";
            if (Utils.existFile(path + binary, false) || Utils.existFile(path + binary)) {
                return true;
            }
        }
        return false;
    }

    public static void chmod(String file, String permission, SU su) {
        su.runCommand("chmod " + permission + " " + file);
    }

    public static void closeSU() {
        if (sInstance != null) sInstance.close();
        sInstance = null;
    }

    public static String runCommand(String command) {
        return getSU().runCommand(command);
    }

    public static SU getSU() {
        if (sInstance == null || sInstance.mClosed || sInstance.mDenied) {
            if (sInstance != null && !sInstance.mClosed) {
                sInstance.close();
            }
            sInstance = new SU();
        }
        return sInstance;
    }

    /*
     * Based on AndreiLux's SU code in Synapse
     * https://github.com/AndreiLux/Synapse/blob/master/src/main/java/com/af/synapse/utils/Utils.java#L238
     */
    public static class SU {

        private Process mProcess;
        private BufferedWriter mWriter;
        private BufferedReader mReader;
        private boolean mClosed;
        public boolean mDenied;
        private boolean mFirstTry;

        private ReentrantLock mLock = new ReentrantLock();

        public SU() {
            this(true, false);
        }

        public SU(boolean root, boolean log) {
            try {
                mFirstTry = true;
                mProcess = Runtime.getRuntime().exec(root ? "su" : "sh");
                mWriter = new BufferedWriter(new OutputStreamWriter(mProcess.getOutputStream()));
                mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            } catch (IOException ignored) {
                mDenied = true;
                mClosed = true;
            }
        }

        public String runCommand(final String command) {
            if (mClosed) return "";
            try {
                mLock.lock();
                StringBuilder sb = new StringBuilder();
                String callback = "/shellCallback/";
                mWriter.write(command + "\necho " + callback + "\n");
                mWriter.flush();

                int i;
                char[] buffer = new char[256];
                while (true) {
                    sb.append(buffer, 0, mReader.read(buffer));
                    if ((i = sb.indexOf(callback)) > -1) {
                        sb.delete(i, i + callback.length());
                        break;
                    }
                }
                mFirstTry = false;
                return sb.toString().trim();
            } catch (IOException e) {
                mClosed = true;
                e.printStackTrace();
                if (mFirstTry) mDenied = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                mDenied = true;
            } catch (Exception e) {
                e.printStackTrace();
                mDenied = true;
            } finally {
                mLock.unlock();
            }
            return null;
        }

        public void close() {
            try {
                try {
                    mLock.lock();
                    if (mWriter != null) {
                        mWriter.write("exit\n");
                        mWriter.flush();
                        mWriter.close();
                    }
                    if (mReader != null) {
                        mReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (mProcess != null) {
                    try {
                        mProcess.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProcess.destroy();
                }
            } finally {
                mLock.unlock();
                mClosed = true;
            }
        }
    }
}