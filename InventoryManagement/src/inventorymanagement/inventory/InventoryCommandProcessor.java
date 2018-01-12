package inventorymanagement.inventory;

/**
 *
 * The inventory command processor will take one line commands and apply the
 * changes to the inventory object.
 */
public class InventoryCommandProcessor {

    /**
     * The internal inventory state
     */
    private Inventory inventory;

    /**
     * Default constructor for the InventoryCommandProcessor. This will create a
     * new inventory object.
     */
    public InventoryCommandProcessor() {
        this(new Inventory());
    }

    /**
     * Constructor used to initialize the inventory object to one that was
     * created previously.
     *
     * @param inventory Inventory to apply changes against.
     */
    public InventoryCommandProcessor(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Process an individual command. If the command does not have the proper
     * format, then the command is ignored.
     *
     * @param command Command to process.
     */
    public void processCommand(String command) {
        String[] splitStr = command.trim().split("\\s+");

        //TODO For better object orientation, convert the commands themselves to individual classes that act as handlers.  Register handlers to a list in the processor, then let each handler try to process the command.
        //TODO Add some kind of information to user that command was ignored?
        //TODO Add some kind of feedback that the command was processed?  As this wasn't part of the task, I did not incorporate it; but, this is not very user-friendly.
        if (splitStr.length > 0) {

            switch (splitStr[0]) {
                case "create":
                    //TODO Can I figure out a scheme to address spaces in the name?  Surround with quotes?
                    if (splitStr.length == 4) {

                        String name = splitStr[1];
                        String costPrice = splitStr[2];
                        String sellPrice = splitStr[3];

                        try {
                            InventoryRecord record = new InventoryRecord(costPrice, sellPrice);
                            this.inventory.addRecord(name, record);
                        } catch (NullPointerException ex) {
                            //A costPrice or sellPrice was null.  Ignore the item.   
                        } catch (NumberFormatException ex) {
                            //A costPrice or sellPrice was not formatted properly.  Ignore the item. 
                        }
                    }
                    break;

                case "delete":
                    if (splitStr.length == 2) {

                        String name = splitStr[1];

                        this.inventory.deleteRecord(name);
                    }
                    break;
                case "updateBuy":
                    if (splitStr.length == 3) {

                        String name = splitStr[1];
                        Long quantity;

                        try {
                            quantity = Long.valueOf(splitStr[2]);
                            this.inventory.processBuyForRecord(name, quantity);
                        } catch (NullPointerException ex) {
                            //A quantity was null.  Ignore the item.   
                        } catch (NumberFormatException ex) {
                            //A quantity was not formatted properly.  Ignore the item. 
                        }
                    }
                    break;
                case "updateSell":
                    if (splitStr.length == 3) {

                        String name = splitStr[1];
                        Long quantity;

                        try {
                            quantity = Long.valueOf(splitStr[2]);
                            this.inventory.processSellForRecord(name, quantity);
                        } catch (NullPointerException ex) {
                            //A quantity was null.  Ignore the item.   
                        } catch (NumberFormatException ex) {
                            //A quantity was not formatted properly.  Ignore the item. 
                        }
                    }
                    break;

                case "report":
                    StringBuffer buffer = inventory.generateReport();

                    //TODO Incorporate a class for information output, and store in the command processor.  This will allow us to use either files or console outputs by replacing the instance of the output class.
                    System.out.print(buffer.toString());

                    break;

                default:
                //TODO unknown command.   Print something out?
            }
        }
    }
}
