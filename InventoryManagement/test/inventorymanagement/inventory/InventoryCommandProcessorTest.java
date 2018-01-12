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
public class InventoryCommandProcessorTest {

    public InventoryCommandProcessorTest() {
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
     * Test of processCommand method, of class InventoryCommandProcessor.
     */
    @Test
    public void testProcessCommand() {
        System.out.println("processCommand");
        Inventory inventory = new Inventory();
        InventoryCommandProcessor instance = new InventoryCommandProcessor(inventory);
        String command = "create item1 5.5 6.6";
        instance.processCommand(command);
        String desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item1			5.50			6.60		0		0.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        String report = inventory.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));

        //Ill formatted create command
        command = "create item2 2.2 1.3 4";
        instance.processCommand(command);
        desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item1			5.50			6.60		0		0.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									0.00\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        report = inventory.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
        
        //Sell without anything there.  Same report.
        command = "updateSell item1 5";
        instance.processCommand(command);
        report = inventory.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
        
        //Ill formatted Buy items command.  Same report
        command = "updateBuy item1 3 4";
        instance.processCommand(command);
        report = inventory.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
        
        //Proper formatted buy items command.
        command = "updateBuy item1 5";
        instance.processCommand(command);
        desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item1			5.50			6.60		5		27.50\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									27.50\n"
                + "Profit since previous report							0.00\n"
                + "\n"
                + "\n";
        report = inventory.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
        
        //Ill formatted sell items command.  Same report.
        command = "updateSell item1";
        instance.processCommand(command);
        report = inventory.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
        
        //Proper sell command
        command = "updateSell item1 3";
        instance.processCommand(command);desiredValue = "              	INVENTORY REPORT\n"
                + "Item Name		Bought At		Sold At		AvailableQty	Value\n"
                + "--------- 		---------		-------		------------	-------\n"
                + "item1			5.50			6.60		2		11.00\n"
                + "---------------------------------------------------------------------------------------\n"
                + "Total value									11.00\n"
                + "Profit since previous report							3.30\n"
                + "\n"
                + "\n";
        report = inventory.generateReport().toString();
        assertTrue(report.contentEquals(desiredValue));
        
    }

}
