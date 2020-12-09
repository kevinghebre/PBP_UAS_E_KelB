package com.kelompok_b.petshop.ui.profil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kelompok_b.petshop.Api.ApiClient;
import com.kelompok_b.petshop.Api.UserAPI;
import com.kelompok_b.petshop.Api.UserApiInterface;
import com.kelompok_b.petshop.Api.UserResponse;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.android.volley.Request.Method.PUT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    String sId, sName, sAge, sGender, sImage, sEmail;
    TextInputEditText name, age, gender;
    MaterialButton btn_update, btn_cancel;
    int idUser;
    User user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        name = root.findViewById(R.id.txtFullname);
        age = root.findViewById(R.id.txtAge);
        gender = root.findViewById(R.id.txtGender);
        btn_cancel = root.findViewById(R.id.btnBatal);
        btn_update = root.findViewById(R.id.btnSimpan);


        Intent i = getActivity().getIntent();
        sId = i.getStringExtra("id");
        sName = i.getStringExtra("name");
        sAge = i.getStringExtra("age");
        sImage = i.getStringExtra("image");
        sGender = i.getStringExtra("gender");
        sEmail = i.getStringExtra("email");
        String image = "";

//        Toast.makeText(getContext(), sName, Toast.LENGTH_SHORT).show();

        name.setText(sName);
        age.setText(sAge);
        gender.setText(sGender);

        idUser = Integer.parseInt(sId);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.nav_profil);
            }
        });

        String txtname = name.getText().toString();
        String txtage = age.getText().toString();
        String txtgender = gender.getText().toString();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfil(txtname, txtage, txtgender, "null");
//                editUser();
                Toast.makeText(getContext(), sId, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(root).navigate(R.id.nav_profil);

            }
        });
        return root;
    }

    private void editUser() {
        UserApiInterface apiService = ApiClient.getClient().create(UserApiInterface.class);
        Call<UserResponse> add = apiService.updateUser(String.valueOf(idUser), name.getText().toString(),
                gender.getText().toString(), Double.parseDouble(age.getText().toString()), null);

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(), ProfilFragment.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal mengupdate user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editProfil(final String name, final String age, final String gender, final String image) {
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Updating Profil Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        Toast.makeText(getContext(), UserAPI.URL_UPDATE + idUser, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(PUT, UserAPI.URL_UPDATE + idUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //JSONObject data = new JSONObject(obj.getString("data"));
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                /*
                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                    API.
                */
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("age", age);
                params.put("gender", gender);
                if (image != null) {
                    params.put("image", image);
                }
                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}