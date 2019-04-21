package test.models.map;
import static org.junit.Assert.*;

import models.game.GameState;
import models.map.Continent;
import models.map.Country;
import models.map.Map;

import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;


/**
 * This class tests Validity Of Map Files in Map Model
 *
 */


public class MapTests  {
	File selectedFile;
	File fileCorrectMap;
	File fileFalseConnection;
	File fileFalseContinent;
	Map map;

	/**
	 * Loading Map Files
	 */
	@Before
	public void InitiateMap() {


		String currentPath=System.getProperty("user.dir");
		//this is the correct map
		fileCorrectMap=new File(currentPath+"\\TestCaseMapFiles\\AlabamaCorrect.map");
		//there is a wrong connection for country, one of country does not exist.
		fileFalseConnection=new File(currentPath+"\\TestCaseMapFiles\\IranFalseConnection.MAP");
		//there is a wrong continent. one of continent does not exist.
		fileFalseContinent=new File(currentPath+"\\TestCaseMapFiles\\AlabamaFalseContinent.map");
		
		map = new Map();
			
	}			

 
				

   @Test
   public void testCorrectMap() {
	   
		boolean resultCorrectMap = map.loadMapFromFile(fileCorrectMap);
	    assertTrue(resultCorrectMap);
	   
   }
  @Test
   public void testFalseConnection() {
	   
	   boolean resultFalseConnection = map.loadMapFromFile(fileFalseConnection);
	   assertFalse(resultFalseConnection);
   }
  @Test
  public void testFalseContinent() {
	   
	   boolean resultFalseContinent = map.loadMapFromFile(fileFalseContinent);
	   assertFalse(resultFalseContinent);
   }
  
  @Test
  public void testMapValidationEmptyMap() {
	  assertFalse(map.isValid());
  }
  
  @Test
  public void testMapValidationMapHasNoContinent() {
	  map.setAuthor("Xinyan");
	  map.setImage("test.img");
	  map.setContinentList(new ArrayList<Continent>());
	  assertFalse(map.isValid());
  }
  
  @Test
  public void testMapValidationMapHasNoCountry() {
	  map.setAuthor("Xinyan");
	  map.setImage("test.img");
	  map.addContinent(new Continent("Test",1));
	  map.setCountryList(new ArrayList<Country>());
	  assertFalse(map.isValid());

  }
  
  @Test
  public void testMapValidationMapNotConnected() {
	  map.setAuthor("Xinyan");
	  map.setImage("test.img");
	  Continent c1 = new Continent("1",1);
	  Continent c2 = new Continent("2",2);
	  Country country1 = new Country("Test1");
	  Country country2 = new Country("Test2");
	  country1.setContinent(c1);
	  country2.setContinent(c2);
	  
	  map.addCountry(country1);
	  map.addCountry(country2);
	  assertFalse(map.isConnected());
  }
   
 
}