package inventorymanagement.inventory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The current status of the inventory
 *
 */
public class Inventory {

    /**
     * The items currently in the inventory.
     */
    private final Map<String, InventoryRecord> items;

    /**
     * Information about the changes to the inventory since the last report was
     * generated.
     */
    private final Map<String, InventoryRecord> reportableChanges;

    /**
     * A list of the deleted items to be used to calculate profit.
     */
    private Double deletedItemsProfitLost;

    /**
     * Constructor for the Inventory object.
     */
    public Inventory() {
        items = new HashMap<>();
        reportableChanges = new HashMap<>();
        deletedItemsProfitLost = new Double(0.0);
    }

    /**
     * Add a new record to the inventory list.
     *
     * If the previous record already existed, it will be replaced with the
     * current record.
     *
     * @param itemName The name of the item to add to the inventory list.
     * @param record The new item to add to the inventory list.
     */
    public void addRecord(String itemName, InventoryRecord record) {

        //An itemName of null is an unknown state for the system, so ignore
        //when that occurs.
        if (itemName != null) {
            items.put(itemName, record);
        }
    }

    /**
     * Delete a record from the inventory.
     *
     * If the record does not exist, then the operation will be ignored.
     *
     * @param itemName The name of the item to remove from the inventory list.
     */
    public void deleteRecord(String itemName) {
        InventoryRecord record = items.remove(itemName);

        if (record != null) {
            deletedItemsProfitLost += record.getValue();
        }
    }

    /**
     * Deducts the quantity from inventory, and adds to the profit. If there are
     * more items sold than in inventory, then the quantity and profit will be
     * based off of all the items in inventory.
     *
     * @param itemName The name of the item to modify.
     * @param quantity The number of items sold.
     */
    public void processSellForRecord(String itemName, Long quantity) {

        long usedQuantity = quantity;
        InventoryRecord record = items.get(itemName);
        if (record != null) {
            if (usedQuantity > record.getQuantity()) {
                usedQuantity = record.getQuantity();
            }
            record.setQuantity(record.getQuantity() - usedQuantity);
            Double sellPrice = record.getSellPrice();
            Double buyPrice = record.getCostPrice();

            record = reportableChanges.get(itemName);
            if (record == null) {
                record = new InventoryRecord(sellPrice - buyPrice, 0.0);
                reportableChanges.put(itemName, record);
            }
            record.setQuantity(record.getQuantity() + usedQuantity);
        }
    }

    /**
     * Increases the quantity for the item in the inventory.
     *
     * @param itemName
     * @param quantity
     */
    public void processBuyForRecord(String itemName, Long quantity) {
        InventoryRecord record = items.get(itemName);

        if (record != null && quantity > 0) {
            record.setQuantity(record.getQuantity() + quantity);
        }
    }

    /**
     * Generate a report returned as a StringBuffer to be used however the
     * caller wishes. This will also reset the state of the changes in
     * inventory.
     *
     * @return The StringBuffer containing the report.
     */
    public StringBuffer generateReport() {
        StringBuffer buffer = new StringBuffer();

        generateReportHeader(buffer);
        generateReportBody(buffer);
        reportableChanges.clear();
        deletedItemsProfitLost = 0.0;
        return buffer;
    }

    /**
     * Helper method with generating the report header.
     *
     * @param buffer The StringBuffer to add information to for the report.
     */
    private void generateReportHeader(StringBuffer buffer) {

        buffer.append("              	INVENTORY REPORT\n");
        buffer.append("Item Name\t\tBought At\t\tSold At\t\tAvailableQty\tValue\n");
        buffer.append("--------- \t\t---------\t\t-------\t\t------------\t-------\n");
    }

    /**
     * Helper method with generating the report body.
     *
     * @param buffer The StringBuffer to add information to for the report.
     */
    private void generateReportBody(StringBuffer buffer) {

        Double totalValue = 0.0;
        Double totalProfit = 0.0;

        Iterator it = items.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, InventoryRecord> pair = (Map.Entry<String, InventoryRecord>) it.next();

            //Add the name field
            String string = pair.getKey();
            int numberOfTabs = 3;
            numberOfTabs -= string.length() / 8;
            generateItemField(buffer, string, numberOfTabs);

            InventoryRecord record = pair.getValue();

            //Add the cost price field
            string = record.formattedCostPrice();
            numberOfTabs = 3;
            numberOfTabs -= string.length() / 8;
            generateItemField(buffer, string, numberOfTabs);

            //Add the sell price field  
            string = record.formattedSellPrice();
            numberOfTabs = 2;
            numberOfTabs -= string.length() / 8;
            generateItemField(buffer, string, numberOfTabs);

            //Add the quantity field
            string = record.formattedQuantity();
            numberOfTabs = 2;
            numberOfTabs -= string.length() / 8;
            generateItemField(buffer, string, numberOfTabs);

            //Add the Value field
            string = record.formattedValue();
            buffer.append(string + "\n");

            //Keep a tally of the total value for reporting later
            totalValue += record.getValue();
        }

        //Add the separator between items and total value
        buffer.append("---------------------------------------------------------------------------------------\n");
        buffer.append("Total value\t\t\t\t\t\t\t\t\t" + String.format("%.2f", totalValue.doubleValue()) + "\n");

        //Calculate the profit and add to the buffer.
        it = reportableChanges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, InventoryRecord> pair = (Map.Entry<String, InventoryRecord>) it.next();

            //Keep a tally of the total profit for reporting later
            totalProfit += pair.getValue().getValue();
        }

        //Deduct the profits lost.
        totalProfit -= deletedItemsProfitLost;

        buffer.append("Profit since previous report\t\t\t\t\t\t\t" + String.format("%.2f", totalProfit.doubleValue()) + "\n\n\n");
    }

    /**
     *
     * Helper method with generating individual fields. This will help ensure
     * better formatting of the report.
     *
     * @param buffer The StringBuffer to add information to for the report.
     * @param string The value to be printed to the field.
     * @param numberOfTabs Tab spacing added afterward to assist with
     * formatting.
     */
    private void generateItemField(StringBuffer buffer, String string, int numberOfTabs) {
        buffer.append(string);
        for (int i = 0; i < numberOfTabs; ++i) {
            buffer.append('\t');
        }
    }
}
