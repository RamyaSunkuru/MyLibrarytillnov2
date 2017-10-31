package com.example.ramya_5542.mylibrary.Activities;


import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.ramya_5542.mylibrary.R;
import java.util.List;


public class BookSearchListAdapter extends RecyclerView.Adapter<BookSearchListAdapter.MyViewHolder>{
    public List<Books> booksList;
    Context context;
    BookSearchFragment bookSearchFragment;
    Cursor mCursor;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView book, author,book_id;
         RatingBar ratingBar;
         ImageView coverImage;

        public MyViewHolder(View view) {
            super(view);
            book= (TextView) view.findViewById(R.id.textView_bookname) ;
            author= (TextView) view.findViewById(R.id.textView_authorname) ;
            book_id=(TextView) view.findViewById(R.id.textView_bookid) ;
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar) ;
            coverImage=(ImageView) view.findViewById(R.id.image_cover) ;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            Bundle args= new Bundle();
            BookSearchDetailFragment fragmentObj = new BookSearchDetailFragment();
            int position = this.getLayoutPosition();
            Books books = booksList.get(position);
            args.putString("BOOK_ID",books.getBook_id()+"");
            fragmentObj.setArguments(args);
            FragmentTransaction transaction = bookSearchFragment.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentObj);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }

    public BookSearchListAdapter(List<Books> booksList, Context context,BookSearchFragment bookSearchFragment) {
        this.booksList = booksList;
        this.context = context;
        this.bookSearchFragment=bookSearchFragment;
    }

    @Override
    public BookSearchListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_recycler_tile, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookSearchListAdapter.MyViewHolder holder, int position) {

        Books books = booksList.get(position);
        byte[] image=books.getCoverpage();
        RoundedBitmapDrawable imageBitmap  = RoundedBitmapDrawableFactory.create(context.getResources(),BitmapFactory.decodeByteArray(image,0,image.length));
        holder.book.setText(books.getTitle());
        holder.author.setText(books.getAuthor());
        holder.ratingBar.setRating(books.getRating());
        holder.book_id.setText((books.getBook_id())+"");
        holder.coverImage.setImageDrawable(imageBitmap);

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}
