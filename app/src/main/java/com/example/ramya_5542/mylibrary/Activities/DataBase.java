package com.example.ramya_5542.mylibrary.Activities;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.example.ramya_5542.mylibrary.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase extends ContentProvider {
    Context context;

    public DataBase() {
    }

    public DataBase(Context context) {
        this.context = context;
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "MyLibraryData";
    private static String AUTHORITY = "com.example.ramya_5542.mylibrary.com.example.ramya_5542.mylibrary.Activities.DataBase";
    public static String LIBRARY_URI = "content://" + AUTHORITY + "/";
    private static final int DATABASE_VERSION = 1;
    static HashMap<String, String> MEMBERS_PROJECTION_MAP;
    static HashMap<String,String> BOOKS_PROJECTION_MAP;
    static HashMap<String,String> MEMBERS_ACTIVITY_PROJECTION_MAP;



    //table name and columns- Members_Details_Table
    public static String MEMBERS_DETAILS_TABLE = "Members_Details_Table";

    public static final String MEMBER_ID = "member_id";
    public static final String USER_NAME = "user_name";
    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String FIRSTNAME = "first_name";
    public static final String SECONDNAME = "second_name";


    // tablename and columns- BOOKS_TABLE
    public static String BOOKS_TABLE = "Books_Table";
    public static final String BOOK_ID = "book_id";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String PAGES = "pages";
    public static final String PUBLISHER = "publisher";
    public static final String GENRE = "genre";
    public static final String RATING = "rating";
    public static final String DESCRIPTION = "description";
    public static final String COVERPAGE = "coverpage";


    // tablename and columns- Members_LogIn_Table

    public static String MEMBERS_ACTIVITY_TABLE = "Members_Activity_Table";
    public static final String FAVORITES = "favorites";
    public static final String STATUS = "status";
    public static final String REVIEW = "review";
    public static final String TIME = "time";
    public static final String OWN_RATING = "own_rating";


    // tablename and columns- Members_Activity_Table
    public static String MEMBERS_LOGIN_TABLE = "Members_LogIn_Table";
    public static final String PASSWORD = "password";


    /// Table Uri's
    public static Uri CONTENT_LIBRARY_URI = (Uri) Uri.parse(LIBRARY_URI);
    public static Uri MEMBERS_DETAILS_TABLE_URI = (Uri) Uri.parse(LIBRARY_URI + MEMBERS_DETAILS_TABLE);
    public static Uri BOOKS_TABLE_URI = (Uri) Uri.parse(LIBRARY_URI + BOOKS_TABLE);
    public static Uri MEMBERS_LOGIN_TABLE_URI = (Uri) Uri.parse(LIBRARY_URI + MEMBERS_LOGIN_TABLE);
    public static Uri MEMBERS_ACTIVITY_TABLE_URI = (Uri) Uri.parse(LIBRARY_URI + MEMBERS_ACTIVITY_TABLE);


    // For switch cases
    private static final int MEMBERS = 1;
    private static final int BOOKS = 2;
    private static final int LOGIN = 3;
    private static final int MEMBER_ACTIVITY = 4;
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, MEMBERS_DETAILS_TABLE, MEMBERS);
        uriMatcher.addURI(AUTHORITY, BOOKS_TABLE, BOOKS);
        uriMatcher.addURI(AUTHORITY, MEMBERS_LOGIN_TABLE, LOGIN);
        uriMatcher.addURI(AUTHORITY, MEMBERS_ACTIVITY_TABLE, MEMBER_ACTIVITY);
    }


    static final String CREATE_MEMBERS_DETAILS_TABLE = "CREATE TABLE " + MEMBERS_DETAILS_TABLE + "(" + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FIRSTNAME + " TEXT," + SECONDNAME + " TEXT," + USER_NAME + " TEXT  UNIQUE," + EMAIL + " TEXT," + MOBILE + " INTEGER);";
    static final String CREATE_BOOKS_TABLE = "CREATE TABLE " + BOOKS_TABLE + "(" + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " TEXT," + AUTHOR + " TEXT," + PAGES + " INTEGER," + PUBLISHER + " TEXT," + GENRE + " TEXT," + RATING + " INTEGER," + COVERPAGE + " BLOB, " + DESCRIPTION + " TEXT);";
    static final String CREATE_MEMBERS_LOGIN_TABLE = "CREATE TABLE " + MEMBERS_LOGIN_TABLE + "(" + USER_NAME + " TEXT UNIQUE," + PASSWORD + " TEXT," + MEMBER_ID + " INTEGER," + " FOREIGN KEY (" + MEMBER_ID + ") REFERENCES " + MEMBERS_DETAILS_TABLE + " (" + MEMBER_ID + "));";
    static final String CREATE_MEMBERS_ACTIVITY_TABLE = "CREATE TABLE " + MEMBERS_ACTIVITY_TABLE + "(" + MEMBER_ID + " INTEGER," + BOOK_ID + " INTEGER," + FAVORITES + " BOOLEAN," + OWN_RATING + " INTEGER," + STATUS + " BOOLEAN," + TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," + " FOREIGN KEY (" + BOOK_ID + ") REFERENCES " + BOOKS_TABLE + " (" + BOOK_ID + "), " + " FOREIGN KEY (" + MEMBER_ID + ") REFERENCES " + MEMBERS_DETAILS_TABLE + " (" + MEMBER_ID + ") " + ");";

// database handler

    public static class DataBaseHandler extends SQLiteOpenHelper {
        SQLiteDatabase database;
        Context context;

        public DataBaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_MEMBERS_DETAILS_TABLE);
            sqLiteDatabase.execSQL(CREATE_BOOKS_TABLE);
            sqLiteDatabase.execSQL(CREATE_MEMBERS_LOGIN_TABLE);
            sqLiteDatabase.execSQL(CREATE_MEMBERS_ACTIVITY_TABLE);
            createBookTable(sqLiteDatabase);
            // passing database as an argument to  prevent calling the database recursively.
            //sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");

        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }

        /*
        public  void open()
        {
            database = getReadableDatabase();
        }
        */
        public Uri addMemberDetailTable(Members member, Context context) {
            database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.clear();
            contentValues.put(USER_NAME, member.getUserName());
            // contentValues.put(MEMBER_ID,member.getMember_id());
            contentValues.put(FIRSTNAME, member.getFirstName());
            contentValues.put(SECONDNAME, member.getSecondName());
            contentValues.put(EMAIL, member.getEmail());
            contentValues.put(MOBILE, member.getMobile());
            // contentValues.put(PASSWORD,member.getPassword());

            return context.getContentResolver().insert(MEMBERS_DETAILS_TABLE_URI, contentValues);

            //database.insert(MEMBERS_DETAILS_TABLE,null,contentValues);


        }
        public int updateMemberDetailTable(Members member, Context context) {
            database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_NAME, member.getUserName());
            contentValues.put(MEMBER_ID, member.getMember_id());
            contentValues.put(FIRSTNAME, member.getFirstName());
            contentValues.put(SECONDNAME, member.getSecondName());
            contentValues.put(EMAIL, member.getEmail());
            contentValues.put(MOBILE, member.getMobile());
            int i = database.update(MEMBERS_DETAILS_TABLE, contentValues, MEMBER_ID + "=" + member.getMember_id(), null);
            Toast.makeText(context.getApplicationContext(), "Updated your profile successfully", Toast.LENGTH_SHORT).show();
            return i;
        }

        public String searchPassword(String userName) {
            database = getReadableDatabase();
            String query = "select " + USER_NAME + "," + PASSWORD + " from " + MEMBERS_LOGIN_TABLE;
            Cursor cursor = database.rawQuery(query, null);
            String user, pass;
            pass = "not found";
            if (cursor.moveToFirst()) {
                do {
                    user = cursor.getString(0);


                    if (user.equals(userName)) {
                        pass = cursor.getString(1);
                        break;
                    }


                } while (cursor.moveToNext());
            }

            return pass;
        }

        public Members getMemberData(String userName, Context context) {
            //database = getReadableDatabase();
            String[] projection = {USER_NAME, FIRSTNAME, SECONDNAME, EMAIL, MOBILE};
            String selection = USER_NAME + "=?";
            String[] selectionArgs = {userName};
            Cursor cursor = context.getContentResolver().query(MEMBERS_DETAILS_TABLE_URI, projection, selection, selectionArgs, null);
            if (cursor.moveToFirst()) {
                Members members = new Members(cursor.getInt(4), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(0));

                cursor.close();
                return members;
            } else {
                cursor.close();
                return new Members();
            }
        }
      //  int pages ,int rating,String title, String publisher, String description, String genre, String author
        public ArrayList<Books> getBooksDetail(int bookId , Context context){

            String[] projection = { PAGES, RATING,TITLE,PUBLISHER,DESCRIPTION,GENRE,AUTHOR };
            String selection = BOOK_ID + " =?";
            String[] selectionArgs ={bookId+""} ;
            Cursor cursor = context.getContentResolver().query(BOOKS_TABLE_URI, projection, selection, selectionArgs, null);
            ArrayList<Books> booksArrayList = new ArrayList<Books>();

            if (cursor.moveToFirst()) {
                do {

                    booksArrayList.add(new Books(cursor.getInt(0),  cursor.getInt(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)));

                } while (cursor.moveToNext());

            }

            cursor.close();
            return booksArrayList;

        }

       public  ArrayList<Books> getAllBooks(Context context)
       {
           String[] projection = {  BOOK_ID, TITLE,AUTHOR, RATING ,COVERPAGE};
           String selection = null;
           String[] selectionArgs = null;
           Cursor cursor = context.getContentResolver().query(BOOKS_TABLE_URI, projection, selection, selectionArgs, null);
           ArrayList<Books> booksArrayList = new ArrayList<Books>();

           if (cursor.moveToFirst()) {
               do {

                   booksArrayList.add(new Books(cursor.getInt(0),  cursor.getString(1), cursor.getString(2), cursor.getInt(3),cursor.getBlob(4)));

               } while (cursor.moveToNext());

           }

           cursor.close();
           return booksArrayList;

       }




        public ArrayList<Books> getBooks(String publisher, String genre, String rating, Context context) {
            //String[] projection = { PAGES, RATING, TITLE, PUBLISHER, DESCRIPTION, GENRE, AUTHOR, BOOK_ID};
            String[] projection = {  BOOK_ID,TITLE, AUTHOR, RATING,COVERPAGE };
            String selection_filter = "1=1";

            List<String> selectionArgs_filter = new ArrayList<>();

            if(!publisher.equals("All")){
                selection_filter = selection_filter + " AND "  + PUBLISHER + "=?" ;
                selectionArgs_filter.add(publisher);
            }
            if(!genre.equals("All"))
            {
                selection_filter = selection_filter + " AND "  + GENRE + "=?" ;
                selectionArgs_filter.add(genre);
            }
            if(!rating.equals("Any")){
                selection_filter = selection_filter + " AND "  + RATING + "=?" ;
                selectionArgs_filter.add(rating);
            }

            /*
            String selection = PUBLISHER + "=? AND " + GENRE + "=? AND " + RATING + "=? ";
            String[] selectionArgs = {publisher, genre, String.valueOf(rating)};
            Cursor cursor = context.getContentResolver().query(BOOKS_TABLE_URI, projection, selection, selectionArgs, null);
            */
            String[] selectionArgs_filter_array = selectionArgs_filter.toArray(new String[0]);
            Cursor cursor = context.getContentResolver().query(BOOKS_TABLE_URI, projection, selection_filter, selectionArgs_filter_array, null);

            ArrayList<Books> booksArrayList = new ArrayList<Books>();


            if (cursor.moveToFirst()) {
                do {
                    booksArrayList.add(new Books(cursor.getInt(0),  cursor.getString(1), cursor.getString(2), cursor.getInt(3),cursor.getBlob(4)));
                    //Books books = new Books(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                   // booksArrayList.add(books);
                  //  booksArrayList.add(new Books(cursor.getInt(7),cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));

                } while (cursor.moveToNext());

            }

            cursor.close();
            return booksArrayList;
        }

        public boolean searchUserName(String userName) {
            database = getReadableDatabase();
            String query = "select " + USER_NAME + " from " + MEMBERS_LOGIN_TABLE;
            Cursor cursor = database.rawQuery(query, null);
            boolean isDuplicate = false;
            String user;
            if (cursor.moveToFirst()) {
                do {
                    user = cursor.getString(0);
                    if (user.equals(userName)) {
                        isDuplicate = true;
                        break;
                    }
                } while (cursor.moveToNext());
            }
            return isDuplicate;
        }




        public void addLogInDetails(MemberLogin memberLogin) {
            database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MEMBER_ID, memberLogin.getMember_id());
            contentValues.put(USER_NAME, memberLogin.getUserName());
            contentValues.put(PASSWORD, memberLogin.getPassword());
            database.insert(MEMBERS_LOGIN_TABLE, null, contentValues);
        }

        public int getMemberIdWithUri(String userName, Context context) {
            database = getReadableDatabase();
            String[] projection = {MEMBER_ID};
            String selection = USER_NAME + "=?";
            String[] selectionArgs = {userName};
            Cursor cursor = context.getContentResolver().query(MEMBERS_DETAILS_TABLE_URI, projection, selection, selectionArgs, null);
            if (cursor.moveToFirst()) {
                int memberId = Integer.parseInt(cursor.getString(0));
                cursor.close();
                return memberId;
            } else {
                cursor.close();
                return 0;
            }
        }


        public void addBookTable(Books books, SQLiteDatabase database) {

            ContentValues contentValues = new ContentValues();
            contentValues.clear();
            contentValues.put(TITLE, books.getTitle());
            contentValues.put(AUTHOR, books.getAuthor());
            contentValues.put(PUBLISHER, books.getPublisher());
            contentValues.put(GENRE, books.getGenre());
            contentValues.put(DESCRIPTION, books.getDescription());
            contentValues.put(PAGES, books.getPages());
            contentValues.put(RATING, books.getRating());
            contentValues.put(COVERPAGE,books.getCoverpage());
            database.insert(BOOKS_TABLE, null, contentValues);

        }



        public void addMemberActivity(MemberActivity memberActivity, Context context) {
            database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MEMBER_ID, memberActivity.getMember_id());
            contentValues.put(BOOK_ID, memberActivity.getBook_id());
            contentValues.put(FAVORITES, memberActivity.getFavorite());
            contentValues.put(REVIEW, memberActivity.getReview());
            contentValues.put(STATUS, memberActivity.getReading_status());
            contentValues.put(TIME, memberActivity.getTime()); // write function with time format
            //database.insert(MEMBERS_ACTIVITY_TABLE,null,contentValues);
            context.getContentResolver().update(MEMBERS_ACTIVITY_TABLE_URI, contentValues, null, null);//have to update the table for particular member id
        }

        public int updateMemberActivity(MemberActivity memberActivity) {
            database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MEMBER_ID, memberActivity.getMember_id());
            contentValues.put(BOOK_ID, memberActivity.getBook_id());
            contentValues.put(FAVORITES, memberActivity.getFavorite());
            contentValues.put(REVIEW, memberActivity.getReview());
            contentValues.put(STATUS, memberActivity.getReading_status());
            contentValues.put(TIME, memberActivity.getTime());
            int i = database.update(MEMBERS_ACTIVITY_TABLE, contentValues, MEMBER_ID + "=" + memberActivity.getMember_id(), null);

            return i;
        }
        public byte[] getImageToBytes (int id)
        {
            Drawable dbDrawable =  ContextCompat.getDrawable(context,id);
            Bitmap bitmap = ((BitmapDrawable) dbDrawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,90, stream);
            byte[] image = stream.toByteArray();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            Bitmap bitmap = ((BitmapDrawable)ContextCompat.getDrawable(context,id).getBi
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            byte[] image = baos.toByteArray();
            return image;
        }

        public void createBookTable(SQLiteDatabase db) {
            addBookTable(new Books("The Da Vinci Code","Dan Brown","Fiction","Random House",606, 4,getImageToBytes(R.drawable.davincicod),"While in Paris on business, Harvard symbologist Robert Langdon receives an urgent late-night phone call: the elderly curator of the Louvre has been murdered inside the museum. Near the body, police have found a baffling cipher. Solving the enigmatic riddle, Langdon is stunned to discover it leads to a trail of clues hidden in the works of da Vinci…clues visible for all to see…and yet ingeniously disguised by the painter."), db);
            addBookTable(new Books( "Cat and Mouse",  "James Patterson","Mystery", "Hachette UK",385, 3,getImageToBytes(R.drawable.catmouse), "In Cat & Mouse, things get too close to home. Two killers, one operating in America — one in Europe — believe Alex Cross is the only worthy opponent in the deadly game each has planned. And Alex must contend with something he hasn't known since the loss of his wife: he's falling deeply in love, and this romance will make him, and her, all the more vulnerable."), db);
            addBookTable(new Books( "The Vow","Kim Carpenter", "Non Fiction",  "B&H Publishing group",183 ,4,getImageToBytes(R.drawable.thevow) ,"A massive head injury, as the result of a tragic car accident, left Krickitt Carpenter in a coma just two months after her marriage to Kim. When she finally emerged from the coma, she recognized everyone in her life except her husband, Kim. Starting all over, they built a new love and dedicated their lives to each other all over again."), db);
//            addBookTable(new Books(190, 4, "book5", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(700, 2, "book6", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(890, 1, "book7", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(400, 3, "book8", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(960, 4, "book9", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(540, 5, "book10","publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
//            addBookTable(new Books(100, 5, "book1", "publisher1", "description", "genre1", "author1"), db);
        }


    }


/// content provider methods!

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DataBaseHandler dbHandler = new DataBaseHandler(context);
        db = dbHandler.getWritableDatabase();
        return db != null;

        // return  true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case MEMBERS:
                qb.setTables(MEMBERS_DETAILS_TABLE);
                qb.setProjectionMap(MEMBERS_PROJECTION_MAP);
                break;

            case BOOKS:
                qb.setTables(BOOKS_TABLE);
                qb.setProjectionMap(BOOKS_PROJECTION_MAP);
                break;
            case MEMBER_ACTIVITY:
                qb.setTables(MEMBERS_ACTIVITY_TABLE);
                qb.setProjectionMap(MEMBERS_ACTIVITY_PROJECTION_MAP);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        /*

        if (sortOrder == null || sortOrder == "") {
            /**
             * By default sort on student names
             */
        /*    sortOrder = MEMBER_ID;
        }
        */
        Cursor c = qb.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;

    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri _uri = null;
        switch (uriMatcher.match(uri)) {
            case MEMBERS:
                long _ID1 = db.insert(MEMBERS_DETAILS_TABLE, "", values);
                //---if added successfully---
                if (_ID1 > 0) {
                    _uri = ContentUris.withAppendedId(MEMBERS_DETAILS_TABLE_URI, _ID1);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case BOOKS:
                long _ID2 = db.insert(BOOKS_TABLE, "", values);
                //---if added successfully---
                if (_ID2 > 0) {
                    _uri = ContentUris.withAppendedId(BOOKS_TABLE_URI, _ID2);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case LOGIN:
                long _ID3 = db.insert(MEMBERS_LOGIN_TABLE, "", values);
                //---if added successfully---
                if (_ID3 > 0) {
                    _uri = ContentUris.withAppendedId(MEMBERS_LOGIN_TABLE_URI, _ID3);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case MEMBER_ACTIVITY:
                long _ID4 = db.insert(MEMBERS_ACTIVITY_TABLE, "", values);
                //---if added successfully---
                if (_ID4 > 0) {
                    _uri = ContentUris.withAppendedId(MEMBERS_ACTIVITY_TABLE_URI, _ID4);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        return _uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case MEMBERS:
                count = db.delete(MEMBERS_DETAILS_TABLE, selection, selectionArgs);
                break;
            case BOOKS:
                count = db.delete(BOOKS_TABLE, selection, selectionArgs);
                break;
            case LOGIN:
                count = db.delete(MEMBERS_LOGIN_TABLE, selection, selectionArgs);
                break;
            case MEMBER_ACTIVITY:
                count = db.delete(MEMBERS_ACTIVITY_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String
            selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case MEMBERS:
                count = db.update(MEMBERS_DETAILS_TABLE, values, selection, selectionArgs);
                break;
            case BOOKS:
                count = db.update(BOOKS_TABLE, values, selection, selectionArgs);
                break;
            case LOGIN:
                count = db.update(MEMBERS_LOGIN_TABLE, values, selection, selectionArgs);
                break;
            case MEMBER_ACTIVITY:
                count = db.update(MEMBERS_ACTIVITY_TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {

            case MEMBERS:
                return "vnd.android.cursor.dir/vnd.example.MyLibraryDataBase";
            case BOOKS:
                return "vnd.android.cursor.dir/vnd.example.MyLibraryDataBase";
            case LOGIN:
                return "vnd.android.cursor.dir/vnd.example.MyLibraryDataBase";
            case MEMBER_ACTIVITY:
                return "vnd.android.cursor.dir/vnd.example.MyLibraryDataBase";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
