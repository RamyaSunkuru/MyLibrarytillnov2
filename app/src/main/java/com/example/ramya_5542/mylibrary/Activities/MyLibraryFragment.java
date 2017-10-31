package com.example.ramya_5542.mylibrary.Activities;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.support.v4.content.Loader;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ramya_5542.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

public class MyLibraryFragment extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {
    MyLibraryRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayout recyclerContainer;
    List<Books> memberBookList = new ArrayList<>();
    Context context;
    public static final int LOADER_ID = 0;
    private static String AUTHORITY = "com.example.ramya_5542.mylibrary.com.example.ramya_5542.mylibrary.Activities.DataBase";
    public static String LIBRARY_URI = "content://" + AUTHORITY + "/";
    public static String MEMBERS_ACTIVITY_TABLE = "Members_Activity_Table";
    public static String CONTENT_URI = LIBRARY_URI + MEMBERS_ACTIVITY_TABLE;
    public static MyLibraryFragment newInstance() {
        MyLibraryFragment fragment = new MyLibraryFragment();
        return fragment;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null,this);
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mylibrary, container, false);
        setHasOptionsMenu(true);
        getActivity().setTitle("My Library");
        recyclerView = (RecyclerView) view.findViewById(R.id.mylibrary_recyclerview);
        recyclerContainer = (LinearLayout) view.findViewById(R.id.mylibrary_recycler_container);
        context= getContext();
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerAdapter = new MyLibraryRecyclerAdapter(memberBookList, context,this);
       // recyclerView.setEmptyView(findViewById(R.id.empty_view));
        recyclerView.setAdapter(recyclerAdapter);
        getLoaderManager().initLoader(LOADER_ID,null,this);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_library_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menuSortId) {
            return true;
        }
        if (id == R.id.menuSortAuthor) {
            return true;
        }
        if (id == R.id.menuSortPages) {
            return true;
        }
        if (id == R.id.menuSortPublisher) {
            return true;
        }
        if (id == R.id.menuSortRating) {
            return true;
        }
        if (id == R.id.menuSortTitle) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(CONTENT_URI);
        return new CursorLoader(getContext(), baseUri, null, null, null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst() && data.getCount() > 0)  {
            do {
//                String name = data.getString(1);
//                int id = data.getInt(0);
//                String email = data.getString(2);
//                int phone = data.getInt(3);
//                String address = data.getString(4);
//                memberBookList.add(new Books(id,name,phone,email,address));
            } while (data.moveToNext());
        }
        recyclerAdapter = new MyLibraryRecyclerAdapter(memberBookList,context,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recyclerAdapter.swapCursor(null);
    }
}