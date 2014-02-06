package x40240.sidek.sirun.a2.dbmodel;

import java.io.Serializable;

public final class ItemInfo implements Serializable
{
	private static final long serialVersionUID = 1000000000000000009L;
    
    // there are only two conditions for an item: NEW, OLD
    public static final int CONDITION_NEW      = 0;
    public static final int CONDITION_USED     = 1;
    
    public static final int FREESHIPPING_FALSE = 0;
    public static final int FREESHIPPING_TRUE  = 1;
    
    private String  itemName;
    private float   itemPrice;
    private int     itemQuantity;
    private int     itemCondition;
    //No boolean type in SQLite
    // private boolean itemFreeShipping, changed it to int;
    private int     itemFreeShipping;

    /**
     * @return itemName
     */
    public String getItemName() {
        return itemName;
    }
    /**
     * @param itemName
     *            the itemName to set
     */
    public void setItemName (String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemPrice
     */
    public float getItemPrice () {
    	return itemPrice;
    }
    /**
     * @param itemPrice
     * itemPrice to set
     */
    public void setItemPrice (float itemPrice) {
    	this.itemPrice = itemPrice;
    }
    
    /**
     * @return itemQuantity
     */
    public int getItemQuantity () {
        return itemQuantity;
    }
    /**
     * @param itemQuantity
     *            the itemQuantity to set
     */
    public void setItemQuantity (int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    /**
     * @return the itemCondition
     */
    public int getItemCondition () {
        return itemCondition;
    }
    /**
     * @param itemCondition
     *            the itemCondition to set
     */
    public void setItemCondition (int itemCondition) {
        this.itemCondition = itemCondition;
    }
    
    /**
     * @return the itemFreeShipping
     */
//  SQLite does not have boolean datatype
//    public boolean getItemFreeShipping () {}
    public int getItemFreeShipping () {
    	return itemFreeShipping;
    }
    /**
     * @param itemFreeShipping
     */
//  SQLite does not have boolean datatype
//    public void setItemFreeShipping (boolean itemFreeShipping) {}
    public void setItemFreeShipping (int itemFreeShipping) {
    	this.itemFreeShipping = itemFreeShipping;
    }
}
