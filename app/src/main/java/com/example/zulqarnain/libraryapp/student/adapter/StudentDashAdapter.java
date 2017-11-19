package com.example.zulqarnain.libraryapp.student.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zulqarnain.libraryapp.R;
import com.example.zulqarnain.libraryapp.model.Book;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Zul Qarnain on 11/19/2017.
 */

    public class StudentDashAdapter extends RecyclerView.Adapter<StudentDashAdapter.ViewJobHolder> {
        private ArrayList<Book> bookList;
        private Context mContext;

        public StudentDashAdapter(Context context, ArrayList<Book> bookList) {
            this.bookList = bookList;
            this.mContext = context;
        }

        @Override
        public ViewJobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.student_single_book_view, parent, false);
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
            private ImageView bkImage;
            private Book mBook;
            public ViewJobHolder(View itemView) {
                super(itemView);
                bkTitle = itemView.findViewById(R.id.st_book_title);
                bkImage= itemView.findViewById(R.id.st_book_image);
            }

            public void bindView(Book mBook) {
                this.mBook = mBook;
                bkTitle.setText(mBook.getBookTitle());
                Picasso.with(mContext).load(mBook.getDownPath()).into(bkImage);
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


