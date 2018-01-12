package inventorymanagement.inventory.translator;

import inventorymanagement.inventory.InventoryCommandProcessor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Load a file containing commands to modify the inventory and apply those
 * commands to the inventory. This translator can be applied cumulatively from
 * different files.
 */
public class InventoryTranslator {

    /**
     * Translator's inventory in its current state after all file loading has
     * been applied.
     */
    private final InventoryCommandProcessor inventoryProcessor;

    /**
     * Default constructor for the inventory translator.
     */
    public InventoryTranslator() {
        this(new InventoryCommandProcessor());
    }

    /**
     * Constructor assigning an InventoryCommandProcessor to use.
     *
     * @param processor
     */
    public InventoryTranslator(InventoryCommandProcessor processor) {
        inventoryProcessor = processor;
    }

    /**
     * Loads and parses a file containing commands, one on each line of the
     * file. Each command is applied to an inventory object.
     *
     * @param filename Filename to load and parse.
     */
    public void loadFile(String filename) {

        //Open the file for loading line-by-line.
        File file = new File(filename);
        try {

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            //Process each line until the end of the file.
            while ((line = bufferedReader.readLine()) != null) {
                inventoryProcessor.processCommand(line);
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InventoryTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InventoryTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
