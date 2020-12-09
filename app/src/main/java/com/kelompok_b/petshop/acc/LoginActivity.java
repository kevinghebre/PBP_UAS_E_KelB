package com.kelompok_b.petshop.acc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.kelompok_b.petshop.Api.ApiClient;
import com.kelompok_b.petshop.Api.UserApiInterface;
import com.kelompok_b.petshop.Api.UserResponse;
import com.kelompok_b.petshop.MainActivity;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.model.User;
import com.kelompok_b.petshop.ui.home.HomeFragment;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText input_Email, input_password;
    private TextInputLayout email_layout, password_layout;
    private MaterialButton btn_login;
    private MaterialTextView text_register;

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    String CHANNEL_ID = "Channel 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_Email = findViewById(R.id.input_email_login);
        email_layout = findViewById(R.id.input_email_login_layout);
        input_password = findViewById(R.id.input_password_login);
        password_layout = findViewById(R.id.input_password_login_layout);
        btn_login = findViewById(R.id.btn_sign_in);
        text_register = findViewById(R.id.text_register);

        firebaseAuth = firebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //fungsi jika user sudah pernah login, akan otomatis login tanpa harus login ulang
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_Email.getText().toString().isEmpty()) {
                    email_layout.setError("input email");
                }
                if (input_password.getText().toString().isEmpty()) {
                    password_layout.setError("input password");
                    return;
                }else if (input_password.length() < 6) {
                    password_layout.setError("Password minimal 6 karakter");
                    return;
                }

                login();
//                final String email = input_Email.getText().toString().trim();
//                String password = input_password.getText().toString().trim();
//
//                // untuk cek apakah sudah pernah login atau belum
////                if (firebaseAuth.getCurrentUser() != null){
////                    startActivity( new Intent(MainActivity.this,LoginActivity.class));
////                    finish();
////                }
//
//                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || password.length() < 6) {
//                    input_Email.setError("Input Email");
//                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                progressBar.setVisibility(View.VISIBLE);
//
//                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
//                            createNotificationChannel();
//                            addNotification();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            intent.putExtra("email_user", email);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });

        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }

    public void login() {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Logging in");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
        Call<UserResponse> userDAOCall = userApiInterface.loginUser(input_Email.getText().toString(), input_password.getText().toString());
        userDAOCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.code() == 200) {

                    if (response.body().getUsers().getEmail().equalsIgnoreCase("admin")) {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        User user = response.body().getUsers();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("id", user.getId());
                        i.putExtra("name", user.getName());
                        i.putExtra("password", user.getPassword());
                        i.putExtra("email", user.getEmail());
                        i.putExtra("gender", user.getGender());
                        i.putExtra("image", user.getImage());
                        i.putExtra("age", user.getAge());
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        createNotificationChannel();
                        addNotification();
                        startActivity(i);
                        progressDialog.dismiss();
                        finish();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());

                        if (object.get("message") instanceof JSONObject) {
                            if (object.getJSONObject("message").has("email")) {
                                email_layout.setError(object.getJSONObject("message").getJSONArray("email").get(0).toString());
                            }
                            if (object.getJSONObject("message").has("password")) {
                                email_layout.setError(object.getJSONObject("message").getJSONArray("password").get(0).toString());
                            }
                            progressDialog.dismiss();
                        } else
                            Toast.makeText(LoginActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                //failure
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int important = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, important);
            channel.setDescription(description);
            //Register the channel with the system; you can't change the importance
            //Or other notification behavior after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification() {
        //konstruktor NotificationCompat.Builder harus diberi CHANNEL_ID untuk api level 26+
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentTitle("Hello")
                .setContentText("Welcome Back, Please Enjoy your stay...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //Membuat intent yang menampilkan notifikasi
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        //Menampilkan notifikasi
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
