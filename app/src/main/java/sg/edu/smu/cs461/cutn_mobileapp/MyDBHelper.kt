package sg.edu.smu.cs461.cutn_mobileapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context:Context) : SQLiteOpenHelper(context,"ProductsDB",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PRODUCTS(PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY VARCHAR(256), PRODUCTNAME VARCHAR(256), QUANTITY VARCHAR(256), DESCRIPTION VARCHAR(256), PRICE REAL, COUNTRY VARCHAR(256))")

        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','fuji Apple','5','fresh imported apples from fuji',6.50,'Aomori, Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','USA organic Apple','5','crunchy and tasty, high in fibre',5.50,'USA')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Australian Avocado','3','Smooth and buttery texture',10.50,'Perth Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Mexican Air-flown Avocado','3','Freshest quality',9.10,'Mexico')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Banana','200g','sweet and flavoury!',2.90,'Singapore')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Malaysia seedless Melon','3kg','Juicy, seedless and fantastic',5.95,'Malaysia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Yellow flesh Melon','4kg','Juicy, seedless and fantastic',6.45,'Tokyo Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','premium golden Pineapple','1kg','yummy and fresh!',4.50,'Singapore')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','Hong Kong red Papaya','1.2kg','juicy with tropical flavour',3.45,'Hong Kong')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('fruits','honeydew Melon','1.5kg','sweet and juicy',6.50,'Singapore')")

        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Green Asparagus','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Quan-fa Organic Cabbage','400g','healthy and yummy',3.20,'Johor Bahru')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Red Cabbage','454g','crunchy and tasty, Healthier option!',4.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Okinawa Carrots','400g','tasty for rabbits',2.90,'Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Baby Carrots','250g','crunchy and tasty, Healthier option!',1.60,'Singapore')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Crunchy Japan Cucumber','454g','crunchy and tasty, Healthier option!',8.45,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','King Oyster Mushroom','100g','Good for steamboat, crunchy!',2.00,'Singapore')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Abalone Mushroom','150g','Good for steamboat, crunchy!',2.95,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','White Whole Garlic','200g','Bad for breathe ew!',1.35,'Vietnam, Ho Chi Minh')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Cherry Tomato','200g','Small Red, Juicy, Healthy!',1.60,'NZ')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Tomato Sauce and Basil','500ML','Good for pasta sauce!',5.60,'NZ')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('vegetables','Holland Potato','1kg','crunchy and tasty, Healthier option!',1.75,'Holland')")

        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('packages','Apple Juice','1.5L','Made from fresh apples from japan',6.65,'Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('packages','Orange Juice','1.5L','Made from fresh oranges from japan',5.95,'Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('packages','Low-fat Yogurt','100g','Lower fat, healthier option!',1.15,'Singapore')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('packages','Pura Sour-Cream','400g','Bad for lactose kids',5.35,'NZ')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('packages','Low-fat Meiji Milk','1L','Good for calcium and bones',5.75,'Japan')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('packages','Goat Milk','1L','Good for calcium and bones, non lactose',6.75,'Australia')")
        db?.execSQL("INSERT INTO PRODUCTS(CATEGORY,PRODUCTNAME,QUANTITY,DESCRIPTION,PRICE,COUNTRY) VALUES('packages','HL-Milk','1L','Premium milk for young boys',4.95,'NZ')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    //get all data.
    fun readData(): MutableList<Product>{
        var list : MutableList<Product> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from Products"
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var curr = Product()
                curr.productid = result.getString(result.getColumnIndex("PRODUCTID")).toInt()
                curr.productname = result.getString(result.getColumnIndex("PRODUCTNAME"))
                curr.quantity = result.getString(result.getColumnIndex("QUANTITY"))
                curr.description = result.getString(result.getColumnIndex("DESCRIPTION"))
                curr.price = result.getString(result.getColumnIndex("PRICE")).toFloat()
                curr.country = result.getString(result.getColumnIndex("COUNTRY"))
                curr.category = result.getString(result.getColumnIndex("CATEGORY"))
                list.add(curr)
            }while(result.moveToNext())
        }

        result.close()
        db.close()
//        Log.i("list",)
        return list
    }

    fun readByCategory(cate:String): MutableList<Product>{
        var list : MutableList<Product> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from Products where category = $cate"
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var curr = Product()
                curr.productid = result.getString(result.getColumnIndex("PRODUCTID")).toInt()
                curr.productname = result.getString(result.getColumnIndex("PRODUCTNAME"))
                curr.quantity = result.getString(result.getColumnIndex("QUANTITY"))
                curr.description = result.getString(result.getColumnIndex("DESCRIPTION"))
                curr.price = result.getString(result.getColumnIndex("PRICE")).toFloat()
                curr.country = result.getString(result.getColumnIndex("COUNTRY"))
                curr.category = result.getString(result.getColumnIndex("CATEGORY"))
                list.add(curr)
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun readByMachineLearning(keyword:String): MutableList<Product>{
        var list : MutableList<Product> = ArrayList()
        val db = this.readableDatabase
        val keyValue = "%$keyword%"
        val query = "Select * from Products where productname LIKE $keyValue"
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var curr = Product()
                curr.productid = result.getString(result.getColumnIndex("PRODUCTID")).toInt()
                curr.productname = result.getString(result.getColumnIndex("PRODUCTNAME"))
                curr.quantity = result.getString(result.getColumnIndex("QUANTITY"))
                curr.description = result.getString(result.getColumnIndex("DESCRIPTION"))
                curr.price = result.getString(result.getColumnIndex("PRICE")).toFloat()
                curr.country = result.getString(result.getColumnIndex("COUNTRY"))
                curr.category = result.getString(result.getColumnIndex("CATEGORY"))
                list.add(curr)
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
}