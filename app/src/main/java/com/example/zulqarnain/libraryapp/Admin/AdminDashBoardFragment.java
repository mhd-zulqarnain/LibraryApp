package com.example.zulqarnain.libraryapp.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zulqarnain.libraryapp.Admin.adapater.AdminDashAdapter;
import com.example.zulqarnain.libraryapp.R;
import com.example.zulqarnain.libraryapp.model.Book;
import com.example.zulqarnain.libraryapp.utils.Messege;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/19/2017.
 */

public class AdminDashBoardFragment extends Fragment {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private RecyclerView bookRecycler;
    private ArrayList<Book> adBookList;
    private final String TAG = "test";
    private AdminDashAdapter adapter;
    private  ChildEventListener listenter;
    String comkey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.add_book_dashboard_view,container,false);
        bookRecycler = v.findViewById(R.id.admin_dashbaord_recycler);
        ref= FirebaseDatabase.getInstance().getReference("book");
        updateUi();
        return v;
    }
    public void updateUi(){
        adBookList = new ArrayList<>();
        adapter = new AdminDashAdapter(getContext(), adBookList);
        bookRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        bookRecycler.setAdapter(adapter);
        listenter = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Book book= dataSnapshot.getValue(Book.class);
                Log.d(TAG, "onChildAdded: "+book.getBookDescription());
                adBookList.add(book);
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
                    adBookList.remove(index);
                    Messege.messege(getContext(),"removed");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(listenter);


    }

    public int getIndexOf(String key) {
        for (int i = 0; i < adBookList.size(); i++) {
            Book mj = adBookList.get(i);
            if (mj.getBookKey().equals(key)) {
                return adBookList.indexOf(mj);
            }
        }
        return -1;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser==true){
            if(listenter!=null){
            ref.removeEventListener(listenter);
            updateUi();}
        }
    }
}
