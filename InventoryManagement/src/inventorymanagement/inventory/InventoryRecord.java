package inventorymanagement.inventory;

/**
 *
 * A record to hold the quantity, cost price and sell price of an item.
 */
public class InventoryRecord {

    /**
     * The quantity of items in this record.
     */
    private Long quantity;

    /**
     * The cost to purchase the item.
     */
    private Double costPrice;

    /**
     * The desired sell price of the item.
     */
    private Double sellPrice;

    /**
     * Constructor initializing with a base cost price and sell price, as that
     * is part of the command used to construct the item. As the inputs are of
     * type String, converting to Number objects can possibly throw an
     * exception.
     *
     * @param costPrice The cost to purchase the item as a String.
     * @param sellPrice The desired sell price of the item as a String.
     * @throws NumberFormatException
     */
    public InventoryRecord(String costPrice, String sellPrice) throws NumberFormatException {
        this(Double.valueOf(costPrice), Double.valueOf(sellPrice));
    }

    /**
     * Constructor initializing with a base cost price and sell price, as that
     * is part of the command used to construct the item.
     *
     * @param costPrice The cost to purchase the item as a Double.
     * @param sellPrice The desired sell price of the item as a Double.
     */
    public InventoryRecord(double costPrice, double sellPrice) {
        //TODO Should sell prices lower than cost price be allowed? It could be a sale price to get rid of items.
        quantity = new Long(0);
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
    }

    /**
     * Quantity getter.
     *
     * @return Quantity for item in record.
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * Quantity setter.
     *
     * @param quantity New quantity for item in record. If the value is null,
     * then the quantity will be updated to 0. If the value is negative, then
     * the call is ignored.
     */
    public void setQuantity(Long quantity) {

        //TODO Check that the negative quantity being ignored is sufficient, or if another behavior is desired.
        this.quantity = quantity == null ? 0L : (quantity >= 0 ? quantity : this.quantity);
    }

    /**
     * Sell price getter.
     *
     * @return Desired sell price of item.
     */
    public Double getSellPrice() {
        return sellPrice;
    }

    /**
     * Cost price getter.
     *
     * @return Cost to purchase an individual item in this record.
     */
    public Double getCostPrice() {
        return costPrice;
    }

    /**
     * Value getter.
     *
     * @return The value based on the cost and quantity of the item.
     */
    public Double getValue() {
        return costPrice.doubleValue() * quantity.doubleValue();
    }

    /**
     * Formatted cost price.
     *
     * @return A formatted String for the cost of an individual item in this
     * record.
     */
    public String formattedCostPrice() {
        return String.format("%.2f", costPrice.doubleValue());
    }

    /**
     * Formatted sell price.
     *
     * @return A formatted String for the desired sell price of an individual
     * item in this record.
     */
    public String formattedSellPrice() {
        return String.format("%.2f", sellPrice.doubleValue());
    }

    /**
     * Formatted quantity.
     *
     * @return A String the quantity of items in this record.
     */
    public String formattedQuantity() {
        return String.format("%d", quantity.longValue());
    }

    /**
     * Formatted value.
     *
     * @return A formatted String for the value of the items, based on the cost
     * and quantity, in this record.
     */
    public String formattedValue() {
        return String.format("%.2f", getValue());
    }
}
