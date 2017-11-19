package com.example.zulqarnain.libraryapp.student;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zulqarnain.libraryapp.Admin.adapater.AdminDashAdapter;
import com.example.zulqarnain.libraryapp.R;
import com.example.zulqarnain.libraryapp.model.Book;
import com.example.zulqarnain.libraryapp.student.adapter.StudentDashAdapter;
import com.example.zulqarnain.libraryapp.ui.LoginActivity;
import com.example.zulqarnain.libraryapp.utils.Messege;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {


    private DatabaseReference ref;
    private FirebaseAuth auth;
   private RecyclerView stRecyclerView;
    private StudentDashAdapter adapter;
    private ArrayList<Book> stBookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setTitle("Student");
        auth = FirebaseAuth.getInstance();
        stRecyclerView= (RecyclerView) findViewById(R.id.student_dashbaord_recycler);
        stRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
        stBookList = new ArrayList<>();
        adapter=new StudentDashAdapter(this,stBookList);
        ref= FirebaseDatabase.getInstance().getReference("book");
        updateUi();
    }

    public void updateUi(){
        stRecyclerView.setAdapter(adapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Book book= dataSnapshot.getValue(Book.class);
                stBookList.add(book);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Book book= dataSnapshot.getValue(Book.class);
                int index= getIndexOf(book.getBookKey());
                if (index!=-1) {
                    stBookList.remove(index);
                    Messege.messege(StudentActivity.this,"removed");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_logout){
            auth.signOut();
            startActivity(new Intent(StudentActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }
    public int getIndexOf(String key) {
        for (int i = 0; i < stBookList.size(); i++) {
            Book mj = stBookList.get(i);
            if (mj.getBookKey().equals(key)) {
                return stBookList.indexOf(mj);
            }
        }
        return -1;
    }
}
