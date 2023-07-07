import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import engine.core.MarioGame;
import engine.core.MarioResult;

public class PlayAstar {
    public static void printResults(MarioResult result) {
        System.out.println("****************************************************************");
        System.out.println("Game Status: " + result.getGameStatus().toString() +
                " Percentage Completion: " + result.getCompletionPercentage());
        System.out.println("Lives: " + result.getCurrentLives() + " Coins: " + result.getCurrentCoins() +
                " Remaining Time: " + (int) Math.ceil(result.getRemainingTime() / 1000f));
        System.out.println("Mario State: " + result.getMarioMode() +
                " (Mushrooms: " + result.getNumCollectedMushrooms() + " Fire Flowers: " + result.getNumCollectedFireflower() + ")");
        System.out.println("Total Kills: " + result.getKillsTotal() + " (Stomps: " + result.getKillsByStomp() +
                " Fireballs: " + result.getKillsByFire() + " Shells: " + result.getKillsByShell() +
                " Falls: " + result.getKillsByFall() + ")");
        System.out.println("Bricks: " + result.getNumDestroyedBricks() + " Jumps: " + result.getNumJumps() +
                " Max X Jump: " + result.getMaxXJump() + " Max Air Time: " + result.getMaxJumpAirTime());
        System.out.println("****************************************************************");
    }

    public static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException e) {
        }
        return content;
    }

    public static String playAstar(MarioGame game, String levelPath, boolean render) {
        System.out.println("Running Astar agent with render: " + render);
        System.out.println("=========================");
        MarioResult result = game.playAstar(new agents.robinBaumgarten.Agent(), getLevel(levelPath), 200, render);
        printResults(result);
        return result.getAStarResult();
    }
    public static String playAstar(MarioGame game, String levelPath, boolean render, String imgDir) {
        System.out.println("Running Astar agent with render: " + render);
        System.out.println("=========================");
        MarioResult result = game.playAstar(new agents.robinBaumgarten.Agent(), getLevel(levelPath), 200, render, imgDir);
        printResults(result);
        return result.getAStarResult();
    }

    public Set<String> listFilesUsingFilesList(String dir) throws IOException {
    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
        return stream
          .filter(file -> !Files.isDirectory(file))
          .map(Path::getFileName)
          .map(Path::toString)
          .collect(Collectors.toSet());
    }
}

    public static void main(String[] args) {
        String levelPaths = "./levels/evaluation/level/";
        String AStarDir = "./levels/evaluation/astar/";

        File tmpLvlPath = new File(levelPaths);
        String levels[] = tmpLvlPath.list();
        for(int i = 0; i < levels.length; i++) {
            MarioGame game = new MarioGame();
            String levelPath = levelPaths + levels[i];
            String res = playAstar(game, levelPath, true);
            try {
                File lvl = new File(levelPath);
                var fAStar = new FileWriter(AStarDir + lvl.getName());
                fAStar.write(res);
                fAStar.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}


// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;

// import engine.core.MarioGame;
// import engine.core.MarioResult;

// public class PlayAstar {
//     public static void printResults(MarioResult result) {
//         System.out.println("****************************************************************");
//         System.out.println("Game Status: " + result.getGameStatus().toString() +
//                 " Percentage Completion: " + result.getCompletionPercentage());
//         System.out.println("Lives: " + result.getCurrentLives() + " Coins: " + result.getCurrentCoins() +
//                 " Remaining Time: " + (int) Math.ceil(result.getRemainingTime() / 1000f));
//         System.out.println("Mario State: " + result.getMarioMode() +
//                 " (Mushrooms: " + result.getNumCollectedMushrooms() + " Fire Flowers: " + result.getNumCollectedFireflower() + ")");
//         System.out.println("Total Kills: " + result.getKillsTotal() + " (Stomps: " + result.getKillsByStomp() +
//                 " Fireballs: " + result.getKillsByFire() + " Shells: " + result.getKillsByShell() +
//                 " Falls: " + result.getKillsByFall() + ")");
//         System.out.println("Bricks: " + result.getNumDestroyedBricks() + " Jumps: " + result.getNumJumps() +
//                 " Max X Jump: " + result.getMaxXJump() + " Max Air Time: " + result.getMaxJumpAirTime());
//         System.out.println("****************************************************************");
//     }

//     public static String getLevel(String filepath) {
//         String content = "";
//         try {
//             content = new String(Files.readAllBytes(Paths.get(filepath)));
//         } catch (IOException e) {
//         }
//         return content;
//     }

//     public static void playAstar(MarioGame game, String level, boolean render) {
//         System.out.println("Running Astar agent with render: " + render);
//         System.out.println("=========================");
//         printResults(game.playAstar(new agents.robinBaumgarten.Agent(), level, 200, render));
//     }
//     public static void playAstar(MarioGame game, String level, boolean render, String imgDir) {
//         System.out.println("Running Astar agent with render: " + render);
//         System.out.println("=========================");
//         printResults(game.playAstar(new agents.robinBaumgarten.Agent(), level, 200, render, imgDir));
//     }

//     public static void main(String[] args) {
//         MarioGame game = new MarioGame();
//         boolean render = true;
//         String level = "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\r\n" + //
//                 "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\r\n" + //
//                 "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\r\n" + //
//                 "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\r\n" + //
//                 "----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\r\n" + //
//                 "----------------------------------------------------------------------------------g-----------------------------------------------------------------------------------------------------------------------\r\n" + //
//                 "----------------------!---------------------------------------------------------SSSSSSSS---SSS!--------------@-----------SSS----S!!S--------------------------------------------------------##------------\r\n" + //
//                 "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------###------------\r\n" + //
//                 "-------------------------------------------------------------------------------g----------------------------------------------------------------------------------------------------------####------------\r\n" + //
//                 "----------------------------------------------------------------1------------------------------------------------------------------------------------------------------------------------#####------------\r\n" + //
//                 "----------------!---S@S!S---------------------tt---------tt------------------S@S--------------C-----SU----!--!--!-----S----------SS------#--#----------##--#------------SS!S------------######------------\r\n" + //
//                 "--------------------------------------tt------tt---------tt-----------------------------------------------------------------------------##--##--------###--##--------------------------#######------------\r\n" + //
//                 "----------------------------tt--------tt------tt---------tt----------------------------------------------------------------------------###--###------####--###-----tt--------------tt-########--------F---\r\n" + //
//                 "---M-----------------g------tt--------tt-g----tt-----g-g-tt------------------------------------g-g--------k-----------------gg-g-g----####--####----#####--####----tt---------gg---tt#########--------#---\r\n" + //
//                 "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--XXXXXXXXXXXXXXX---XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\r\n" + //
//                 "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--XXXXXXXXXXXXXXX---XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
//         playAstar(game, level, render);
//     }
// }
