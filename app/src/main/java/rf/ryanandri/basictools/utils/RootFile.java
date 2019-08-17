package rf.ryanandri.basictools.utils;

/**
 * Created by willi on 30.12.15.
 */
public class RootFile {

    private final String mFile;
    private RootUtils.SU mSU;

    public RootFile(String file) {
        mFile = file;
        mSU = RootUtils.getSU();
    }

    public RootFile(String file, RootUtils.SU su) {
        mFile = file;
        mSU = su;
    }

    public void write(String text, boolean append) {
        String[] array = text.split("\\r?\\n");
        if (!append) delete();
        for (String line : array) {
            mSU.runCommand("echo '" + line + "' >> " + mFile);
        }
        RootUtils.chmod(mFile, "755", mSU);
    }

    public void delete() {
        mSU.runCommand("rm -r '" + mFile + "'");
    }

    public boolean exists() {
        String output = mSU.runCommand("[ -e '" + mFile + "' ] && echo true");
        return output != null && output.equals("true");
    }

    public String readFile() {
        return mSU.runCommand("cat '" + mFile + "'");
    }

    @Override
    public String toString() {
        return mFile;
    }
}
