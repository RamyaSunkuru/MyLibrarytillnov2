package com.example.ramya_5542.mylibrary.Activities;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ramya_5542.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

public class BookSearchDetailFragment extends Fragment{
    LayoutInflater inflater;
    ViewGroup container;
    List<Books> booksArray;
    DataBase.DataBaseHandler dataBaseHandler;
    TextView  title,author , id,publisher,decription,pages,bookId,genre;
    RatingBar rating;
    private Boolean isFabOpen = false;
    private FloatingActionButton fabAdd,fabMyLibrary,fabWishList;
    private Animation fabOpen,fabClose,rotateForward,rotateBackward;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final int book_id;
        this.inflater= inflater;
        this.container=container;

        View view = inflater.inflate(R.layout.book_search_list_detail, container, false);
        setHasOptionsMenu(false);
        getActivity().setTitle("Book Details");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        fabAdd = (FloatingActionButton)view.findViewById(R.id.fab_add) ;
        fabMyLibrary = (FloatingActionButton)view.findViewById(R.id.fab_mylibrary) ;
        fabWishList = (FloatingActionButton)view.findViewById(R.id.fab_wishlist) ;
        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.fab_rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.fab_rotate_backward);

        title= (TextView) view.findViewById(R.id.book_title);
        author=(TextView) view.findViewById(R.id.book_author);
        id=(TextView) view.findViewById(R.id.book_id);
        publisher=(TextView) view.findViewById(R.id.book_publisher);
        genre=(TextView) view.findViewById(R.id.book_genre);
        pages=(TextView) view.findViewById(R.id.book_pages);
        rating=(RatingBar) view.findViewById(R.id.book_rating);
        decription=(TextView) view.findViewById(R.id.book_decription);

        book_id = Integer.parseInt(getArguments().getString("BOOK_ID"));
        booksArray = new ArrayList<Books>(1);
        dataBaseHandler = new DataBase.DataBaseHandler(getContext());
        booksArray = dataBaseHandler.getBooksDetail(book_id,getContext());
        //String val = booksArray.get(0).getTitle();
        //String val1 = booksArray.get(0).getGenre();

        title.setText(booksArray.get(0).getTitle());
        author.setText(booksArray.get(0).getAuthor());
        id.setText(book_id+"");
        publisher.setText(booksArray.get(0).getPublisher());
        pages.setText(booksArray.get(0).getPages()+"");
        genre.setText(booksArray.get(0).getGenre());
        rating.setRating(booksArray.get(0).getRating());
        decription.setText(booksArray.get(0).getDescription());

          fabAdd.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  animateFAB();
              }
          });
        fabMyLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Ramya", "Fab MyLibrary");
                dataBaseHandler = new DataBase.DataBaseHandler(getContext());
              //  dataBaseHandler.addMemberActivity(new MemberActivity(,book_id),getContext());

            }
        });
        fabWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Ramya", "Fab WishList");
            }
        });




        return view;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();

                return true;
            case  R.id.action_add:
                  return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onResume() {

        View view = inflater.inflate(R.layout.book_search_list_detail, container, false);
        ((HomeActivity)getActivity()).setDrawerState(false);
        super.onResume();
    }
    public void animateFAB(){

        if(isFabOpen){

            fabAdd.startAnimation(rotateBackward);
            fabMyLibrary.startAnimation(fabClose);
            fabWishList.startAnimation(fabClose);
            fabMyLibrary.setClickable(false);
            fabWishList.setClickable(false);
            isFabOpen = false;


        } else {

            fabAdd.startAnimation(rotateForward);
            fabMyLibrary.startAnimation(fabOpen);
            fabWishList.startAnimation(fabOpen);
            fabMyLibrary.setClickable(true);
            fabWishList.setClickable(true);
            isFabOpen = true;


        }
    }




}







