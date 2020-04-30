package latihansharedpreference;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.imran.latihansharedpreferences.model.R;
import com.imran.latihansharedpreferences.model.UserModel;

import latihansharedpreferences.utils.Preferences;

/* Mohamad Imran 10117170 IF5
   Waktu pengerjaan Kamis 30 April
 */

public class RegisterActivity extends AppCompatActivity {

    private TextView txtMasuk;
    private EditText edtUserName;
    private EditText edtPassWord;
    private EditText edtRePassWord;
    private EditText edtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        declareView();
        txtMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiRegister();
            }
        });

    }

    private void declareView() {
        txtMasuk = findViewById(R.id.txt_reg_masuk);
        edtUserName = findViewById(R.id.edt_reg_username);
        edtPassWord = findViewById(R.id.edt_reg_password);
        edtRePassWord = findViewById(R.id.edt_reg_password_confirm);
        edtPhoneNumber = findViewById(R.id.edt_reg_phone);
    }

    private void validasiRegister(){

        edtUserName.setError(null);
        edtPassWord.setError(null);
        edtRePassWord.setError(null);
        View fokus = null;
        boolean cancel = false;


        String userName = edtUserName.getText().toString();
        String password = edtPassWord.getText().toString();
        String rePassword = edtRePassWord.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();


        if (TextUtils.isEmpty(userName)){
            edtUserName.setError("Harus diisi");
            fokus = edtUserName;
            cancel = true;
        }else if(cekUser(userName)){
            edtUserName.setError("Username sudah terdaftar");
            fokus = edtUserName;
            cancel = true;
        }


        if (TextUtils.isEmpty(password)){
            edtPassWord.setError("Harus Diisi");
            fokus = edtPassWord;
            cancel = true;
        }else if (!cekPassword(password,rePassword)){
            edtPassWord.setError("Password yang dimasukkan tidak sesuai");
            fokus = edtPassWord;
            cancel = true;
        }


        if (cancel){
            fokus.requestFocus();
        }else{

            UserModel userModel = new UserModel();
            userModel.setUsername(userName);
            userModel.setPassword(password);
            userModel.setPhone(phoneNumber);

            Preferences.setUserPreferences(getBaseContext(),userModel);
            Preferences.setLoggedInStatus(getBaseContext(),true);

            startActivity(new Intent(getBaseContext(), HomeActivity.class));
            finish();
        }

    }


    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }

    private boolean cekPassword(String password, String repassword){
        return password.equals(repassword);
    }

}