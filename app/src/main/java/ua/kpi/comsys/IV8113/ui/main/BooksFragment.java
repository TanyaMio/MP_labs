package ua.kpi.comsys.IV8113.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ua.kpi.comsys.IV8113.R;
import ua.kpi.comsys.IV8113.adapters.BooksAdapter;
import ua.kpi.comsys.IV8113.models.Book;


public class BooksFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_books, container, false);
        ArrayList<Book> books = getBooks();
        RecyclerView recyclerView = root.findViewById(R.id.books_list);
        BooksAdapter adapter = new BooksAdapter(this.getContext(), books);
        recyclerView.setAdapter(adapter);
        return root;
    }

    ArrayList<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.books_list);
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        String eachline = null;
        String booksStr = "";
        try {
            eachline = bufferedReader.readLine();
            while (eachline != null) {
                booksStr += eachline;
                eachline = bufferedReader.readLine();
            }
            booksStr = booksStr.substring(booksStr.indexOf("[")+1, booksStr.lastIndexOf("]"));
            String currBook;
            Book book = new Book();
            int elementStart;
            while (!booksStr.isEmpty()) {
                currBook = booksStr.substring(booksStr.indexOf("{")+1, booksStr.indexOf("}"));
                elementStart = currBook.indexOf("\"", currBook.indexOf("\"title\":")+"\"title\":".length());
                book.setTitle(currBook.substring(elementStart+1, currBook.indexOf("\"", elementStart+1)));
                elementStart = currBook.indexOf("\"", currBook.indexOf("\"subtitle\":")+"\"subtitle\":".length());
                book.setSubtitle(currBook.substring(elementStart+1, currBook.indexOf("\"", elementStart+1)));
                elementStart = currBook.indexOf("\"", currBook.indexOf("\"isbn13\":")+"\"isbn13\":".length());
                book.setIsbn13(currBook.substring(elementStart+1, currBook.indexOf("\"", elementStart+1)));
                elementStart = currBook.indexOf("\"", currBook.indexOf("\"price\":")+"\"price\":".length());
                book.setPrice(currBook.substring(elementStart+1, currBook.indexOf("\"", elementStart+1)));
                elementStart = currBook.indexOf("\"", currBook.indexOf("\"image\":")+"\"image\":".length());
                book.setImage(currBook.substring(elementStart+1, currBook.indexOf("\"", elementStart+1)));
                books.add(book);
                book = new Book();
                booksStr = booksStr.substring(booksStr.indexOf("}") + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return books;
    }
}