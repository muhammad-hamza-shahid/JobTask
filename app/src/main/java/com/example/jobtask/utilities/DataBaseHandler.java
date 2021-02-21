package com.example.jobtask.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.jobtask.model.DrinkResponce;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper{

    public static final String DRINK_NAME = " drinkName ";
    public static final String IMAGE_URL=" imageURL ";
    public static final String ALCOHOLIC_STATUS=" alcoholicStatus ";
    public static final String DRINK_RECIPE = " drinkRecipe ";


    private static final String DATABASE_NAME = "FavouriteDrinks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String FAVOURITE_DRINK_TABLE = "AllFavouriteDrinkTable";

    private static final String CREATE_FAVOURITE_DRINKS_TABLE =
            "CREATE TABLE " + FAVOURITE_DRINK_TABLE + " ( "
                    + DRINK_NAME + " TEXT , "
                    + IMAGE_URL + " TEXT , "
                    + ALCOHOLIC_STATUS + " TEXT , "
                    + DRINK_RECIPE + " TEXT ); " ;

     public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAVOURITE_DRINKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVOURITE_DRINK_TABLE);
            onCreate(db);
    }

    public void insertFavorite(DrinkResponce drink)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DRINK_RECIPE,drink.getDrinkRecipe());
        cv.put(IMAGE_URL,drink.getDrinkImage());
        cv.put(ALCOHOLIC_STATUS,drink.getAlcoholStatus());
        cv.put(DRINK_NAME,drink.getDrinkName());

        db.insert(FAVOURITE_DRINK_TABLE,null,cv);

        db.close();
    }

    public List<DrinkResponce> getFavorite()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        List<DrinkResponce> favouriteDrinks = new ArrayList<>();
        String selectFavouriteQuery = "SELECT * FROM " +FAVOURITE_DRINK_TABLE;
     //   this.open();

        Cursor cursor = db.rawQuery(selectFavouriteQuery,null);

        if(cursor.moveToNext())
        {
            do{
                DrinkResponce singleDrink = new DrinkResponce(cursor.getString(0),
                        cursor.getString(3),
                        cursor.getString(2),
                        cursor.getString(1));

                favouriteDrinks.add(singleDrink);
            }while(cursor.moveToNext());
        }

        return favouriteDrinks;
    }
}