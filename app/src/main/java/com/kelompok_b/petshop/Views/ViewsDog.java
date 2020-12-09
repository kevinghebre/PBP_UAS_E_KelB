package com.kelompok_b.petshop.Views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.kelompok_b.petshop.Api.PetAPI;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.adapter.AdapterCat;
import com.kelompok_b.petshop.adapter.AdapterDog;
import com.kelompok_b.petshop.model.Cat;
import com.kelompok_b.petshop.model.Dog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ViewsDog extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDog adapter;
    private List<Dog> listDog;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dog_shop_fragment, container, false);

        loadDaftarDog();
        System.out.println("aaaaaa");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bar_dog, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.btnSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.btnSearch).setVisible(true);
        menu.findItem(R.id.btnAdd).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnAdd) {
            Bundle data = new Bundle();
            data.putString("status", "tambah");
            TambahEditDog tambahEditDog = new TambahEditDog();
            tambahEditDog.setArguments(data);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_view_dog, tambahEditDog)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadDaftarDog() {
        setAdapter();
        getDog();
    }

    public void setAdapter() {
//        getActivity().setTitle("Data Anjing");
        getActivity().setTitle("Data Dog");
        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/
        final int col = getResources().getInteger(R.integer.gallery_columns);
        listDog = new ArrayList<Dog>();
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new AdapterDog(view.getContext(), listDog, new AdapterDog.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if (delete) {
                    loadDaftarDog();
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(), col);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getDog() {
        //Tambahkan tampil buku disini
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        //Meminta tanggapan string dari URL yang telah disediakan menggunakan method GET
        //untuk request ini tidak memerlukan parameter
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan Data Anjing");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, PetAPI.URL_SHOW_DOG
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONArray jsonArray = response.getJSONArray("data");

                    if (!listDog.isEmpty())
                        listDog.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int idDog = jsonObject.optInt("id");
                        String nama_dog = jsonObject.optString("pet_name");
                        String jenis_dog = jsonObject.optString("type_name");
                        String jk_dog = jsonObject.optString("gender");
                        Double berat_dog = jsonObject.optDouble("weight");
                        Double umur_dog = jsonObject.optDouble("age");
                        Double harga_dog = jsonObject.optDouble("price");
                        String image_dog = jsonObject.optString("pet_image");
                        String kategori = jsonObject.optString("category");

                        //Membuat objek user
                        Dog dog = new Dog(idDog, nama_dog, jenis_dog, jk_dog, kategori, image_dog, harga_dog, berat_dog, umur_dog);

                        //Menambahkan objek user tadi ke list user
                        listDog.add(dog);
                    }
                    Toast.makeText(view.getContext(), Integer.toString(jsonArray.length()), Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
//                Toast.makeText(view.getContext(), error.getMessage(),
//                        Toast.LENGTH_SHORT).show();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}