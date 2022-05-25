package com.c323FinalProject.ctaddeuc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "reviews.db";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USERID = "_userId";
    public static final String COLUMN_USEREMAIL = "_userEmail";
    public static final String COLUMN_USERNAME = "_userName";
    public static final String COLUMN_USERIMAGE = "_userImage";

    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORYID = "_categoryId";
    public static final String COLUMN_CATEGORYNAME = "_categoryName";
    public static final String COLUMN_CATEGORYIMAGE = "_categoryImage";

    public static final String TABLE_REVIEWLIST = "reviewlist";
    public static final String COLUMN_REVIEWLISTID = "_reviewListId";
    //public static final String COLUMN_CATEGORYID = "_categoryId";
    public static final String COLUMN_REVIEWNAME = "_reviewName";
    public static final String COLUMN_REVIEWLISTIMAGE =  "_reviewListImage";

    public static final String TABLE_REVIEW = "review";
    public static final String COLUMN_REVIEWID = "_reviewId";
    //public static final String COLUMN_REVIEWLISTID = "_reviewListId";
    //public static final String COLUMN_CATEGORYID = "_categoryId";
    public static final String COLUMN_REVIEWDETAILS = "_reviewDetails";
    public static final String COLUMN_REVIEWIMAGE = "_reviewImage";

    public static final String TABLE_FAVORITE = "favorite";
    public static final String COLUMN_FAVORITEID = "_favoriteId";
    //public static final String COLUMN_REVIEWID = "_reviewId";
    //public static final String COLUMN_REVIEWLISTID = "_reviewListId";
    //public static final String COLUMN_REVIEWNAME = "_reviewName";
    //public static final String COLUMN_REVIEWIMAGE = "_reviewImage";

    public static final String TABLE_TRASH = "trash";
    public static final String COLUMN_TRASHID = "_trashId";
    //public static final String COLUMN_FAVORITEID = "_favoriteId";
    //public static final String COLUMN_REVIEWID = "_reviewId";
    //public static final String COLUMN_REVIEWLISTID = "_reviewListId";
    //public static final String COLUMN_REVIEWLISTIMAGE =  "_reviewImage";

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //create db
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" + COLUMN_USERID + " INTEGER PRIMARY KEY, " + COLUMN_USEREMAIL + " TEXT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_USERIMAGE + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + " (" + COLUMN_CATEGORYID + " INTEGER PRIMARY KEY, " + COLUMN_CATEGORYNAME + " TEXT, " + COLUMN_CATEGORYIMAGE + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
        String CREATE_REVIEWLIST_TABLE = "CREATE TABLE " + TABLE_REVIEWLIST + " (" + COLUMN_REVIEWLISTID + " INTEGER PRIMARY KEY, " + COLUMN_REVIEWNAME + " TEXT, " + COLUMN_REVIEWLISTIMAGE + " TEXT, " + COLUMN_CATEGORYID + " INTEGER, " + "FOREIGN KEY(" + COLUMN_CATEGORYID + ") REFERENCES category(" + COLUMN_CATEGORYID + "));";
        sqLiteDatabase.execSQL(CREATE_REVIEWLIST_TABLE);
        String CREATE_REVIEW_TABLE = "CREATE TABLE " + TABLE_REVIEW + " (" + COLUMN_REVIEWID + " INTEGER PRIMARY KEY, " + COLUMN_REVIEWDETAILS + " TEXT," + COLUMN_REVIEWIMAGE + " TEXT, " + COLUMN_REVIEWLISTID + " INTEGER, " + COLUMN_CATEGORYID + " INTEGER, " + "FOREIGN KEY(_reviewListId) REFERENCES reviewlist(_reviewListId), FOREIGN KEY(_categoryId) REFERENCES category(_categoryId));";
        sqLiteDatabase.execSQL(CREATE_REVIEW_TABLE);
        String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE + " (" + COLUMN_FAVORITEID + " INTEGER PRIMARY KEY, " + COLUMN_REVIEWLISTID + " INTEGER, " + COLUMN_CATEGORYID + " INTEGER, " + COLUMN_REVIEWID + " INTEGER, " + COLUMN_REVIEWNAME + " TEXT, " + COLUMN_REVIEWIMAGE + " TEXT, " + "FOREIGN KEY(_reviewListId) REFERENCES review(_reviewListId), FOREIGN KEY(_categoryId) REFERENCES category(_categoryId), FOREIGN KEY(_reviewId) REFERENCES review(_reviewId));";
        sqLiteDatabase.execSQL(CREATE_FAVORITE_TABLE);
        String CREATE_TRASH_TABLE = "CREATE TABLE " + TABLE_TRASH + " (" + COLUMN_TRASHID + " INTEGER PRIMARY KEY, " +  COLUMN_REVIEWLISTID + " INTEGER, " + COLUMN_CATEGORYID + " INTEGER, " + COLUMN_REVIEWID + " INTEGER, " +  COLUMN_FAVORITEID + " INTEGER," + "FOREIGN KEY(_reviewListId) REFERENCES review(_reviewListId), FOREIGN KEY(_categoryId) REFERENCES category(_categoryId), FOREIGN KEY(_reviewId) REFERENCES review(_reviewId), FOREIGN KEY(_favoriteId) REFERENCES favorite(_favoriteId));";
        sqLiteDatabase.execSQL(CREATE_TRASH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWLIST);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRASH);
        onCreate(sqLiteDatabase);

    }

    public Cursor getUsers(){ //returns list of users in db
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_USER, null);
        return res;
    }

    public void addUserDB(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USEREMAIL, user.get_userEmail());
        values.put(COLUMN_USERNAME, user.get_userName());
        values.put(COLUMN_USERIMAGE, user.get_userImage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();

    }

    public void addCategoryDB(Category category){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORYNAME, category.getCategoryName());
        values.put(COLUMN_CATEGORYIMAGE, "");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    public Cursor getCategories(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null);
        return res;
    }

    public Cursor getCategoryId(Category category){
        String name = category.getCategoryName();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor id = db.rawQuery("SELECT " + COLUMN_CATEGORYID + " FROM " + TABLE_CATEGORY + " WHERE " + COLUMN_CATEGORYNAME + " = \"" + name + "\"", null);
        return id;
    }

    public void addReviewListDB(ReviewList reviewlist){
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEWNAME, reviewlist.getReviewListName());
        values.put(COLUMN_REVIEWLISTIMAGE, "");
        values.put(COLUMN_CATEGORYID, reviewlist.getCategoryId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_REVIEWLIST, null, values);
        db.close();

    }

    public Cursor getReviewList(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_REVIEWLIST + " WHERE " + COLUMN_CATEGORYID + " = \"" + id + "\"", null);
        return res;
    }

    public Cursor getReviewId(ReviewList reviewList){
        String name = reviewList.getReviewListName();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + COLUMN_REVIEWLISTID + " FROM " + TABLE_REVIEWLIST + " WHERE " + COLUMN_REVIEWNAME + " = \"" + name + "\"", null);
        return res;
    }

    public void addReviewDB(Review review){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORYID, review.getCategoryId());
        values.put(COLUMN_REVIEWIMAGE, "");
        values.put(COLUMN_REVIEWLISTID, review.getReviewListId());
        values.put(COLUMN_REVIEWDETAILS, review.getReviewDetails());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_REVIEW, null, values);
        db.close();

    }

    public Cursor getReview(String reviewListId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT " + COLUMN_REVIEWDETAILS +  ", " + COLUMN_REVIEWID + " FROM " + TABLE_REVIEW + " WHERE " + COLUMN_REVIEWLISTID + " = \'" + reviewListId + "\'", null);
        return res;
    }

    public Cursor getAllReviews(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_REVIEW, null);
        return res;
    }


    public void addToFavorites(String reviewId, String reviewListId, String reviewName){
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEWID, reviewId);
        values.put(COLUMN_REVIEWLISTID, reviewListId);
        values.put(COLUMN_REVIEWNAME, reviewName);
        values.put(COLUMN_REVIEWIMAGE, "");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_FAVORITE, null, values);
        db.close();

    }

    public Cursor getFavorites(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_FAVORITE, null);
        return res;
    }

    public void removeFavorite(String reviewName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITE, COLUMN_REVIEWNAME + "=?", new String[]{reviewName});
        db.close();


    }

    public void dropFavorite(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);

        String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVORITE + " (" + COLUMN_FAVORITEID + " INTEGER PRIMARY KEY, " + COLUMN_REVIEWLISTID + " INTEGER, " + COLUMN_CATEGORYID + " INTEGER, " + COLUMN_REVIEWID + " INTEGER, " + COLUMN_REVIEWNAME + " TEXT, " + COLUMN_REVIEWIMAGE + " TEXT, " + "FOREIGN KEY(_reviewListId) REFERENCES review(_reviewListId), FOREIGN KEY(_categoryId) REFERENCES category(_categoryId), FOREIGN KEY(_reviewId) REFERENCES review(_reviewId));";
        db.execSQL(CREATE_FAVORITE_TABLE);
        db.close();
    }




}
