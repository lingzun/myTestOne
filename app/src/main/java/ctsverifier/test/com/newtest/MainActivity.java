package ctsverifier.test.com.newtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button registe, login, visible;
    private CheckBox saveUser;
    private EditText userName, userPsw;
    String NameData = "", PswData = "";
    private static final String TAG = "MainActivity.this";
    Boolean isChecked = false, isSave = false;
    SharedPreferences pref, save;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("data", MODE_PRIVATE);
        save = getSharedPreferences("data_save", MODE_PRIVATE);
        registe = (Button) findViewById(R.id.main_registe_btn);
        login = (Button) findViewById(R.id.main_login_btn);
        visible = (Button) findViewById(R.id.psw_visible_btn);
        saveUser = (CheckBox) findViewById(R.id.remember_userpsw);
        userName = (EditText) findViewById(R.id.main_user_name_et);
        userPsw = (EditText) findViewById(R.id.main_psw_et);
        visible.setText("Visible");
//        userPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        isChecked = save.getBoolean("remember_password", false);

        if (isChecked) {
            String Name = save.getString("UserName", "");
            String Psw = save.getString("UserPsw", "");
            userName.setText(Name);
            userPsw.setText(Psw);
            saveUser.setChecked(true);
        }

//        load("Name");
//        load("Psw");
//        load("check");
//        //checkbox逻辑
//        saveUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    isSave = true;
////                    Toast.makeText(MainActivity.this, "勾选保存按钮！", Toast.LENGTH_SHORT).show();
//                } else {
//                    isSave = false;
////                    Toast.makeText(MainActivity.this, "取消勾选保存按钮！", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        saveUser.setChecked(isSave);
        //注册按钮逻辑
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                String Name = userName.getText().toString();
//                String Psw = userPsw.getText().toString();
////
//                if (!TextUtils.isEmpty(Name)) {
//                    intent.putExtra("userName", Name);
//                }
//                if (!TextUtils.isEmpty(Psw)) {
//                    intent.putExtra("userPsw", Psw);
//                }
//                startActivityForResult(intent, 1);
                startActivity(intent);
            }
        });
        //登录按钮逻辑
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = userName.getText().toString();
                String Psw = userPsw.getText().toString();
                String account = pref.getString("UserName", "");
                String password = pref.getString("UserPsw", "");
                if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Psw)) {
                    if (account.equals(Name) && password.equals(Psw)) {
//                        Toast.makeText(MainActivity.this, "用户名和密码正确!", Toast.LENGTH_SHORT).show();
                        editor = save.edit();
                        if (saveUser.isChecked()) {
                            editor.putString("UserName", Name);
                            editor.putString("UserPsw", Psw);
                            editor.putBoolean("remember_password", true);
                        } else {
                            editor.clear();
                        }
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, LoggedActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "用户名和密码不正确，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "输入的用户名或密码为空，请重新输入！", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: userName or Password is null！");
                }
            }
        });
        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((visible.getText()).equals("Visible")) {
                    userPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    userPsw.setSelection(userPsw.length());
                    visible.setText("Invisible");
                } else if ((visible.getText()).equals("Invisible")) {
                    userPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    userPsw.setSelection(userPsw.length());
                    visible.setText("Visible");
                }
            }
        });
    }

    //对另一个activity返回的结果进行处理的逻辑
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//
//            NameData = data.getStringExtra("userName1");
//            PswData = data.getStringExtra("userPsw1");
//            userName.setText(NameData);
//            userPsw.setText(PswData);
////            Toast.makeText(MainActivity.this, "数据获取成功！", Toast.LENGTH_SHORT).show();
//        }
//    }
  /*  public void save(String inputText, String fileName) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void load(String fileName) {
        File loadFileName = new File(getFilesDir() + "/" + fileName);
        if (loadFileName.exists()) {
//            Toast.makeText(MainActivity.this, "数据文件已存在！", Toast.LENGTH_SHORT).show();
            FileInputStream in = null;
            BufferedReader readder = null;
            StringBuilder builder = new StringBuilder();
            try {
                in = openFileInput(fileName);
                readder = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = readder.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (readder != null) {
                    try {
                        readder.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (fileName.equals("Name")) userName.setText(builder);
            if (fileName.equals("Psw")) userPsw.setText(builder);
            if (fileName.equals("check") && (builder.toString()).equals("checked")) isSave = true;
        } else {
//            Toast.makeText(MainActivity.this, "未发现 " + fileName, Toast.LENGTH_SHORT).show();
        }
    }
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor = save.edit();
        if (saveUser.isChecked()) {
            String Name = userName.getText().toString();
            String Psw = userPsw.getText().toString();
            if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Psw)) {

                editor.putString("UserName", Name);
                editor.putString("UserPsw", Psw);
                editor.putBoolean("remember_password", true);
            }
        } else {
            editor.clear();
        }
        editor.apply();
//        if (isSave) {
//            String Name = userName.getText().toString();
//            String Psw = userPsw.getText().toString();
//            if (!TextUtils.isEmpty(Name)) save(Name, "Name");
//            if (!TextUtils.isEmpty(Psw)) save(Psw, "Psw");
//            save("checked", "check");
////            Toast.makeText(MainActivity.this, "数据保存操作已执行！", Toast.LENGTH_SHORT).show();
//        } else {
//            File loadFileName = new File(getFilesDir() + "/" + "Name");
//            File loadFileName1 = new File(getFilesDir() + "/" + "Psw");
//            File loadFileName2 = new File(getFilesDir() + "/" + "check");
//            loadFileName.delete();
//            loadFileName1.delete();
//            loadFileName2.delete();
//            Toast.makeText(MainActivity.this, "保存按钮未勾选，默认不保存用户名和密码！", Toast.LENGTH_SHORT).show();
//        }
    }
}
