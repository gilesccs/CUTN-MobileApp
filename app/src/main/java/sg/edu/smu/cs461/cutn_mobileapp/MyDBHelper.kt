package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context:Context) : SQLiteOpenHelper(context,"ProductsDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PRODUCTS(PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY TEXT, PRODUCTNAME TEXT, QUANTITY TEXT, DESCRIPTION TEXT, PRICE REAL, COUNTRY TEXT)")

        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','fuji apple','5','fresh imported apples from fuji',6.5,'Aomori, Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','USA organic apple','5','crunchy and tasty, high in fibre',5.5,'USA')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Australian Avocado','3','Smooth and buttery texture',10.5,'Perth Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Mexican Air-flown Avocado','3','Freshest quality',9.1,'Mexico')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Banana','200g','sweet and flavoury!',2.9,'Singapore')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Malaysia seedless Watermelon','3kg','Juicy, seedless and fantastic',5.95,'Malaysia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Yellow flesh watermelon','4kg','Juicy, seedless and fantastic',6.45,'Tokyo Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','premium golden pineapple','1kg','yummy and fresh!',4.50,'Singapore')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Hong Kong red papaya','1.2kg','juicy with tropical flavour',3.45,'Hong Kong')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','honeydew melon','1.5kg','sweet and juicy',6.5,'Singapore')")

        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Quan fa organic Cabbage','400g','healthy and yummy',3.2,'Johor Bahru')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}