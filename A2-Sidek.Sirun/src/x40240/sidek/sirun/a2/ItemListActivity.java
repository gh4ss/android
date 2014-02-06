/**
 * 
 */
/**
 * @author Sidek Sirun
 *
 */
package x40240.sidek.sirun.a2;

import android.os.Bundle;
//import android.app.Activity;
//import android.view.Menu;

import java.util.List;

import x40240.sidek.sirun.a2.db.DBHelper;
import x40240.sidek.sirun.a2.dbmodel.ItemInfo;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public final class ItemListActivity extends ListActivity {
	
    private static final String LOGTAG = ItemListActivity.class.getSimpleName();
    private static final boolean DEBUG = true;
    
	private ItemInfo    itemInfo;  // data passed to us
	private ListAdapter listAdapter;
	
	private Context     activityContext = this;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intitialize();
        
        setListAdapter(listAdapter = new ItemInfoListAdapter(activityContext));
        
    }
    
    //  Encapsulate non-ui related initialization here.
    private void intitialize() {
        Intent callingIntent = this.getIntent();  // Get the Intent that started us.
        
        //****************************************************************************************
        //  If we are being passed a Serialized POJO:
        // hash table with key/value pairs
        itemInfo = (ItemInfo)callingIntent.getSerializableExtra("itemInfo");
        
        //****************************************************************************************
        // -- Alternative method --
        // If we are being called with each PersonInfo field.
        //
        //String  itemName         = callingIntent.getStringExtra("itemName");
        //float   itemPrice        = callingIntent.getFloatExtra("itemPrice", 0);
        //int     itemQuantity     = callingIntent.getIntExtra("itemQuantity", 0);
        //int     itemCondition    = callingIntent.getIntExtra("itemCondition", 1);
        //boolean itemFreeShipping = callingIntent.getBooleanExtra("itemFreeShipping", false);
        //
        //ItemInfo itemInfo = new ItemInfo();
        //itemInfo.setItemName(itemName);
        //itemInfo.setItemPrice(itemPrice);
        //itemInfo.setItemQuantity(itemQuantity);
        //itemInfo.setItemCondition(itemCondition);
        //itemInfo.setItemFreeShipping(itemFreeShipping);        
    }
    
    //**********************************************************************************************
    //  Lifecycle Methods
    //  http://developer.android.com/reference/android/app/Activity.html
    //**********************************************************************************************
    @Override
    public void onStart() {
        //  Called after onCreate() OR onRestart()
        //  Called after onStop() but process has not been killed.
        if (DEBUG) Log.d (LOGTAG, "onRestart");
        super.onRestart();
    }
    
    @Override
    public void onRestart() {
        //  Called after onStop() but process has not been killed.
        if (DEBUG) Log.d (LOGTAG, "onRestart");
        super.onRestart();
    }
    
    @Override
    protected void onResume() {
        //  Called after onStart() as Activity comes to foreground.
        if (DEBUG) Log.d (LOGTAG, "onResume");
        super.onResume();
        new UpdateDBTask().execute (itemInfo);
    }
    
    @Override
    public void onPause() {
        //  Called when Activity is placed in background
        if (DEBUG) Log.d (LOGTAG, "onPause");
        super.onPause();
    }
    
    @Override
    protected void onStop() {
    	//  The Activity is no longer visible
        if (DEBUG) Log.d (LOGTAG, "onStop");
        super.onStop();
    }
    
    @Override
    public void onDestroy() {
    	//  The Activity is finishing or being destroyed by the system
        if (DEBUG) Log.d (LOGTAG, "onDestroy");
        super.onDestroy();
    }
	
    //**********************************************************************************************
    //  Dialog management
    //**********************************************************************************************
    private ProgressDialog progressDialog;
    
    protected static final int INDETERMINATE_DIALOG_KEY = 0;
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case INDETERMINATE_DIALOG_KEY:
            progressDialog = new ProgressDialog(this);
            return progressDialog;
        }
        return null;
    }
    
    @Override
    protected void onPrepareDialog (int id, Dialog dialog) {
        if (DEBUG) {
            Log.d(LOGTAG, "onPrepareDialog.threadId="+Thread.currentThread().getId());
            Log.d(LOGTAG, "onPrepareDialog.id="+id);
            Log.d(LOGTAG, "onPrepareDialog.dialog="+dialog);
        }
        if (dialog instanceof ProgressDialog)
        {
            ((ProgressDialog)dialog).setMessage(getResources().getText(R.string.please_wait_label));
            ((ProgressDialog)dialog).setIndeterminate(true);
            ((ProgressDialog)dialog).setCancelable(true);
        }
    }
    
    // http://developer.android.com/reference/android/os/AsyncTask.html
    private class UpdateDBTask extends AsyncTask<ItemInfo, Void, List<ItemInfo>>
	{
	    private final String LOGTAG = UpdateDBTask.class.getSimpleName();
	    private final boolean DEBUG = true;
	    
	    //  Runs on Main thread so we can manipulate the UI.
	    @Override
	    protected void onPreExecute() {
	        showDialog(INDETERMINATE_DIALOG_KEY);
	    }
	    
	    //  Do all expensive operations here off the main thread.
	    @Override
	    protected List<ItemInfo> doInBackground (final ItemInfo...paramArrayOfParams) {
	        if (DEBUG) Log.d(LOGTAG, "**** doInBackground() STARTING");
	        
	    	DBHelper dbHelper = new DBHelper(activityContext);
	        ItemInfo itemInfo = paramArrayOfParams[0];
	        if (itemInfo != null) dbHelper.insert(itemInfo);  //  Do the insert.
	        List<ItemInfo> list = dbHelper.selectAll();
	        // close DB (insert and list are done)
	        dbHelper.closeDb();
	        return list;
	    }
	
	    //  Runs on Main thread so we can manipulate the UI.
	    @Override
	    protected void onPostExecute(final List<ItemInfo> list) {
	        ((ItemInfoListAdapter)listAdapter).setList(list);  // Must be done on main thread
	        dismissDialog(INDETERMINATE_DIALOG_KEY);
	    }
	}
    
    private class ItemInfoListAdapter extends BaseAdapter 
    {
    	//  Remember our context so we can use it when constructing views.
    	private Context        context;
    
    	private List<ItemInfo> list;
    	private LayoutInflater layoutInflater;

    	public ItemInfoListAdapter (Context context) {
    		this.context = context;
    	}

    	/**
    	 * The number of items in the list.
    	 * 
    	 * @see android.widget.ListAdapter#getCount()
    	 */
    	public int getCount() {
    		return list == null ? 0 : list.size();
    	}

    	/**
    	 * @see android.widget.ListAdapter#getItem(int)
    	 */
    	public Object getItem (int position) {
    		return list.get(position);
    	}

    	/**
    	 * Use the array index as a unique id.
    	 * 
    	 * @see android.widget.ListAdapter#getItemId(int)
    	 */
    	public long getItemId (int position) {
    		return position;
    	}

    	public void setList (List<ItemInfo> list) {
    		this.list = list;
    		this.notifyDataSetChanged();
    	}
    
    	/* (non-Javadoc)
    	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
    	 */
    	public View getView (int position, View convertView, ViewGroup parent)
    	{
    		ViewGroup listItem;
    		if (convertView == null) {
    			listItem = (ViewGroup) getLayoutInflator().inflate(R.layout.inventory_list, null);
    		}
    		else {
    			listItem = (ViewGroup) convertView;
    		}
    		ItemInfo itemInfo             = list.get(position);
    		TextView itemNameText         = (TextView) listItem.findViewById(R.id.itemName_text);
    		TextView itemPriceText        = (TextView) listItem.findViewById(R.id.itemPrice_text);
    		TextView itemQuantityText     = (TextView) listItem.findViewById(R.id.itemQuantity_text);
    		TextView itemConditionText    = (TextView) listItem.findViewById(R.id.itemCondition_text);
    		TextView itemFreeShippingText = (TextView) listItem.findViewById(R.id.itemFreeShipping_text);
        
    		itemNameText.setText(itemInfo.getItemName());
    		itemPriceText.setText(Float.toString(itemInfo.getItemPrice()));
    		itemQuantityText.setText(Integer.toString(itemInfo.getItemQuantity()));
    		
    		String itemCondition;
    		String itemFreeShipping;    		
    		Resources resources = context.getResources();
    		
    		switch (itemInfo.getItemCondition()) {
    		case ItemInfo.CONDITION_NEW:
    			itemCondition = resources.getString(R.string.itemNew_label);
    		break;
    		default:
    		case ItemInfo.CONDITION_USED:
    			itemCondition = resources.getString(R.string.itemUsed_label);
    		break;
    		}
    		itemConditionText.setText(itemCondition);
    		
    		switch (itemInfo.getItemFreeShipping()) {
    		case ItemInfo.FREESHIPPING_TRUE:
    			itemFreeShipping = resources.getString(R.string.itemFreeShipping_value);
    		break;
    		default:
    		case ItemInfo.FREESHIPPING_FALSE:
    			itemFreeShipping = resources.getString(R.string.itemNotFreeShipping_value);
    		break;
    		}
    		itemFreeShippingText.setText(itemFreeShipping);
    		
    		return listItem;
    	}
    
    	private LayoutInflater getLayoutInflator() {
    		if (layoutInflater == null) {
    			layoutInflater = (LayoutInflater)
    				this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		}
    		return layoutInflater;
    	}
    }
    
}
