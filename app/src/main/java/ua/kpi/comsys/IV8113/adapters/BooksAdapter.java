package ua.kpi.comsys.IV8113.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ua.kpi.comsys.IV8113.R;
import ua.kpi.comsys.IV8113.models.Book;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private List<Book> books;
    private BookListener bookListener;

    public BooksAdapter(Context context, BookListener bookListener) {
        books = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.bookListener = bookListener;
    }
    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.book_entry, parent, false);
        view.getResources();
        return new ViewHolder(view, bookListener);
    }

    @Override
    public void onBindViewHolder(BooksAdapter.ViewHolder holder, int position) {
        Book book = books.get(position);
        String tmp = book.getImage();
        if (tmp != null) {
            Glide.with(holder.coverView.getContext())
                    .load(tmp)
                    .placeholder(R.drawable.ic_action_load)
                    .thumbnail(Glide.with(holder.coverView.getContext()).load(R.raw.spinner_icon))
                    .dontAnimate()
                    .into(holder.coverView);
        }
        if(!book.getTitle().isEmpty()) {
            holder.titleView.setText(book.getTitle());
            holder.titleView.setTextColor(holder.titleView.getResources().getColor(R.color.black));
        }
        if(!book.getSubtitle().isEmpty()) {
            holder.subtitleView.setText(book.getSubtitle());
            holder.subtitleView.setTextColor(holder.titleView.getResources().getColor(R.color.black));
        }
        if(!book.getIsbn13().isEmpty()) {
            tmp = holder.isbnView.getResources().getString(R.string.isbn) + book.getIsbn13();
            holder.isbnView.setText(tmp);
            holder.isbnView.setTextColor(holder.titleView.getResources().getColor(R.color.black));
        }
        if(!book.getPrice().isEmpty()) {
            holder.priceView.setText(book.getPrice());
            holder.priceView.setTextColor(holder.titleView.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView coverView;
        final TextView titleView, subtitleView, isbnView, priceView;
        BookListener bookListener;
        ViewHolder(View view, BookListener bookListener){
            super(view);
            coverView = view.findViewById(R.id.cover);
            titleView = view.findViewById(R.id.title);
            subtitleView = view.findViewById(R.id.subtitle);
            isbnView = view.findViewById(R.id.isbn);
            priceView = view.findViewById(R.id.price);
            this.bookListener = bookListener;
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            bookListener.onBookClick(getAdapterPosition());
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface BookListener {
        void onBookClick(int position);
    }

    public void addBook(Book book) {
        this.books.add(book);
        this.notifyDataSetChanged();
    }

    public Book getBook(int position) {
        return  this.books.get(position);
    }

    public void clear() {
        this.books = new ArrayList<>();
        this.notifyDataSetChanged();
    }
}