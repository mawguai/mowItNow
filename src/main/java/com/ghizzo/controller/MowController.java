package com.ghizzo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ghizzo.model.Lawn;
import com.ghizzo.model.RectangularLawn;
import com.ghizzo.model.bean.Mow;
import com.ghizzo.model.enums.CardinalPointEnum;
import com.ghizzo.model.enums.MovementEnum;
import com.ghizzo.model.exception.IllegalPositionException;

public class MowController {

	private Lawn lawn;

	public Lawn getLawn() {
		return lawn;
	}

	/**
	 * Check the validity of a lines's list, then use it to create and move the Mows
	 * @param lines
	 * @throws IllegalPositionException
	 */
	public void handleLines(List<String> lines) throws IllegalPositionException {
		// First line is the lawn creation
		initLawn(lines.get(0));

		// Even lines are about creation, odd lines are about the movement
		Mow currentMow = null;
		boolean isMowCreation = true;
		List<String> mowsLines = lines.subList(1, lines.size());
		for (String line : mowsLines) {
			if (isMowCreation) {
				currentMow = handleMowCreationLine(line);
				lawn.addMow(currentMow);
				isMowCreation = false;
			} else {
				handleMowMovementLine(line, currentMow);
				isMowCreation = true;
			}
		}
	}

	/**
	 * Deal with the movement line
	 * @param mowingLine
	 * @param mow
	 */
	private void handleMowMovementLine(String mowingLine, Mow mow) {
		List<MovementEnum> movementList = new ArrayList<MovementEnum>();
		validateLine("\\s+[AGD]*\\s+", mowingLine);
		Pattern mowCreationLinePattern = Pattern.compile("[AGD]");
		Matcher matcher = mowCreationLinePattern.matcher(mowingLine);
		while (matcher.find()) {
			movementList.add(MovementEnum.valueOf(matcher.group()));
		};
		lawn.moveMow(movementList, mow);
	}

	/**
	 * Deal with the mow's creation line
	 * @param mowCreationLine
	 * @return the new {@link Mow}
	 */
	private Mow handleMowCreationLine(String mowCreationLine) {
		validateLine("\\s+\\d\\s+\\d\\s[NESW]{1}\\s+", mowCreationLine);
		Pattern mowCreationLinePattern = Pattern.compile("\\d+");
		Matcher matcher = mowCreationLinePattern.matcher(mowCreationLine);
		matcher.find();
		int xPos = Integer.parseInt(matcher.group());
		matcher.find();
		int yPos = Integer.parseInt(matcher.group());
		mowCreationLinePattern = Pattern.compile("[NESW]{1}");
		matcher = mowCreationLinePattern.matcher(mowCreationLine);
		matcher.find();
		CardinalPointEnum direction = CardinalPointEnum.valueOf(matcher.group());
		return new Mow(xPos, yPos, direction);
	}

	/**
	 * Deal with the lawn's creation line
	 * @param lawnSizeLine
	 */
	private void initLawn(String lawnSizeLine) {
		validateLine("\\s+\\d+\\s+\\d+\\s+", lawnSizeLine);
		Pattern lawnSizeLinePattern = Pattern.compile("\\d+");
		Matcher matcher = lawnSizeLinePattern.matcher(lawnSizeLine);
		matcher.find();
		int xMax = Integer.parseInt(matcher.group());
		matcher.find();
		int yMax = Integer.parseInt(matcher.group());
		matcher.group();
		lawn = new RectangularLawn(xMax, yMax);
	}

	private boolean validateLine(String regex, String lineToValidate) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(lineToValidate);
		return matcher.find();
	}
}
