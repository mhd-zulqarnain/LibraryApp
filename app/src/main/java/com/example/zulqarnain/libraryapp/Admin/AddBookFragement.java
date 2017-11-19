package com.example.zulqarnain.libraryapp.Admin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zulqarnain.libraryapp.R;
import com.example.zulqarnain.libraryapp.model.Book;
import com.example.zulqarnain.libraryapp.utils.Messege;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Zul Qarnain on 11/19/2017.
 */

public class AddBookFragement extends Fragment implements View.OnClickListener {

    private ImageView bImage;
    private EditText edTitle;
    private EditText edDesc;
    private Button btnSave;
    private Button btnaddImage;
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private final int PICK_IMAGE_REQUEST =1;
    private Uri mImagePath;
    private StorageReference reference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_book_fragement_view, container, false);
        edDesc = view.findViewById(R.id.ed_book_des);
        edTitle = view.findViewById(R.id.ed_book_des);
        bImage = view.findViewById(R.id.image_book);
        btnSave = view.findViewById(R.id.btn_save);
        btnaddImage = view.findViewById(R.id.btn_add_image);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("book");
        btnSave.setOnClickListener(this);
        btnaddImage.setOnClickListener(this);
        reference = FirebaseStorage.getInstance().getReference("bookCover");
        return view;
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_save) {

            saveRecord();

        } else if (view.getId()==R.id.btn_add_image){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    public void saveRecord(){

        String title = edTitle.getText().toString();
        String des = edDesc.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Messege.messege(getContext(), "Add title of the book");
            return;
        }

        if (TextUtils.isEmpty(des)) {
            Messege.messege(getContext(), "Add title of the book");
            return;
        }

        String key = ref.push().getKey();
        Book book = new Book(title, des, "null", key);
        ref.child(key).setValue(book);

        if (mImagePath != null) {
//            StorageReference ref = reference.child(mImagePath.)
           /* StorageReference ref = taskPhotoRef.child(mImagePath.getLastPathSegment());
            ref.putFile(mImagePath).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String key = String.valueOf(mDataBase.push().getKey());
                            Task task = new Task(mTaskName.getText().toString(), key,downloadUrl.toString());
                            mDataBase.child(key).setValue(task);
                        }
                    });
        } else {
            Messege.messege(getContext(), "pubmission failed");
            String key = String.valueOf(mDataBase.push().getKey());
            Task myTask  =new Task(mTaskName.getText().toString(),key,"null");
            mDataBase.child(key).setValue(myTask);*/

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && resultCode == Activity.RESULT_OK) {
            mImagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mImagePath);
                bImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
