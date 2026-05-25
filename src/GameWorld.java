public class GameWorld {
    private static GameWorld instance;
    public Room startingRoom;
    private Room townSquar;
    private Room forest;
    private Room mountains;
    private Room cave;
    private Room market;
    private Room tavern;
    private Room riverbank;

    private GameWorld(){
        startingRoom = new Room("SpawnRoom", "All the new players are spawned here.");
        townSquar = new Room("TownSquare", "A busy square eith Fountain in the middle.");
        forest = new Room("DarkForest", "Tall Trees blocks sunlight.");
        mountains = new Room("Mountains", "large mountains.");
        cave = new Room("Cave", "dark cave may consist of monsters");
        market = new Room("Market", "A place to buy and sell items.");
        tavern = new Room("Tavern", "A pub");
        riverbank = new Room("Riverbank", "riverbank");

        startingRoom.west = townSquar;
        townSquar.east = startingRoom;

        townSquar.north = forest;
        forest.south = townSquar;

        townSquar.south = market;
        market.north = townSquar;

        townSquar.west = tavern;
        tavern.east = townSquar;

        market.south = riverbank;
        riverbank.north = market;

        forest.west = cave;
        cave.east = forest;

        forest.east = mountains;
        mountains.west = forest;
    }

    public static GameWorld getInstance(){
        if (instance == null) instance = new GameWorld();
        return instance;
    }
}
