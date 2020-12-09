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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kelompok_b.petshop.Api.ApiClient;
import com.kelompok_b.petshop.Api.UserAPI;
import com.kelompok_b.petshop.Api.UserApiInterface;
import com.kelompok_b.petshop.Api.UserResponse;
import com.kelompok_b.petshop.MainActivity;
import com.kelompok_b.petshop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;

import static com.android.volley.Request.Method.POST;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText input_email, input_password, input_name, input_age, input_gender;
    private TextInputLayout email_layout, name_layout, age_layout, gender_layout, password_layout;
    private MaterialButton btn_register;
    private MaterialTextView text_login;

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    String CHANNEL_ID = "Channel 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        input_email = findViewById(R.id.input_email_register);
        input_password = findViewById(R.id.input_password_register);
        input_age = findViewById(R.id.input_age_register);
        input_name = findViewById(R.id.input_name_register);
        input_gender = findViewById(R.id.input_gender_register);
        email_layout = findViewById(R.id.input_email_register_layout);
        name_layout = findViewById(R.id.input_name_register_layout);
        age_layout = findViewById(R.id.input_age_register_layout);
        gender_layout = findViewById(R.id.input_email_gender_layout);
        password_layout = findViewById(R.id.input_password_register_layout);

        btn_register = findViewById(R.id.btn_register);
        text_login = findViewById(R.id.text_login);

        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        firebaseAuth = firebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                regist();
                if (input_name.getText().toString().isEmpty()) {
                    name_layout.setError("Please Input Name");
                }
                if (input_age.getText().toString().isEmpty()) {
                    age_layout.setError("Please Input Age");
                }
                if (input_gender.getText().toString().isEmpty()) {
                    gender_layout.setError("Please Input Gender");
                }
                if (input_email.getText().toString().isEmpty()) {
                    email_layout.setError("Please Input Email");
                }
                if (input_password.getText().toString().isEmpty()) {
                    password_layout.setError("Please Input Password");
                    return;
                } else if (input_password.length() < 6) {
                    password_layout.setError("Password Minimal 6 Karakter");
                    return;
                }
                saveUser();
            }
        });


//        btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = input_email.getText().toString().trim();
//                String password = input_password.getText().toString().trim();
//
////                if (firebaseAuth.getCurrentUser() != null){
////                    startActivity( new Intent(MainActivity.this,LoginActivity.class));
////                    finish();
////                }
//                if (TextUtils.isEmpty(email)){
////                    input_email.setError("Input Email");
//                    Toast.makeText(RegisterActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)){
////                    input_password.setError("Input Password");
//                    Toast.makeText(RegisterActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (password.length() < 6){
////                    input_password.setError("Password too short");
//                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                progressBar.setVisibility(View.VISIBLE);
//
//                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if (task.isSuccessful()) {
//                            firebaseAuth.signOut();
//                            createNotificationChannel();
//                            addNotification();
//                            Toast.makeText(RegisterActivity.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "Registration Failed, " +task.getException().getMessage() , Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
    }

    // Pake Volley nyerah bro.....
    public void regist() {
        //Pendeklarasian queue

        final String name = this.input_name.getText().toString().trim();
        final String age = this.input_age.getText().toString().trim();
        final String gender = this.input_gender.getText().toString().trim();
        final String email = this.input_email.getText().toString().trim();
        final String password = this.input_password.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menambahkan user");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, UserAPI.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
//                        progressDialog.dismiss();
                        try {
                            //Mengubah response string menjadi object
                            JSONObject obj = new JSONObject(response);
                            //obj.getString("message") digunakan untuk mengambil pesan status dari response
                            if (obj.getString("message").equals("Please confirm yourself by clicking on verify user button sent to you on your email")) {
                                Toast.makeText(RegisterActivity.this, "Register Berhasil!", Toast.LENGTH_SHORT).show();
                            }

                            //obj.getString("message") digunakan untuk mengambil pesan message dari response
//                            Toast.makeText(RegisterActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Berhasil!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
//                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //            @Override
//            protected Map<String, String> getParams() {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

            /*
                                Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                                dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                                API.
                            */
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("age", age);
                params.put("gender", gender);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
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
        Intent notificationIntent = new Intent(this, RegisterActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        //Menampilkan notifikasi
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void saveUser() {
        String image = "";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Melakukan Registrasi user");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        UserApiInterface userApiInterface = ApiClient.getClient().create(UserApiInterface.class);
        Call<UserResponse> add = userApiInterface.registerUser(
                input_name.getText().toString(),
                input_gender.getText().toString(),
                input_age.getText().toString(),
                image,
                input_email.getText().toString(),
                input_password.getText().toString());

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                name_layout.setError(null);
                gender_layout.setError(null);
                age_layout.setError(null);
                email_layout.setError(null);
                password_layout.setError(null);
                progressDialog.dismiss();
                if (response.code() == 200) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    createNotificationChannel();
                    addNotification();
                    startActivity(i);
                    finish();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());

                        if (jObjError.get("message") instanceof JSONObject) {
                            if (jObjError.getJSONObject("message").has("email")) {
                                email_layout.setError(jObjError.getJSONObject("message").getJSONArray("email").get(0).toString());
                            }
                            if (jObjError.getJSONObject("message").has("password")) {
                                password_layout.setError(jObjError.getJSONObject("message").getJSONArray("password").get(0).toString());
                            }
                            if (jObjError.getJSONObject("message").has("name")) {
                                name_layout.setError(jObjError.getJSONObject("message").getJSONArray("name").get(0).toString());
                            }
                            if (jObjError.getJSONObject("message").has("gender")) {
                                gender_layout.setError(jObjError.getJSONObject("message").getJSONArray("gender").get(0).toString());
                            }
                            if (jObjError.getJSONObject("message").has("age")) {
                                age_layout.setError(jObjError.getJSONObject("message").getJSONArray("age").get(0).toString());
                            }
                        } else
                            Toast.makeText(RegisterActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
//        add.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
//                Toast.makeText(RegisterActivity.this, "Berhasil menambah user", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
//                createNotificationChannel();
//                addNotification();
//                startActivity(i);
//                finish();
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                Toast.makeText(RegisterActivity.this, "Gagal menambah user", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//        });
//    }
}