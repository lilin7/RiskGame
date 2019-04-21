package test.models.game;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.game.GameState;
import models.game.Player;
import models.map.Country;

public class GameStateTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		GameState.reset();
		GameState.getInstance().loadMapFromFile (new File("res/001_I72_Ghtroc 720/001_I72_Ghtroc 720.map"));
		//System.out.println(GameState.getInstance().getMap().getCountryList().size());
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testSaveGame() {
		Player player1 = new Player();

		Player player2 = new Player();
		
		GameState.getInstance().getPlayerList().add(player1);

		GameState.getInstance().getPlayerList().add(player2);
		GameState.getInstance().setFirstPlayer();
		GameState.getInstance().randomAssignCountry();
		int numArmy = GameState.getInstance().getMap().getCountryList().get(0).getNumOfArmies();
		String countryName = GameState.getInstance().getMap().getCountryList().get(0).getName();
		
		String fileName = new SimpleDateFormat("yyyyMMddHHmm'.save'").format(new Date());
		String currentPath=System.getProperty("user.dir");
    	String absoluteFilePath = currentPath+"\\save\\"+fileName;
		File file = new File(absoluteFilePath);
		GameState.getInstance().saveGameToFile(file);
		
		try {
			boolean countryBegin=false;
			FileReader reader = new FileReader(file);
		    BufferedReader bufferedReader = new BufferedReader (reader);
		    String line;		    
	    	String[] splitLine;
	    	int numLine = 1;
	    	while ((line=bufferedReader.readLine())!=null) {
		    	if(line.isEmpty()) {
		    		continue;
		    	}
		    	if(line.equals("[Continents]")){
		    		
		    		countryBegin=true;
		    		
		    	}
		    	else if(countryBegin) {
		    		splitLine = line.split(",");
		    		if(splitLine[0].equals(countryName)) {
		    			assertTrue(Integer.parseInt(splitLine[2]) == numArmy);
		    		}
		    		
		    	}
	    	}
		}
		catch (Exception e) {
			
		}
		
	}


}
