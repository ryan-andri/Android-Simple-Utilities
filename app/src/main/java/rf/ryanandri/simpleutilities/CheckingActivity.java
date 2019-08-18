package rf.ryanandri.simpleutilities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.lang.ref.WeakReference;

import rf.ryanandri.simpleutilities.utils.RootUtils;
import rf.ryanandri.simpleutilities.utils.frags.UtilsDeviceInfo;
import rf.ryanandri.simpleutilities.utils.frags.UtilsMiscellaneous;

/**
 * Created by Ryan Andri on 8/16/2019.
 */
public class CheckingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Sprite m3Bounce = new ThreeBounce();
            setContentView(R.layout.activity_checking);
            ProgressBar loading = findViewById(R.id.loadingCheck);
            loading.setIndeterminateDrawable(m3Bounce);
            loading.setIndeterminate(true);
            new AsyncCheck(this).execute();
        }
    }

    private static class AsyncCheck extends AsyncTask<Void, Void, Void> {
        private WeakReference<CheckingActivity> checkingActivityWeakReference;
        private boolean hasRoot, hasBusybox;

        private AsyncCheck(CheckingActivity checkingActivity) {
            checkingActivityWeakReference = new WeakReference<>(checkingActivity);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            CheckingActivity checkingActivity = checkingActivityWeakReference.get();

            if (!hasRoot || !hasBusybox) {
                Intent intent = new Intent(checkingActivity, BlankActivity.class);
                intent.putExtra("nosu_or_nobb", hasRoot ? "Busybox not installed!" : "No Root Access!");
                checkingActivity.startActivity(intent);
                checkingActivity.finish();
                return;
            }

            checkingActivity.startActivity(new Intent(checkingActivity, MainActivity.class));
            checkingActivity.finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            hasRoot = RootUtils.rootAccess();
            if (hasRoot) {
                hasBusybox = RootUtils.busyboxInstalled();
                if (hasBusybox) {
                    UtilsDeviceInfo.getInstance();
                    UtilsMiscellaneous.init();
                }
            }
            return null;
        }
    }
}
