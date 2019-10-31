package creo.com.vendors;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import cdflynn.android.library.checkview.CheckView;

public class orderplacesuccess extends AppCompatActivity {
    CheckView checkView;
    TextView textView;
    Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderplacesuccess);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));


// clear FLAG_TRANSLUCENT_STATUS flag:

        checkView = findViewById(R.id.check);
        textView = findViewById(R.id.text);
        checkView.check();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                startActivity(new Intent(orderplacesuccess.this,orderlist.class));
            }
        }, 4000);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(orderplacesuccess.this,Login.class));
    }
}