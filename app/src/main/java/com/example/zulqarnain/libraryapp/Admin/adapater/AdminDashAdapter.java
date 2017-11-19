package com.example.zulqarnain.libraryapp.Admin.adapater;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zulqarnain.libraryapp.R;
import com.example.zulqarnain.libraryapp.model.Book;
import com.example.zulqarnain.libraryapp.utils.Messege;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/19/2017.
 */

    public class AdminDashAdapter extends RecyclerView.Adapter<AdminDashAdapter.ViewJobHolder> {
        private ArrayList<Book> bookList;
        private Context mContext;

        public AdminDashAdapter(Context context, ArrayList<Book> bookList) {
            this.bookList = bookList;
            this.mContext = context;
        }

        @Override
        public ViewJobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.single_book_view, parent, false);
            ViewJobHolder holder = new ViewJobHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewJobHolder holder, int position) {

        Book book= bookList.get(position);
            holder.bindView(book);
        }

        @Override
        public int getItemCount() {
            return bookList.size();
        }

        public class ViewJobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView bkTitle;
            private TextView bDec;
            private Button bDel;
            private Book mBook;
            public ViewJobHolder(View itemView) {
                super(itemView);
                bkTitle = itemView.findViewById(R.id.ed_title);
                bDec =itemView.findViewById(R.id.book_desc);
                bDel =itemView.findViewById(R.id.btn_dlt);
                bDel.setOnClickListener(this);
            }

            public void bindView(Book mBook) {
                this.mBook = mBook;
                bkTitle.setText(mBook.getBookTitle());
                bDec.setText(mBook.getBookDescription());
            }


            public int getIndex(String key) {
                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).toString().equals(key)) {
                        return i;
                    }
                }
                return -1;
            }

            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.btn_dlt){
                    FirebaseDatabase.getInstance().getReference("book")
                            .child(mBook.getBookKey()).removeValue();
                }
            }
        }
    }


