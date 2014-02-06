package x40240.sidek.sirun.a2.db;

import java.util.ArrayList;
import java.util.List;

import x40240.sidek.sirun.a2.dbmodel.ItemInfo;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public final class DBHelper {
    private static final String LOGTAG = DBHelper.class.getSimpleName();
    
    private static final String DATABASE_NAME        = "ebayItemdb.db";
    private static final int    DATABASE_VERSION     = 1;
    private static final String TABLE_NAME           = "ebayItem";

    // Column Names
    public static final String  KEY_ID               = "_id";
    public static final String  KEY_ITEMNAME         = "itemName";
    public static final String  KEY_ITEMPRICE        = "itemPrice";
    public static final String  KEY_ITEMQUANTITY     = "itemQuantity";
    public static final String  KEY_ITEMCONDITION    = "itemCondition";
    public static final String  KEY_ITEMFREESHIPPING = "itemFreeShipping";

    // Column indexes
    public static final int COLUMN_ID                = 0;
    public static final int COLUMN_ITEMNAME          = 1;
    public static final int COLUMN_ITEMPRICE         = 2;
    public static final int COLUMN_ITEMQUANTITY      = 3;
    public static final int COLUMN_ITEMCONDITION     = 4;
    public static final int COLUMN_ITEMFREESHIPPING  = 5;
    
    private Context             context;
    private SQLiteDatabase      db;
    private SQLiteStatement     insertStmt;
    
    private static final String INSERT =
            "INSERT INTO " + TABLE_NAME + "(" +
              KEY_ITEMNAME + ", " +
              KEY_ITEMPRICE + ", " +
              KEY_ITEMQUANTITY + ", " +
              KEY_ITEMCONDITION + ", " +
              KEY_ITEMFREESHIPPING + ") values (?, ?, ?, ?, ?)";
    
    public DBHelper (Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        db = openHelper.getWritableDatabase();
        insertStmt = db.compileStatement(INSERT);
        //*crash*// db.close();
    }

    public long insert (ItemInfo itemInfo) {
        insertStmt.bindString(COLUMN_ITEMNAME, itemInfo.getItemName());
        insertStmt.bindDouble(COLUMN_ITEMPRICE, itemInfo.getItemPrice());
        insertStmt.bindLong(COLUMN_ITEMQUANTITY, itemInfo.getItemQuantity());
        insertStmt.bindLong(COLUMN_ITEMCONDITION, itemInfo.getItemCondition());
        insertStmt.bindLong(COLUMN_ITEMFREESHIPPING, itemInfo.getItemFreeShipping());
        
        long value = insertStmt.executeInsert();
        Log.d (LOGTAG, "value="+value);
        return value;
    }
    
    public void deleteAll() {
        db.delete(TABLE_NAME, null, null);
    }
    
    // close SQLite DB
    public void closeDb() {
    	db.close();
    }

    //  This is not the way to handle large DB's
    public List<ItemInfo> selectAll() {
        List<ItemInfo> list = new ArrayList<ItemInfo>();
        Cursor cursor = db.query(TABLE_NAME,
        	new String[] { KEY_ID, KEY_ITEMNAME, KEY_ITEMPRICE, KEY_ITEMQUANTITY, KEY_ITEMCONDITION, KEY_ITEMFREESHIPPING },
        	    null, null, null, null, KEY_ITEMNAME);
        if (cursor.moveToFirst())
        {
            do {
                ItemInfo itemInfo = new ItemInfo();
                itemInfo.setItemName(cursor.getString(COLUMN_ITEMNAME));
                itemInfo.setItemPrice((float)cursor.getFloat(COLUMN_ITEMPRICE));
                itemInfo.setItemQuantity((int)cursor.getLong(COLUMN_ITEMQUANTITY));
                itemInfo.setItemCondition((int)cursor.getLong(COLUMN_ITEMCONDITION));
                itemInfo.setItemFreeShipping((int)cursor.getLong(COLUMN_ITEMFREESHIPPING));
                list.add(itemInfo);
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }
    
    private static class OpenHelper extends SQLiteOpenHelper 
    {
    	private static final String LOGTAG = OpenHelper.class.getSimpleName();
    
    	private static final String CREATE_TABLE =
    		"CREATE TABLE " +
    		TABLE_NAME +
    		" (" + KEY_ID + " integer primary key autoincrement, " +
    		KEY_ITEMNAME + " TEXT, " +
    		KEY_ITEMPRICE + " REAL, " +
    		KEY_ITEMQUANTITY  + " INTEGER, " +
    		KEY_ITEMCONDITION + " INTEGER, " +
    		KEY_ITEMFREESHIPPING + " INTEGER);";
    
    	OpenHelper (Context context) {
    		super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	}

    	@Override
    	public void onCreate (SQLiteDatabase db) {
    		Log.d(LOGTAG, "onCreate!");
    		db.execSQL(CREATE_TABLE);
    	}

    	@Override
    	public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
    		Log.w("x40240.sidek.sirun.a2:", "Upgrading database, this will drop tables and recreate.");
    		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    		onCreate(db);
    	}
    }
}
