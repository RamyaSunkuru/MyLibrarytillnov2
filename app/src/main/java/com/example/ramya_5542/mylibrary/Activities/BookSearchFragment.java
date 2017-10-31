package com.example.ramya_5542.mylibrary.Activities;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.ramya_5542.mylibrary.R;
import java.util.ArrayList;
import java.util.List;

public class BookSearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    View view;
    TextView emptyText;
    LinearLayout recycleViewContainer;
    DataBase.DataBaseHandler dataBaseHandler;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private BookSearchListAdapter bookSearchListAdapter;
    String selectedPublisher, selectedGenre, selectedRating;
    Button applyFilters;
    List<Books> booksList = new ArrayList<Books>();
    public static final int LOADER_ID1 = 1;
    private static String AUTHORITY = "com.example.ramya_5542.mylibrary.com.example.ramya_5542.mylibrary.Activities.DataBase";
    public static String LIBRARY_URI = "content://" + AUTHORITY + "/";
    public static String BOOKS_TABLE = "Books_Table";
    public static String CONTENT_URI = LIBRARY_URI + BOOKS_TABLE;
    public static BookSearchFragment newInstance() {
        BookSearchFragment fragment = new BookSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID1, null,this);
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_booksearch, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("Book Search");



        recyclerView = (RecyclerView) view.findViewById(R.id.booksearch_recyclerview) ;
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
       // dataBaseHandler = new DataBase.DataBaseHandler(getContext());
       // booksList = dataBaseHandler.getAllBooks(getContext());
       // bookSearchListAdapter=new BookSearchListAdapter(booksList , getContext(),this) ;
        recyclerView.setAdapter(bookSearchListAdapter);
        emptyText = (TextView) view.findViewById(R.id.empty_text);
        recycleViewContainer=(LinearLayout)view.findViewById(R.id.linear_layout_container) ;
        applyFilters = (Button) view.findViewById(R.id.button_applyfilters);

        getLoaderManager().initLoader(LOADER_ID1,null,this);

        String [] values_publisher =
                {"All","B&H Publishing group","Hachette UK","Random House","publisher1",};
        String [] values_genre =
                {"All","Fiction","Non Fiction","Mystery","Thriller","Romance","genre1",};

        String [] values_rating = {"Any","5","4","3","2","1"};

        Spinner spinner_publisher = (Spinner) view.findViewById(R.id.spinner_publisher);
        ArrayAdapter<String> adapter_publisher = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values_publisher);
        adapter_publisher.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_publisher.setAdapter(adapter_publisher);
        spinner_publisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPublisher = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner_genre = (Spinner) view.findViewById(R.id.spinner_genre);
        ArrayAdapter<String> adapter_genre = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values_genre);
        adapter_genre.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_genre.setAdapter(adapter_genre);
        spinner_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGenre =parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner_rating = (Spinner) view.findViewById(R.id.spinner_rating);
        ArrayAdapter<String> adapter_rating = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values_rating);
        adapter_rating.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_rating.setAdapter(adapter_rating);
        spinner_rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedRating =parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        applyFilters.setOnClickListener(new View.OnClickListener() {
            ArrayList<Books> booksArray;
            @Override
            public void onClick(View v) {

                booksArray = new ArrayList<Books>();
                dataBaseHandler = new DataBase.DataBaseHandler(getContext());
                booksArray = dataBaseHandler.getBooks(selectedPublisher, selectedGenre, selectedRating,getContext());
                //int    val1 = booksArray.get(0).getPages();// To check whether the data is added in the books array
                // String val2 = booksArray.get(0).getTitle(); // To check
                if(booksArray.isEmpty()){
                    // set empty view
                    recycleViewContainer.setVisibility(View.GONE);
                    emptyText.setVisibility(View.VISIBLE);
                }else {
                    if(!recycleViewContainer.isShown()) {
                      recycleViewContainer.setVisibility(View.VISIBLE);
                        emptyText.setVisibility(View.GONE);
                    }

                    bookSearchListAdapter.booksList = booksArray;
                    bookSearchListAdapter.notifyDataSetChanged();
                }


            }
        });

//        hideFilters.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.booksearch_filterwindow);
//                linearLayout.setVisibility(View.GONE);
//            }
//        });



        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.book_search_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuFilter) {
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.booksearch_filterwindow);
            if(linearLayout.isShown()) {
                linearLayout.setVisibility(View.GONE);
            }else{
                linearLayout.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        ((HomeActivity)getActivity()).setDrawerState(true);
        super.onResume();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(CONTENT_URI);
        return new CursorLoader(getContext(), baseUri, null, null, null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        dataBaseHandler = new DataBase.DataBaseHandler(getContext());
        booksList = dataBaseHandler.getAllBooks(getContext());
        bookSearchListAdapter = new BookSearchListAdapter(booksList,getContext(),this);
        recyclerView.setAdapter(bookSearchListAdapter);
        bookSearchListAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        bookSearchListAdapter.swapCursor(null);
    }
}