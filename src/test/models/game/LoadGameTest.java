package test.models.game;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import models.game.GameState;
import models.game.GameState.Phase;

public class LoadGameTest {

	File fileGame;

	@Before
	public void setUp(){
		GameState.reset();
		String currentPath=System.getProperty("user.dir");
		fileGame=new File(currentPath+"\\save\\201811291348.save");
		
		GameState.getInstance().loadGameFromFile(fileGame);		
	}
	
	@Test
	public void testNumberOfPlayers() {
		assertEquals(GameState.getInstance().getPlayerList().size(),2);
	}

	@Test
	public void testGamePhase() {
		assertEquals(GameState.getInstance().getPhase(),Phase.ATTACK);
	}

	@Test
	public void testCurrentPlayer() {
		assertEquals(GameState.getInstance().getCurrentPlayer().getId(),0);
	}
	
	
}
