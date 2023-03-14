package com.example.vastu;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class ListingsFragment extends Fragment{

    public static final String TAG = "ListingsFragment";
    FirebaseFirestore fStore;
    StorageReference storageReference;
    ArrayList<Product> mArrayList = new ArrayList<>();
    List<StorageReference> images;
    ListView lv;

    public ListingsFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listings, container, false);
        lv=view.findViewById(R.id.listview);

        fStore = FirebaseFirestore.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        // Retrieving image from firebase storage
        StorageReference storageRef =  storageReference.child("products/");

        // Now we get the references of these images
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                images = listResult.getItems();
                // Fetching User details from firebase cloud storage
                fStore.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d(TAG,"onSuccess: LIST EMPTY");
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<Product> types = documentSnapshots.toObjects(Product.class);

                            // Add all to your list
                            mArrayList.addAll(types);
                            ListAdapter adapter = new ListAdapter(getActivity(),mArrayList,images);
                            lv.setAdapter(adapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"onFailure: " + e);
                    }
                });

            }
        });
        return view;
    }



}
