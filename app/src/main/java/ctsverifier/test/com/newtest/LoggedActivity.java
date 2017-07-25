package ctsverifier.test.com.newtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoggedActivity extends AppCompatActivity implements View.OnClickListener {
    private Button openUri, permissionTest, permissionTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_activity);
        openUri = (Button) findViewById(R.id.openUrl);
        permissionTest = (Button) findViewById(R.id.permission_btn);
        permissionTest1 = (Button) findViewById(R.id.permission_btn1);
        openUri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoggedActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoggedActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);

                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:112"));
                        startActivity(intent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        permissionTest.setOnClickListener(this);
        permissionTest1.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.permission_btn:
                if ((ContextCompat.checkSelfPermission(LoggedActivity.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)||( ContextCompat.checkSelfPermission(LoggedActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(LoggedActivity.this, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }

            break;
            case R.id.permission_btn1:

                break;
            default:
                break;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:112"));
                        startActivity(intent);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "You deny the permission!", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoggedActivity.this, "You request permission Successful!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "You deny the permission!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }

    }

}
