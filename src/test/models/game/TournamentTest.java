package test.models.game;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.game.Aggressive;
import models.game.Player;
import models.game.Random;
import models.game.Tournament;
import models.map.Map;

public class TournamentTest {
	Map maps =new Map();
	Player players0 = new Player(new Aggressive());
	Player players1 = new Player(new Random());
	int numOfGames = 2;
	int turns = 20;
	List<Map> mapList = new ArrayList<Map>();
	List<Player> playerList = new ArrayList<Player>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		File fileCorrectMap = new File("res/001_I72_Ghtroc 720/001_I72_Ghtroc 720.map");
		
		maps.loadMapFromFile(fileCorrectMap);
		
		mapList.add(maps);
		players0.setId(0);
		players1.setId(1);
		playerList.add(players0);
		playerList.add(players1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Tournament tournament = new Tournament(mapList, playerList, numOfGames, turns);
		tournament.run();
		String [][] results=tournament.getResults();
		for (String[]s:results)
			for(String ss:s) {
				assertTrue(ss.contains("Aggressive")||ss.contains("Random")||ss.contains("Draw"));
			}
		
	}

}
