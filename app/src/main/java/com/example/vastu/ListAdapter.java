package com.example.vastu;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    ArrayList<Product> products = new ArrayList<>();
    List<StorageReference> images;
    Context context;
    ListAdapter(Context context, ArrayList<Product> products,List<StorageReference> images){
        this.context=context;
        this.products = products;
        this.images=images;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView aType,propAddress,fprice,propDesc,propArea,adverName,adverEmail;
        ImageView displayImageView;
        ConstraintLayout root;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final String TAG = "LISTADAPTER";
        ViewHolder holder = null;
        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item, null);

        holder = new ViewHolder();
        holder.root = view.findViewById(R.id.root);

        holder.aType = view.findViewById(R.id.adTitle);
        holder.fprice = view.findViewById(R.id.price);
        holder.propAddress = view.findViewById(R.id.adCity);
        holder.propArea = view.findViewById(R.id.adArea);
        holder.propDesc = view.findViewById(R.id.adDescrip);
        holder.adverName = view.findViewById(R.id.advertiserName);
        holder.adverEmail = view.findViewById(R.id.advertiserEmail);
        holder.displayImageView = view.findViewById(R.id.displayImageView);

        holder.aType.setText(products.get(position).getTitle());
        holder.fprice.setText(products.get(position).getPrice().replace("€","₹"));
        holder.propAddress.setText(products.get(position).getCity());
        holder.propArea.setText(products.get(position).getArea());
        holder.propDesc.setText(products.get(position).getDescription());
        holder.adverName.setText(products.get(position).getAdvertiserName());
        holder.adverEmail.setText(products.get(position).getAdvertiserEmail());

        final ViewHolder finalHolder = holder;
        images.get(position).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(finalHolder.displayImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e);
            }
        });

        return view;
    }
}
