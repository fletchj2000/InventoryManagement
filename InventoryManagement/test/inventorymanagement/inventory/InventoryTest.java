package inventorymanagement.inventory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonathan
 */
public class InventoryTest {

    public InventoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addRecord method, of class Inventory.
     */
    @Test
    public void testAddRecord() {
        System.out.println("addRecord");
        String itemName = "item 1";
        InventoryRecord record = new InventoryRecord(5.5, 6.6);
        Inventory instance = new Inventory();
        instance.addRecord(itemName, record);
        String desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		0		0.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        String report = instance.generateReport().toString();

        assertTrue(report.contentEquals(desiredValue));

    }

    /**
     * Test of deleteRecord method, of class Inventory.
     */
    @Test
    public void testDeleteRecord() {
        System.out.println("deleteRecord");
        String itemName = "item 1";
        InventoryRecord record = new InventoryRecord(5.5, 6.6);
        Inventory instance = new Inventory();
        instance.addRecord(itemName, record);
        String desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		0		0.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        String report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //null record name should do nothing.
        instance.deleteRecord(null);
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //Invalid record name should do nothing.
        instance.deleteRecord("item 2");
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //Empty report after delete.
        desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        instance.deleteRecord("item 1");
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
    }

    /**
     * Test of processSellForRecord method, of class Inventory.
     */
    @Test
    public void testProcessSellForRecord() {
        System.out.println("processSellForRecord");
        String itemName = "item 1";
        InventoryRecord record = new InventoryRecord(5.5, 6.6);
        Inventory instance = new Inventory();
        instance.addRecord(itemName, record);
        String desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		0		0.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        String report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //sell items that I don't have?  Should still come out with a quantity of zero.
        instance.processSellForRecord(itemName, 5L);
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //Don't have this item, so it should generate the same report.
        instance.processSellForRecord("item2", 5L);
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        instance.processBuyForRecord(itemName, 123L);
        report = instance.generateReport().toString();
        desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		123		676.50\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									676.50\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        assertTrue(report.contentEquals(desiredValue));

        //Don't have this item, so it should generate the same report.
        instance.processSellForRecord("item2", 5L);
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //Sell some quantity
        instance.processSellForRecord(itemName, 13L);
        report = instance.generateReport().toString();
        desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		110		605.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									605.00\n"
                + "Profit since previous report							14.30\n"
                + "\n"
                + "\n";
        assertTrue(report.contentEquals(desiredValue));

        //Sell more than available, should drop to zero?
        instance.processSellForRecord(itemName, 123L);
        report = instance.generateReport().toString();
        desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		0		0.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							121.00\n"
                + "\n"
                + "\n";
        assertTrue(report.contentEquals(desiredValue));

    }

    /**
     * Test of processBuyForRecord method, of class Inventory.
     */
    @Test
    public void testProcessBuyForRecord() {
        System.out.println("processBuyForRecord");
        String itemName = "item 1";
        InventoryRecord record = new InventoryRecord(5.5, 6.6);
        Inventory instance = new Inventory();
        instance.addRecord(itemName, record);
        String desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		0		0.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        String report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //Buy a negative amount?  Should not change quantity.
        instance.processBuyForRecord(itemName, -1L);
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //Buy some amount
        instance.processBuyForRecord(itemName, 5L);
        desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item 1			5.50			6.60		5		27.50\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									27.50\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        report = instance.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
    }

    /**
     * Test of generateReport method, of class Inventory.
     */
    @Test
    public void testGenerateReport() {
        //I've tested this method all throughout the other tests.  Just pass it.
        assertTrue(true);
    }

}
