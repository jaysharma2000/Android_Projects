package com.example.bookreviewapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreviewapp.R;
import com.example.bookreviewapp.model.Book;

public class BookAdapter extends ListAdapter<Book, BookAdapter.BookViewHolder> {
    public BookAdapter(){super(DIFF_CALLBACK);}

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentUser = getItem(position);
        holder.textViewId.setText("Id: " + currentUser.getId());
        holder.textViewName.setText("Book Name: " + currentUser.getName());
        holder.textViewAuthor.setText("Book Author: " + currentUser.getAuthor());
    }

    public static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK = new DiffUtil.ItemCallback<Book>() {
        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getAuthor().equals(newItem.getAuthor());
        }
    };

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewName, textViewAuthor;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
        }
    }
}
