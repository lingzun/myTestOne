package ctsverifier.test.com.newtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private EditText UserNameET, UserPswET;
    private Button Registe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        UserNameET = (EditText) findViewById(R.id.registe_user_name_et);
        UserPswET = (EditText) findViewById(R.id.registe_psw_et);
        Registe = (Button) findViewById(R.id.registe_registe_btn);
//        Intent intent = getIntent();
        final SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
//        String UserName = intent.getStringExtra("userName");
//        String UserPsw = intent.getStringExtra("userPsw");
//        UserNameET.setText(UserName);
//        UserPswET.setText(UserPsw);
        Registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = UserNameET.getText().toString();
                String psw = UserPswET.getText().toString();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(psw)) {
//                    Intent intent=new Intent();
//                    intent.putExtra("userName1",name);
//                    intent.putExtra("userPsw1",psw);
//                    setResult(RESULT_OK,intent);
                    editor.putString("UserName",name);
                    editor.putString("UserPsw",psw);
                    editor.apply();
                    Toast.makeText(SecondActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SecondActivity.this, "用户名或密码为空，请重新输入！", Toast.LENGTH_SHORT).show();
                    UserNameET.setText("");
                    UserPswET.setText("");
                }
            }
        });
    }
}
