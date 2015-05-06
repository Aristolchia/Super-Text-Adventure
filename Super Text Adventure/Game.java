/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    public Room currentRoom;
    boolean complete = false;
    boolean activation = false;
    int timeLimit = 40;
    int t = 0;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms(complete);
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms(boolean c)
    {
        Room outside, foyer, coatCloset, gazeebo, centralHallway, staircase, diningHall, kitchen, ballroom, secondFloorHallway, billiardsRoom, servantQuarters, wardrobe, masterBedroom, poolRoom;
        c = complete;
        // create the rooms
        outside = new Room("Outside", "You are surrounded by a dense forrest, in front of you stands a massive mansion, basking in the moonlight");
        foyer = new Room("In the Foyer", "The mansion's foyer has a marble floor with a soft, crimson carpet. A fountain spits out muddy looking water");
        coatCloset = new Room("In the coat closet", "A small, stuffy closet filled with old coats");
        gazeebo = new Room("In the gazeebo", "A beautiful elevated mahogany gazeebo with a set of comfortable chairs");
        centralHallway = new Room("In the large central hallway of the mansion", "A carpeted hallway decorated with all kinds of paintings");
        staircase = new Room("In the staircase connecting the first and second floors", "A stone staircase that makes a sharp 90 degree turn that indicates the entrance to the second floor to the north");
        diningHall = new Room("In the mansion's Dining Hall", "A massive wooden table accompanied by a hanging chandelier and decorated with fine silverware, plates, and wine glasses");
        kitchen = new Room("In the large Kitchen", "A kitchen you would expect to find in the back of a 5 star restaraunt, the smell of rotting food emenates from the locked freezer");
        ballroom = new Room("In the Ballroom", "A lavish ballroom complete with tables for drinks, and an untouched dance floor");
        secondFloorHallway = new Room("In the smaller second floor hallway", "A short hallway illuminated by the moonlight through the windows");
        billiardsRoom = new Room("In the Billiards room", "A small room sporting a gold trimmed billiards table with the balls set up, no sign of any sticks, however");
        servantQuarters = new Room("In the servant's quarters", "A series of beds seperated by small desks");
        wardrobe = new Room("In the master's wardrobe", "A wardrobe filled with elaborate robes, suits, and gowns");
        masterBedroom  = new Room("In the large master bedroom", "A furnished bedroom with a made king sized bed");
        poolRoom = new Room("In the Pool room", "An indoor pool big enough to host parties at, upon further inspection, there is a trapdoor that runs down the length of the house");
        
        
        // initialise room exits
        outside.setExit("north", foyer);
        foyer.setExit("north", centralHallway);
        foyer.setExit("east", gazeebo);
        foyer.setExit("west", coatCloset);
        centralHallway.setExit("south", foyer);
        centralHallway.setExit("north", ballroom);
        centralHallway.setExit("west", staircase);
        centralHallway.setExit("east", diningHall);
        gazeebo.setExit("east", foyer);
        coatCloset.setExit("west", foyer);
        staircase.setExit("east", centralHallway);
        staircase.setExit("north", secondFloorHallway);
        diningHall.setExit("west", centralHallway);
        diningHall.setExit("north", kitchen);
        kitchen.setExit("south", diningHall);
        kitchen.setExit("north", ballroom);
        ballroom.setExit("south", centralHallway);
        ballroom.setExit("east", kitchen);
        secondFloorHallway.setExit("south", staircase);
        secondFloorHallway.setExit("north", masterBedroom);
        secondFloorHallway.setExit("east", servantQuarters);
        secondFloorHallway.setExit("west", billiardsRoom);
        servantQuarters.setExit("west", secondFloorHallway);
        servantQuarters.setExit("north", wardrobe);
        wardrobe.setExit("south", servantQuarters);
        wardrobe.setExit("west", masterBedroom);
        masterBedroom.setExit("south", secondFloorHallway);
        masterBedroom.setExit("east", wardrobe);
        billiardsRoom.setExit("east", secondFloorHallway);
        billiardsRoom.setExit("north", poolRoom);
        poolRoom.setExit("south", billiardsRoom);
        poolRoom.setExit("west", outside);
        currentRoom = outside;  // start game outside
        if (complete == true)
        {
            foyer.setExit("south", outside);
        }
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        
        boolean finished = false;
        while (! finished) {
            if(activation == false && currentRoom.getShortDescription() == "In the Foyer")
            {
                System.out.println("As you Enter the Foyer, the doors close and lock behind you. The smell of gas fills the room, this mansion was booby-trapped! There's only a matter of time until the gas fills the mansion!");
                activation = true;
            }
            if(activation == true && t<timeLimit && complete != true)
            {
                t++;
            }
            else if(t == timeLimit)
            {
                System.out.println("You have suffocated, Game over");
                finished = true;
            }
            if(t == timeLimit-10)
            {
                System.out.println("The air is getting heavy with gas, you should hurry and find your way out of there!");
            }
            if(activation == true && currentRoom.getShortDescription() == "Outside")
            {
                System.out.println("You've escaped the mansion! after a few hours pass, any trace of the gas is gone, you are now free to roam the mansion");
                complete = true;
                createRooms(complete);
            }
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Super Text Adventure");
        System.out.println("Super Text Adventure is a super cool game for cooler people!");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
            
            case LOOK:
                System.out.println(currentRoom.getDDescription());
                break;
                
            case SMELL:
                if(currentRoom.getShortDescription() == "In the large Kitchen")
                {
                    System.out.println("The stink of rotting food fills the air");
                }
                if(activation == false)
                {
                    System.out.println("Nothing smells out of the ordinary");
                }
                else if(activation == true && complete == false)
                {
                    System.out.println("Based on the thickness of the gas in the air, you believe that you have about " + (timeLimit-t) + " minutes until you run out of air");
                }
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You stumbled upon a massive, abandoned mansion in the woods");
        System.out.println("maybe you should explore it?");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
