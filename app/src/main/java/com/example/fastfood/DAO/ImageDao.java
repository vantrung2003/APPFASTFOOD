package com.example.fastfood.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.fastfood.Model.Image;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageDao {
    FirebaseFirestore db;
    CollectionReference _Image;
    Context context;
    FirebaseStorage storage;
    StorageReference storageRef;

    public ImageDao(Context context) {
        db = FirebaseFirestore.getInstance();
        _Image = db.collection("Image");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        this.context = context;
    }

    public void addImage(Image image, ImageView img) {

        Map<String, Object> anh = new HashMap<>();
        anh.put("idfood", image.getIdfood());
        _Image.add(anh)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        StorageReference anhRef = storageRef.child(image.getIdfood() + "/" + documentReference.getId() + ".png");
                        img.setDrawingCacheEnabled(true);
                        img.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = anhRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception exception) {
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                    }
                });
    }

    public void getImage(String idfood, ImageView imageView) {
        _Image.whereEqualTo("idfood", idfood).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Image> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(new Image(document.getId(), document.getData().get("idfood").toString()));
                                StorageReference ref = storageRef.child(idfood + "/" + document.getId() + ".png");
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get().load(uri).into(imageView);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                            }
                        } else {
                        }
                    }
                });
    }

    public void deleteImage(String id) {
        _Image.document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                    }
                });
    }

}
