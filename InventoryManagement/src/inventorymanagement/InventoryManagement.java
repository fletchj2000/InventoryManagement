package inventorymanagement;

import inventorymanagement.inventory.Inventory;
import inventorymanagement.inventory.InventoryCommandProcessor;
import inventorymanagement.inventory.translator.InventoryTranslator;

/**
 *
 * An application that will load a file, updating an inventory based on the
 * commands provided, and generate a report of the status of the inventory since
 * the previous report.
 */
public class InventoryManagement {

    /**
     * The inventory to use.
     */
    private Inventory inventory;

    /**
     * A command processor.
     */
    private InventoryCommandProcessor processor;

    /**
     * Constructor
     */
    public InventoryManagement() {
        inventory = new Inventory();
        processor = new InventoryCommandProcessor(inventory);
    }

    /**
     * Loads and parses a file containing commands, one on each line of the
     * file. Each command is applied to an inventory object.
     *
     * @param filename Filename to load and parse.
     */
    public void loadFile(String filename) {
        InventoryTranslator translator = new InventoryTranslator(processor);
        translator.loadFile(filename);
    }

    /**
     * @param args the command line arguments. This application does not expect
     * any command-line arguments.
     */
    public static void main(String[] args) {
        InventoryManagement inventoryManagement = new InventoryManagement();
        inventoryManagement.loadFile("resources\\providedinput.txt");
    }

}
