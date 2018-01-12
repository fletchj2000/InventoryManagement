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
public class InventoryRecordTest {

    public InventoryRecordTest() {
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
     * Test of getQuantity method, of class InventoryRecord.
     */
    @Test
    public void testGetQuantity() {
        System.out.println("getQuantity");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        Long expResult = 0L;
        Long result = instance.getQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of setQuantity method, of class InventoryRecord.
     */
    @Test
    public void testSetQuantity() {
        System.out.println("setQuantity");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        Long expResult = 0L;
        Long result = instance.getQuantity();
        assertEquals(expResult, result);

        instance.setQuantity(5L);
        expResult = 5L;
        result = instance.getQuantity();
        assertEquals(expResult, result);

        //Bad value, no change expected.
        instance.setQuantity(null);
        expResult = 0L;
        result = instance.getQuantity();
        assertEquals(expResult, result);

        //Bad value, no change expected.
        instance.setQuantity(5L);
        instance.setQuantity(-1L);
        expResult = 5L;
        result = instance.getQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSellPrice method, of class InventoryRecord.
     */
    @Test
    public void testGetSellPrice() {
        System.out.println("getSellPrice");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        Double expResult = 6.6;
        Double result = instance.getSellPrice();
        assertEquals(expResult, result);

        boolean success = false;
        try {
            instance = new InventoryRecord("5.5", null);
            success = true;
        } catch (Exception ex) {

        }
        assertFalse(success);
    }

    /**
     * Test of getCostPrice method, of class InventoryRecord.
     */
    @Test
    public void testGetCostPrice() {
        System.out.println("getCostPrice");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        Double expResult = 5.5;
        Double result = instance.getCostPrice();
        assertEquals(expResult, result);

        boolean success = false;
        try {
            instance = new InventoryRecord(null, "6.6");
            success = true;
        } catch (Exception ex) {

        }
        assertFalse(success);
    }

    /**
     * Test of getValue method, of class InventoryRecord.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        Double expResult = 0.0;
        Double result = instance.getValue();
        assertEquals(expResult, result);

        instance.setQuantity(5L);
        expResult = 27.5;
        result = instance.getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of formattedCostPrice method, of class InventoryRecord.
     */
    @Test
    public void testFormattedCostPrice() {
        System.out.println("formattedCostPrice");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        String expResult = "5.50";
        String result = instance.formattedCostPrice();
        assertEquals(expResult, result);
    }

    /**
     * Test of formattedSellPrice method, of class InventoryRecord.
     */
    @Test
    public void testFormattedSellPrice() {
        System.out.println("formattedSellPrice");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        String expResult = "6.60";
        String result = instance.formattedSellPrice();
        assertEquals(expResult, result);
    }

    /**
     * Test of formattedQuantity method, of class InventoryRecord.
     */
    @Test
    public void testFormattedQuantity() {
        System.out.println("formattedQuantity");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        String expResult = "0";
        String result = instance.formattedQuantity();
        assertEquals(expResult, result);

        instance.setQuantity(20L);
        expResult = "20";
        result = instance.formattedQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of formattedValue method, of class InventoryRecord.
     */
    @Test
    public void testFormattedValue() {
        System.out.println("formattedValue");
        InventoryRecord instance = new InventoryRecord(5.5, 6.6);
        String expResult = "0.00";
        String result = instance.formattedValue();
        assertEquals(expResult, result);

        instance.setQuantity(20L);
        expResult = "110.00";
        result = instance.formattedValue();
        assertEquals(expResult, result);
    }

}
