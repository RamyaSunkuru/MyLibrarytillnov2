
package com.example.ramya_5542.mylibrary.Activities;

        import android.content.Context;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.RatingBar;
        import android.widget.TextView;
        import com.example.ramya_5542.mylibrary.R;
        import java.util.List;


public class MyLibraryRecyclerAdapter extends RecyclerView.Adapter< MyLibraryRecyclerAdapter.MyViewHolder>{
    public List<Books> booksList;
    Context context;
    Cursor mCursor;
   MyLibraryFragment myLibraryFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView book, author,book_id;
        RatingBar ratingBar;


        public MyViewHolder(View view) {
            super(view);
            book= (TextView) view.findViewById(R.id.textView_bookname) ;
            author= (TextView) view.findViewById(R.id.textView_authorname) ;
            book_id=(TextView) view.findViewById(R.id.textView_bookid) ;
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar) ;
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
            FragmentTransaction transaction = myLibraryFragment.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentObj);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }

    public MyLibraryRecyclerAdapter(List<Books> booksList, Context context,MyLibraryFragment myLibraryFragment) {
        this.booksList = booksList;
        this.context = context;
        this.myLibraryFragment=myLibraryFragment;
    }

    @Override
    public MyLibraryRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mylibrary_recycler_tile, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Books books = booksList.get(position);
        holder.book.setText(books.getTitle());
        holder.author.setText(books.getAuthor());
        holder.ratingBar.setRating(books.getRating());
        holder.book_id.setText((books.getBook_id())+"");
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
