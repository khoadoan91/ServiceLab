package tcss450.uw.edu.servicelab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REBOOT = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Button startButton = (Button) findViewById(R.id.start_button);
        if (startButton != null) {
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RSSService.setServiceAlarm(v.getContext(), true);
                    editor.putBoolean(getString(R.string.ON), true);
                    editor.commit();
                }
            });
        }
        Button stopButton = (Button) findViewById(R.id.stop_button);
        if (stopButton != null) {
            stopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RSSService.setServiceAlarm(v.getContext(), false);
                    editor.putBoolean(getString(R.string.ON), false);
                    editor.commit();
                }
            });
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},
                    MY_PERMISSIONS_REBOOT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String result = getIntent().getStringExtra(RSSService.FEED);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadData(result, "text/html", null);
    }
}
