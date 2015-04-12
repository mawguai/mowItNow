package com.ghizzo.model.bean;

import javax.swing.JOptionPane;

import com.ghizzo.model.enums.CardinalPointEnum;

public class Mow {

	private CardinalPointEnum direction;
	private Position position;

	public Mow(Position position, CardinalPointEnum direction) {
		this.direction = direction;
		this.position = position;
	}

	public Mow(int xAxis, int yAxis, CardinalPointEnum direction) {
		Position position = new Position(xAxis, yAxis);
		this.direction = direction;
		this.position = position;
	}

	public CardinalPointEnum getDirection() {
		return direction;
	}

	public void setDirection(CardinalPointEnum direction) {
		this.direction = direction;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void showInformation() {
		// Clearly not a best practice, but easiest & fastest way to bring a notification to the user
		JOptionPane.showMessageDialog(null, "Position : " + position.getxAxis() + " " + position.getyAxis() + " " + direction.name(), "Mow Informations", JOptionPane.INFORMATION_MESSAGE);
	}

}
