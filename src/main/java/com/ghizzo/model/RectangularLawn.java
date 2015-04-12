package com.ghizzo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghizzo.model.bean.Mow;
import com.ghizzo.model.enums.CardinalPointEnum;
import com.ghizzo.model.enums.MovementEnum;
import com.ghizzo.model.exception.IllegalMovementException;
import com.ghizzo.model.exception.IllegalPositionException;

public class RectangularLawn implements Lawn {

	final static Logger logger = LoggerFactory.getLogger(RectangularLawn.class);

	private int xMax;
	private int yMax;
	private List<Mow> mowList;

	public RectangularLawn(int xMax, int yMax) {
		logger.debug("Creating a lawn");
		this.xMax = xMax;
		this.yMax = yMax;
		mowList = new ArrayList<Mow>();
	}

	public void addMow(Mow newMow) throws IllegalPositionException {
		int xPos = newMow.getPosition().getxAxis();
		int yPos = newMow.getPosition().getyAxis();
		logger.debug("Adding a mow {} {} {}", xPos, yPos, newMow.getDirection());
		if (xPos < 0 || xPos > xMax) {
			throw new IllegalPositionException("Can't place the mow in the neighbor's garden");
		}
		if (yPos < 0 || yPos > yMax) {
			throw new IllegalPositionException("Can't place the mow in the neighbor's garden");
		}
		mowList.add(newMow);
	}

	public void moveMow(List<MovementEnum> movementList, Mow mow) {
		logger.debug("moving a mow {} ", movementList);
		for (MovementEnum movement : movementList) {
			try {
				moveMow(movement, mow);
			} catch (IllegalMovementException e) {
				logger.warn(e.getMessage());
			}
		}
		mow.showInformation();
	}

	public int getWidth() {
		return xMax;
	}

	public int getLength() {
		return yMax;
	}

	public List<Mow> getMowList() {
		return Collections.unmodifiableList(mowList);
	}

	/**
	 * The mow position or direction will be update according to the movement
	 * @param movement - the {@link MovementEnum} to use
	 * @param mow - the {@link Mow} to move
	 * @throws IllegalMovementException
	 */
	private void moveMow(MovementEnum movement, Mow mow) throws IllegalMovementException {
		switch (movement) {
		case A : updateMowPosition(mow);
		break;
		case D :
		case G : updateMowDirection(movement, mow);
		break;
		}
	}

	/**
	 * this method should only be called for a forward movement,
	 * it will determine the next Position of the Mow and update it
	 * @param mow - the {@link Mow} to move
	 * @throws IllegalMovementException
	 */
	private void updateMowPosition(Mow mow) throws IllegalMovementException {
		int nextValue;
		switch (mow.getDirection()) {
		case N:
			nextValue = mow.getPosition().getyAxis() + 1;
			if (nextValue > yMax) throw new IllegalMovementException("Mow can't go beyond (trying to reach the coordinate (" + mow.getPosition().getxAxis() + ',' + nextValue + "))");
			mow.getPosition().setyAxis(nextValue);
		break;
		case E:
			nextValue = mow.getPosition().getxAxis() + 1;
			if (nextValue > xMax) throw new IllegalMovementException("Mow can't go beyond (trying to reach the coordinate (" + nextValue + ',' + mow.getPosition().getyAxis() + "))");
			mow.getPosition().setxAxis(nextValue);
		break;
		case S:
			nextValue = mow.getPosition().getyAxis() - 1;
			if (nextValue < 0) throw new IllegalMovementException("Mow can't go beyond (trying to reach the coordinate (" + mow.getPosition().getxAxis() + ',' + nextValue + "))");
			mow.getPosition().setyAxis(nextValue);
		break;
		case W:
			nextValue = mow.getPosition().getxAxis() - 1;
			if (nextValue < 0) throw new IllegalMovementException("Mow can't go beyond (trying to reach the coordinate (" + nextValue + ',' + mow.getPosition().getyAxis() + "))");
			mow.getPosition().setxAxis(nextValue);
		}
	}

	/**
	 * This method should only be called for a rotation,
	 * it will determine the next direction of the Mow and update it
	 * @param movement - the {@link MovementEnum} to use
	 * @param mow - the {@link Mow} to rotate
	 */
	private void updateMowDirection(MovementEnum movement, Mow mow) {
		if (movement.equals(MovementEnum.D)) {
			switch (mow.getDirection()) {
			case N: mow.setDirection(CardinalPointEnum.E);
			break;
			case E: mow.setDirection(CardinalPointEnum.S);
			break;
			case S: mow.setDirection(CardinalPointEnum.W);
			break;
			case W: mow.setDirection(CardinalPointEnum.N);
			}
		} else if (movement.equals(MovementEnum.G)) {
			switch (mow.getDirection()) {
			case N: mow.setDirection(CardinalPointEnum.W);
			break;
			case E: mow.setDirection(CardinalPointEnum.N);
			break;
			case S: mow.setDirection(CardinalPointEnum.E);
			break;
			case W: mow.setDirection(CardinalPointEnum.S);
			}
		}
	}
}
