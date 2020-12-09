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
import com.kelompok_b.petshop.Api.FoodAPI;
import com.kelompok_b.petshop.R;
import com.kelompok_b.petshop.Views.TambahEditFood;
import com.kelompok_b.petshop.model.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.adapterFoodViewHolder> {

    private List<Food> foodList;
    private List<Food> foodListFiltered;
    private Context context;
    private View view;
    private AdapterFood.deleteItemListener mListener;

    public AdapterFood(Context context, List<Food> foodList,
                       AdapterFood.deleteItemListener mListener) {
        this.context = context;
        this.foodList = foodList;
        this.foodListFiltered = foodList;
        this.mListener = mListener;
    }

    public interface deleteItemListener {
        void deleteItem(Boolean delete);
    }

    @NonNull
    @Override
    public AdapterFood.adapterFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_adapter_food, parent, false);
        return new adapterFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFood.adapterFoodViewHolder holder, int position) {
        final Food food = foodListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.food_name.setText(food.getFood_name());
        holder.category.setText(food.getCategory());
        holder.weight.setText(formatter.format(food.getNet_weight()) + "gr");
        holder.stock.setText(formatter.format(food.getStock()) + "pcs");
        holder.calories.setText(formatter.format(food.getCalories()) + "cal");
        holder.price.setText("Rp " + formatter.format(food.getPrice()));
        holder.supplier.setText(food.getSupplier());
//        Glide.with(context)
//                .load(FoodAPI.URL_IMAGE+food.getFood_image())
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(holder.ivGambar);

        if (food.getFood_image() != null) {
            byte[] imageByteArray = Base64.decode(food.getFood_image(), Base64.DEFAULT);
            Glide.with(context)
                    .load(imageByteArray)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_baseline_food_bank_24)
                    .into(holder.ivGambar);
        }

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle data = new Bundle();
                data.putSerializable("food", food);
                data.putString("status", "edit");
                TambahEditFood tambahEditFood = new TambahEditFood();
                tambahEditFood.setArguments(data);
                loadFragment(tambahEditFood);
            }
        });

        holder.ivHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda yakin ingin menghapus Food ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFood(String.valueOf(food.getIdFood()));
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
        return (foodListFiltered != null) ? foodListFiltered.size() : 0;
    }

    public static class adapterFoodViewHolder extends RecyclerView.ViewHolder {
        private final TextView food_name;
        private final TextView calories;
        private final TextView category;
        private final TextView supplier;
        private final TextView price;
        private final TextView weight;
        private final TextView stock;
        private final TextView ivEdit;
        private final TextView ivHapus;
        ;
        private final ImageView ivGambar;

        public adapterFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.tvName);
            category = itemView.findViewById(R.id.tvType);
            supplier = itemView.findViewById(R.id.tvSupplier);
            stock = itemView.findViewById(R.id.tvStok);
            weight = itemView.findViewById(R.id.tvWeight);
            calories = itemView.findViewById(R.id.tvCalories);
            price = itemView.findViewById(R.id.tvPrice);
            ivGambar = itemView.findViewById(R.id.ivFotoFood);
            ivEdit = (TextView) itemView.findViewById(R.id.ivEdit);
            ivHapus = (TextView) itemView.findViewById(R.id.ivHapus);
            CardView cardFood = itemView.findViewById(R.id.cardFood);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if (userInput.isEmpty()) {
                    foodListFiltered = foodList;
                } else {
                    List<Food> filteredList = new ArrayList<>();
                    for (Food food : foodList) {
                        if (String.valueOf(food.getFood_name()).toLowerCase().contains(userInput) ||
                                food.getCategory().toLowerCase().contains(userInput)) {
                            filteredList.add(food);
                        }
                    }
                    foodListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = foodListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                foodListFiltered = (ArrayList<Food>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void loadFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_view_food, fragment)
                .commit();
    }

    public void deleteFood(String idFood) {
        //Tambahkan hapus buku disini
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data Anjing");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(DELETE, FoodAPI.URL_DELETE + idFood, new Response.Listener<String>() {
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
