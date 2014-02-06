package x40240.sidek.sirun.a2;

import x40240.sidek.sirun.a2.dbmodel.ItemInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class ItemDatabaseActivity extends Activity {

    private static final String LOGTAG = ItemDatabaseActivity.class.getSimpleName();
    
    private EditText     itemNameEdit;
    private EditText     itemPriceEdit;
    private EditText     itemQuantityEdit;
    private RadioButton  itemNewButton;
    private RadioButton  itemUsedButton;
    private CheckBox     itemFreeShippingCheck;
    
    private Context activityContext = this;  // our execution context
    
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //  Not used, but demonstrates how we can track clicks on the EditText field
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Log.d(LOGTAG, "itemNameText: Got Click!");
            }
        };
        
        //  Demonstrates how we can monitor/respond to each key typed into the EditText field
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey (View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            Log.d(LOGTAG, view.getClass().getSimpleName()+": Key event!");
                            return true;
                    }
                }
                return false;
            }
        };
        
        itemNameEdit = (EditText) findViewById(R.id.itemName_edit);
        itemNameEdit.setOnClickListener(onClickListener);
        itemNameEdit.setOnKeyListener(onKeyListener);
        
        itemPriceEdit = (EditText) findViewById(R.id.itemPrice_edit);
        itemPriceEdit.setOnClickListener(onClickListener);
        itemPriceEdit.setOnKeyListener(onKeyListener);
        
        itemQuantityEdit = (EditText) findViewById(R.id.itemQuantity_edit);
        itemQuantityEdit.setOnClickListener(onClickListener);
        itemQuantityEdit.setOnKeyListener(onKeyListener);

        itemNewButton    = (RadioButton) this.findViewById(R.id.itemNew_radio);
        itemUsedButton   = (RadioButton) this.findViewById(R.id.itemUsed_radio);      
        
        itemFreeShippingCheck = (CheckBox) this.findViewById(R.id.itemFreeshippingCheckbox);
        //if (checkBox.isChecked()) {
        //    checkBox.setChecked(false);
        //}
    }
    
    @Override
    protected void onResume() {
        //  Called after onStart() as Activity comes to foreground.
        super.onResume();
        clearData();
    }
    
    private void clearData() {
        itemNameEdit.setText(null);
        itemPriceEdit.setText(null);
        itemQuantityEdit.setText(null);
        itemNewButton.setChecked(false);
        itemUsedButton.setChecked(false);
        itemFreeShippingCheck.setChecked(false);
    }
    
    //  This is the OnClickListener that is set in the XML layout as 
    //     android:onClick="onOKButtonClick"
    public void onOKButtonClick(View view) {
        // Perform action on click
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setItemName(itemNameEdit.getText().toString());
        itemInfo.setItemPrice(Float.parseFloat(itemPriceEdit.getText().toString()));
        itemInfo.setItemQuantity(Integer.parseInt(itemQuantityEdit.getText().toString()));
        
        if (itemNewButton.isChecked()) itemInfo.setItemCondition(ItemInfo.CONDITION_NEW);
        if (itemUsedButton.isChecked()) itemInfo.setItemCondition(ItemInfo.CONDITION_USED);
        
        if (itemFreeShippingCheck.isChecked()) 
        	itemInfo.setItemFreeShipping(ItemInfo.FREESHIPPING_TRUE);
        else 
        	itemInfo.setItemFreeShipping(ItemInfo.FREESHIPPING_FALSE);
        
        Log.d(LOGTAG, "itemName="+itemInfo.getItemName());
        Log.d(LOGTAG, "itemPrice="+itemInfo.getItemPrice());
        Log.d(LOGTAG, "itemQuantity="+itemInfo.getItemQuantity());
        Log.d(LOGTAG, "itemCondition="+itemInfo.getItemCondition());
        Log.d(LOGTAG, "itemFreeShipping="+itemInfo.getItemFreeShipping());
        
        Intent myIntent = new Intent();
        // This is an EXPLICIT intent -- i.e., we specify the target Activity by class name.
        myIntent.setClass (activityContext, ItemListActivity.class);  
        
        // We can do the insert here as per the old example:
        //  new DBHelper(NameDatabaseActivity.this).insert(personInfo);
        
        // OR we can pass the PersonInfo in its entirety using the Intent.
        // This specific mechanism requires the POJO (i.e., PersonInfo) implement the
        // Serializable marker interface.
        // POJO: Plain Old Java Object
        // this method is not recommended for large object/data
        myIntent.putExtra("itemInfo", itemInfo);
        
        startActivity(myIntent);
    }
    
    //  This is the OnClickListener that is set in the XML layout as 
    //     android:onClick="onClearButtonClick"
    public void onClearButtonClick(View view) {
    	clearData();
    }
    
    //  This is the OnClickListener that is set in the XML layout as 
    //     android:onClick="onClearButtonClick"
    public void onReportButtonClick(View view) {
        Intent myIntent = new Intent();
        startActivity(new Intent().setClass (activityContext, ItemListActivity.class));
    }
    
}
