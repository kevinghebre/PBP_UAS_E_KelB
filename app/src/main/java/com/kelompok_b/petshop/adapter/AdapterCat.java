package com.kelompok_b.petshop.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kelompok_b.petshop.Api.PetAPI;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.Views.TambahEditCat;
import com.kelompok_b.petshop.model.Cat;
import com.kelompok_b.petshop.model.Pet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class AdapterCat extends RecyclerView.Adapter<AdapterCat.adapterCatViewHolder> {

    private List<Cat> catList;
    private List<Cat> catListFiltered;
    private Context context;
    private View view;
    private AdapterCat.deleteItemListener mListener;

    public AdapterCat(Context context, List<Cat> catList,
                      AdapterCat.deleteItemListener mListener) {
        this.context = context;
        this.catList = catList;
        this.catListFiltered = catList;
        this.mListener = mListener;
    }

    public interface deleteItemListener {
        void deleteItem(Boolean delete);
    }

    @NonNull
    @Override
    public AdapterCat.adapterCatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_adapter_cat, parent, false);
        return new adapterCatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterCatViewHolder holder, int position) {
        final Cat cat = catListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.pet_name.setText(cat.getNama_cat());
        holder.type_name.setText(cat.getJenis_cat());
        holder.weight.setText(formatter.format(cat.getBerat_cat()) + "Kg");
        holder.age.setText(formatter.format(cat.getUmur_cat()) + "Tahun");
        holder.gender.setText(cat.getJk_cat());
        holder.price.setText("Rp " + formatter.format(cat.getHarga_cat()));
//        Glide.with(context)
//                .load(PetAPI.URL_IMAGE+cat.getImage_cat())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.ivGambar);

        if (cat.getImage_cat() != null) {
            byte[] imageByteArray = Base64.decode(cat.getImage_cat(), Base64.DEFAULT);
            Glide.with(context)
                    .load(imageByteArray)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_baseline_pets_24)
                    .into(holder.ivGambar);
        }

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle data = new Bundle();
                data.putSerializable("cat", cat);
                data.putString("status", "edit");
                TambahEditCat tambahEditCat = new TambahEditCat();
                tambahEditCat.setArguments(data);
                loadFragment(tambahEditCat);
            }
        });

        holder.ivHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda yakin ingin menghapus cat ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCat(String.valueOf(cat.getIdCat()));
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (catListFiltered != null) ? catListFiltered.size() : 0;
    }

    public static class adapterCatViewHolder extends RecyclerView.ViewHolder {
        private final TextView type_name;
        private final TextView pet_name;
        private final TextView gender;
        private final TextView price;
        private final TextView weight;
        private final TextView age;
        private final TextView ivEdit;
        private final TextView ivHapus;
        private final ImageView ivGambar;

        public adapterCatViewHolder(@NonNull View itemView) {
            super(itemView);
            pet_name = itemView.findViewById(R.id.tvName);
            type_name = itemView.findViewById(R.id.tvType);
            gender = itemView.findViewById(R.id.tvGender);
            age = itemView.findViewById(R.id.tvAge);
            weight = itemView.findViewById(R.id.tvWeight);
            price = itemView.findViewById(R.id.tvPrice);
            ivGambar = itemView.findViewById(R.id.ivFotoCat);
            ivEdit = (TextView) itemView.findViewById(R.id.ivEdit);
            ivHapus = (TextView) itemView.findViewById(R.id.ivHapus);
            CardView cardCat = itemView.findViewById(R.id.cardCat);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if (userInput.isEmpty()) {
                    catListFiltered = catList;
                } else {
                    List<Cat> filteredList = new ArrayList<>();
                    for (Cat cat : catList) {
                        if (String.valueOf(cat.getNama_cat()).toLowerCase().contains(userInput) ||
                                cat.getJenis_cat().toLowerCase().contains(userInput)) {
                            filteredList.add(cat);
                        }
                    }
                    catListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = catListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                catListFiltered = (ArrayList<Cat>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void loadFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_view_cat, fragment)
                .commit();
    }

    public void deleteCat(String idCat) {
        //Tambahkan hapus buku disini
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Deleting Data Cat");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(DELETE, PetAPI.URL_DELETE + idCat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    mListener.deleteItem(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
