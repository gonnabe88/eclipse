package com.swmaestro.tdd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BowlingTest {

	private Game bowling;

	@Before
	public void setup()  throws Exception
	{
		bowling = new Game();
	}
	
	@Test
	public void gutterGame() {		
		rollMany(20, 0);
		assertEquals(0, bowling.score());
	}

	protected void rollMany(int totalRoll, int pins) {
		for( int i=0 ; i<totalRoll ; i++)
		{
			bowling.roll(pins);
		}
	}
	
	@Test
	public void testAllOnes() {
		
		rollMany(20, 1);
		
		assertEquals(20, bowling.score());
	}
	
	@Test
	public void testOneSpare() {
		bowling.roll(5);
		bowling.roll(5);
		bowling.roll(3);
		rollMany(17,0);
		assertEquals(16, bowling.score());
	}

}
