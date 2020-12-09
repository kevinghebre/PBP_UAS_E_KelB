package com.kelompok_b.petshop.Views;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.kelompok_b.petshop.Api.FoodAPI;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.model.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class TambahEditFood extends Fragment {

    private TextInputEditText txtNamaFood, txtHargaFood, txtStokFood, txtBeratFood, txtKategoriFood, txtSupplier, txtCalories;
    private String status;
    private ImageView ivGambar;
    private Button btnSimpan, btnBatal, btnUnggah;
    private String selected;
    private int idFood;
    private Food food;
    private View view;
    private Bitmap bitmap;
    private Uri selectedImage = null;
    private static final int PERMISSION_CODE = 1000;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private int REQUEST_IMAGE_CAPTURE = 100;
    private int RESULT_OK = -1;
    private String imageString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tambah_edit_food, container, false);
        init();
        setAttribut();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu.findItem(R.id.btnSearch) != null)
            menu.findItem(R.id.btnSearch).setVisible(false);
        if (menu.findItem(R.id.btnAdd) != null)
            menu.findItem(R.id.btnAdd).setVisible(false);
    }

    public void init() {
        food = (Food) getArguments().getSerializable("food");
        txtNamaFood = view.findViewById(R.id.txtNamaFood);
        txtKategoriFood = view.findViewById(R.id.txtJenisFood);
        txtCalories = view.findViewById(R.id.txtCalories);
        txtStokFood = view.findViewById(R.id.txtStock);
        txtBeratFood = view.findViewById(R.id.txtWeight);
        txtHargaFood = view.findViewById(R.id.txtPrice);
        txtSupplier = view.findViewById(R.id.txtSupplier);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnBatal = view.findViewById(R.id.btnBatal);
        btnUnggah = view.findViewById(R.id.btnUnggah);
        ivGambar = view.findViewById(R.id.ivGambar);

        status = getArguments().getString("status");
        if (status.equals("edit")) {
            idFood = food.getIdFood();
            txtNamaFood.setText(food.getFood_name());
            txtKategoriFood.setText(food.getCategory());
            txtBeratFood.setText(String.valueOf(food.getNet_weight()));
            txtStokFood.setText(String.valueOf(food.getStock()));
            txtHargaFood.setText(String.valueOf(Math.round(food.getPrice())));
            txtCalories.setText(String.valueOf(Math.round(food.getCalories())));
            txtSupplier.setText(food.getSupplier());
            Glide.with(view.getContext())
                    .load(food.getFood_image())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(ivGambar);
        }
    }

    private void setAttribut() {
        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
                View view = layoutInflater.inflate(R.layout.pilih_media, null);

                final AlertDialog alertD = new AlertDialog.Builder(view.getContext()).create();

                Button btnKamera = (Button) view.findViewById(R.id.btnKamera);
                Button btnGaleri = (Button) view.findViewById(R.id.btnGaleri);

                btnKamera.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        selected = "kamera";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                                    PackageManager.PERMISSION_DENIED ||
                                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                            PackageManager.PERMISSION_DENIED) {
                                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permission, PERMISSION_CODE);
                            } else {
                                openCamera();
                            }
                        } else {
                            openCamera();
                        }
                        alertD.dismiss();
                    }
                });

                btnGaleri.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        selected = "galeri";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permission, PERMISSION_CODE);
                            } else {
                                openGallery();
                            }
                        } else {
                            openGallery();
                        }
                        alertD.dismiss();
                    }
                });

                alertD.setView(view);
                alertD.show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_food = txtNamaFood.getText().toString();
                String jenis_food = txtKategoriFood.getText().toString();
                String supplier = txtSupplier.getText().toString();
                String harga_food = txtHargaFood.getText().toString();
                String berat_food = txtBeratFood.getText().toString();
                String kalori = txtCalories.getText().toString();
                String stok_food = txtStokFood.getText().toString();
                String gambar = imageString;

                if (name_food.isEmpty() || txtStokFood.getText().toString().isEmpty() || txtHargaFood.getText().toString().isEmpty() || jenis_food.isEmpty() || supplier.isEmpty()
                        || txtBeratFood.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                else {
//                    food = new Food(jenis_food, name_food, gambar, supplier,
//                            Double.parseDouble(harga_food), Double.parseDouble(kalori), Double.parseDouble(berat_food), Double.parseDouble(stok_food));
                    if (status.equals("tambah")) {
                        String bytesString = "";
                        if (bitmap != null) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] bytes = byteArrayOutputStream.toByteArray();
                            bytesString = Base64.encodeToString(bytes, Base64.DEFAULT);
                        }
                        tambahFood(jenis_food, name_food, supplier, Double.parseDouble(harga_food),
                    Double.parseDouble(kalori), Double.parseDouble(berat_food), Double.parseDouble(stok_food), gambar);
                    } else {
                        String bytesString = "";
                        if (bitmap != null) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] bytes = byteArrayOutputStream.toByteArray();
                            bytesString = Base64.encodeToString(bytes, Base64.DEFAULT);
                        }
                        editFood(jenis_food, name_food, supplier, Double.parseDouble(harga_food),
                                Double.parseDouble(kalori), Double.parseDouble(berat_food), Double.parseDouble(stok_food), "null");
                    }
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
//                loadFragment(new ViewsFood());
            }
        });
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    if (selected.equals("kamera"))
                        openCamera();
                    else
                        openGallery();
                } else {
                    Toast.makeText(getContext(), "Permision denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == 1) {
//            selectedImage = data.getData();
//            try {
//                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
//                bitmap = BitmapFactory.decodeStream(inputStream);
//            } catch (Exception e) {
//                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//            ivGambar.setImageBitmap(bitmap);
//            bitmap = getResizedBitmap(bitmap, 512);
//        } else if (resultCode == RESULT_OK && requestCode == 2) {
//            Bundle extras = data.getExtras();
//            bitmap = (Bitmap) extras.get("data");
//            ivGambar.setImageBitmap(bitmap);
//            bitmap = getResizedBitmap(bitmap, 512);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            selectedImage = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            ivGambar.setImageBitmap(bitmap);
            bitmap = getResizedBitmap(bitmap, 512);
            handleUpload(bitmap);
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            ivGambar.setImageBitmap(bitmap);
            bitmap = getResizedBitmap(bitmap, 512);
            handleUpload(bitmap);
        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.replace(R.id.frame_tambah_edit_food, fragment)
                .detach(this)
                .attach(this)
                .commit();
    }

    public void closeFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(TambahEditFood.this).detach(this)
                .attach(this).commit();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void tambahFood(final String category, final String food_name, final String supplier, final double price,
                           final double calories, final double net_weight, final double stock, final String gambar) {
        //Tambahkan tambah buku disini
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menambahkan data Makanan");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, FoodAPI.URL_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan status dari response
                    if (obj.getString("message").equals("Add Food Success")) {
//                        Toast.makeText(getContext(), "masuk", Toast.LENGTH_SHORT).show();
                        loadFragment(new ViewsFood());
                    }

                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
//                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("category", category);
                params.put("price", String.valueOf(price));
                params.put("stock", String.valueOf(stock));
                params.put("food_name", food_name);
                params.put("supplier", supplier);
                params.put("calories", String.valueOf(calories));
                params.put("net_weight", String.valueOf(net_weight));
                if (gambar != null) {
                    params.put("food_image", gambar);
                }
                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    public void editFood(final String category, final String food_name, final String supplier, final double price,
                         final double calories, final double net_weight, final double stock, final String gambar) {
        //Tambahkan edit buku disini
        //Tambahkan tambah buku disini
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengedit data Food");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(PUT, FoodAPI.URL_UPDATE + idFood, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan status dari response
                    if (obj.getString("message").equals("Update Food Success")) {
                        loadFragment(new ViewsFood());
                    }

                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
//                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("category", category);
                params.put("price", String.valueOf(price));
                params.put("stock", String.valueOf(stock));
                params.put("food_name", food_name);
                params.put("supplier", supplier);
                params.put("calories", String.valueOf(calories));
                params.put("net_weight", String.valueOf(net_weight));
                if (gambar != null) {
                    params.put("food_image", gambar);
                }
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                1200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}