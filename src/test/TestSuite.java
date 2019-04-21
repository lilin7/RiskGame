package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.models.map.*;
import test.views.game.AttackViewTest;
import test.models.game.*;
@RunWith(Suite.class)
@SuiteClasses({ContinentTest.class, CountryTests.class, MapTests.class,
	DiceTest.class, AttackTest.class, FortificationTest.class, StrategyTest.class,
	PlayerTest.class, ReinforcementTests.class, TournamentTest.class, AttackViewTest.class, 
	CardTest.class, LoadGameTest.class, GameStateTest.class
	})
/**
 * Test Suite for running all tests
 *
 */
public class TestSuite {

	

}
