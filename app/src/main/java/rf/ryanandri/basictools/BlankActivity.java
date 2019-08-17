package rf.ryanandri.basictools;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Ryan Andri on 8/17/2019.
 */
public class BlankActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        TextView tvResult = findViewById(R.id.nosuornobb);
        tvResult.setText(getIntent().getStringExtra("nosu_or_nobb"));
    }
}
