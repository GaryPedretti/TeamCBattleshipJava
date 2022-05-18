package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;
import org.scrum.psd.battleship.controller.dto.ShipState;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
    private static List<ShipState> myFleet;
    private static List<ShipState> enemyFleet;
    private static ColoredPrinter console;
    public static final String ANSI_WATER_COLOR = "\u001B[34m";
    public static final String ANSI_HIT_COLOR = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    
    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).background(Ansi.BColor.BLACK).foreground(Ansi.FColor.WHITE).build();

        console.setForegroundColor(Ansi.FColor.MAGENTA);
        console.println("                                     |__");
        console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-61/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.setForegroundColor(Ansi.FColor.WHITE);

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);
        
        console.print("\033[2J\033[;H");
        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");
        console.println("************************************************");

        do {
            console.println("");
            console.println("Player, it's your turn");
            console.println("Enter coordinates for your shot OR type 'quit' to leave the game");

            String playerInput = scanner.next(); 
            
            if("quit".equalsIgnoreCase(playerInput)){
                console.println("You have decided to quit. Bye!");
                break;
            } 
            Position position = parsePosition(playerInput);

            while (position == null){
                console.println("This position is outside the playing field. Repeat your shot.");
                console.println("Enter coordinates for your shot :");
                position = parsePosition(scanner.next());
            }

            boolean isHit = GameController.checkIsHit(enemyFleet, position);
            printHitOrMissText(isHit, position);

            if (isDone(enemyFleet)) {
                console.println("Player, you win!!");
                return;
            }

          
            position = getRandomPosition();
            isHit = GameController.checkIsHit(myFleet, position);
            console.println("");
            console.println(String.format("Computer shot at %s%s and %s", position.getColumn(), position.getRow(), isHit ? "hit your ship !" : "missed"));
            printHitOrMissText(isHit, position);

            if (isDone(myFleet)) {
                console.println("Player, you lose :(");
                return;
            }

        } while (true);
    }

    public static void printHitOrMissText(boolean isHit, Position position) {
     if (isHit) {
            beep();
         
            sendHitColorText("                \\         .  ./");
            sendHitColorText("              \\      .:\" \";'.:..\" \"   /");
            sendHitColorText("                  (M^^.^~~:.'\" \").");
            sendHitColorText("            -   (/  .    . . \\ \\)  -");
            sendHitColorText("               ((| :. ~ ^  :. .|))");
            sendHitColorText("            -   (\\- |  \\ /  |  /)  -");
            sendHitColorText("                 -\\  \\     /  /-");
            sendHitColorText("                   \\  \\   /  /");
            sendHitColorText("                     \\ \\ /  /");
            
            sendHitColorText("Hit on " + position.toString() + " !");
            console.print("***************************************");
            console.println("\r\n");
        } else {
            sendWaterColorText("                \\         .  ./");
            sendWaterColorText("              \\      .:\" \";'.:..\" \"   /");
            sendWaterColorText("                  (M^^.^~~:.'\" \").");
            sendWaterColorText("            -   (/  .    . . \\ \\)  -");
            sendWaterColorText("               ((| :. ~ ^  :. .|))");
            sendWaterColorText("            -   (\\- |  \\ /  |  /)  -");
            sendWaterColorText("                 -\\  \\     /  /-");
            sendWaterColorText("                   \\  \\   /  /");
            sendWaterColorText("                     \\ \\ /  /");
            
            sendWaterColorText("Miss on " + position.toString() + " !");
            console.print("***************************************");
            console.println("\r\n");
        }
     }

    private static void sendHitColorText(String hitText) {
        console.println(ANSI_HIT_COLOR + hitText + ANSI_RESET);
    }

    private static void sendWaterColorText(String hitText) {
        console.println(ANSI_WATER_COLOR + hitText + ANSI_RESET);
    }

    private static void beep() {
        console.print("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1, input.length() ));
        if (number > 8) return null;


        return new Position(letter, number);
    }
    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
       // InitializeMyFleet();
       autoInitializeFleetWithStaticValues();
        InitializeEnemyFleet();
    }

   private static void autoInitializeFleetWithStaticValues() {
        List<Ship> fleet = GameController.initializeShips();

        fleet.get(0).getPositions().add(new Position(Letter.B, 4));
        fleet.get(0).getPositions().add(new Position(Letter.B, 5));
        fleet.get(0).getPositions().add(new Position(Letter.B, 6));
        fleet.get(0).getPositions().add(new Position(Letter.B, 7));
        fleet.get(0).getPositions().add(new Position(Letter.B, 8));

        fleet.get(1).getPositions().add(new Position(Letter.E, 5));
        fleet.get(1).getPositions().add(new Position(Letter.E, 6));
        fleet.get(1).getPositions().add(new Position(Letter.E, 7));
        fleet.get(1).getPositions().add(new Position(Letter.E, 8));

        fleet.get(2).getPositions().add(new Position(Letter.A, 3));
        fleet.get(2).getPositions().add(new Position(Letter.B, 3));
        fleet.get(2).getPositions().add(new Position(Letter.C, 3));

        fleet.get(3).getPositions().add(new Position(Letter.F, 8));
        fleet.get(3).getPositions().add(new Position(Letter.G, 8));
        fleet.get(3).getPositions().add(new Position(Letter.H, 8));

        fleet.get(4).getPositions().add(new Position(Letter.C, 5));
        fleet.get(4).getPositions().add(new Position(Letter.C, 6));

        myFleet = ShipState.from(fleet);

       console.println("Autoset up player fleet!");
   }

    private static void InitializeMyFleet() {
        
        Scanner scanner = new Scanner(System.in);
        List<Ship> fleet = GameController.initializeShips();

        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : fleet) {
            console.println("");
            console.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));

                String positionInput = scanner.next();
                ship.addPosition(positionInput);
            }
        }
        myFleet = ShipState.from(fleet);
    }

    private static void InitializeEnemyFleet() {
        List<Ship> fleet = GameController.initializeShips();

        fleet.get(0).getPositions().add(new Position(Letter.B, 4));
        fleet.get(0).getPositions().add(new Position(Letter.B, 5));
        fleet.get(0).getPositions().add(new Position(Letter.B, 6));
        fleet.get(0).getPositions().add(new Position(Letter.B, 7));
        fleet.get(0).getPositions().add(new Position(Letter.B, 8));

        fleet.get(1).getPositions().add(new Position(Letter.E, 5));
        fleet.get(1).getPositions().add(new Position(Letter.E, 6));
        fleet.get(1).getPositions().add(new Position(Letter.E, 7));
        fleet.get(1).getPositions().add(new Position(Letter.E, 8));

        fleet.get(2).getPositions().add(new Position(Letter.A, 3));
        fleet.get(2).getPositions().add(new Position(Letter.B, 3));
        fleet.get(2).getPositions().add(new Position(Letter.C, 3));

        fleet.get(3).getPositions().add(new Position(Letter.F, 8));
        fleet.get(3).getPositions().add(new Position(Letter.G, 8));
        fleet.get(3).getPositions().add(new Position(Letter.H, 8));

        fleet.get(4).getPositions().add(new Position(Letter.C, 5));
        fleet.get(4).getPositions().add(new Position(Letter.C, 6));

        enemyFleet = ShipState.from(fleet);
    }

    static boolean isDone(List<ShipState> fleet) {
        Iterator<ShipState> it = fleet.iterator();
        while (it.hasNext()) {
            ShipState ship = it.next();
            if (ship.isSunk()) {
                it.remove();
            }
        }
        return fleet.isEmpty();
    }
}
