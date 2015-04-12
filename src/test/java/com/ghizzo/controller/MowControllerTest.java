package com.ghizzo.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ghizzo.controller.MowController;
import com.ghizzo.model.enums.CardinalPointEnum;
import com.ghizzo.model.exception.IllegalPositionException;

public class MowControllerTest {

	@Test
	public void testExpectedResult() throws IllegalPositionException {
		MowController mowController = new MowController();
		List<String> stringList = new ArrayList<String>();
		stringList.add("5 5");
		stringList.add("1 2 N");
		stringList.add("GAGAGAGAA");
		stringList.add("3 3 E");
		stringList.add("AADAADADDA");
		mowController.handleLines(stringList);

		assertEquals(CardinalPointEnum.N, mowController.getLawn().getMowList().get(0).getDirection());
		assertEquals(1, mowController.getLawn().getMowList().get(0).getPosition().getxAxis());
		assertEquals(3, mowController.getLawn().getMowList().get(0).getPosition().getyAxis());
		assertEquals(CardinalPointEnum.E, mowController.getLawn().getMowList().get(1).getDirection());
		assertEquals(5, mowController.getLawn().getMowList().get(1).getPosition().getxAxis());
		assertEquals(1, mowController.getLawn().getMowList().get(1).getPosition().getyAxis());
		assertEquals(5, mowController.getLawn().getLength());
		assertEquals(5, mowController.getLawn().getWidth());
	}

	@Test(expected = IllegalPositionException.class)
	public void testIllegalMowCreation() throws IllegalPositionException {
		MowController mowController = new MowController();
		List<String> stringList = new ArrayList<String>();
		stringList.add("2 2");
		stringList.add("3 2 N");
		stringList.add("GAGAGAGAA");
		mowController.handleLines(stringList);
	}

}
