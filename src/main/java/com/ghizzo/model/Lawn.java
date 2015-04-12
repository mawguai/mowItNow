package com.ghizzo.model;

import java.util.List;

import com.ghizzo.model.bean.Mow;
import com.ghizzo.model.enums.MovementEnum;
import com.ghizzo.model.exception.IllegalPositionException;

public interface Lawn {

	/**
	 * Add a {@link Mow} on the lawn
	 * @param newMow
	 * @throws IllegalPositionException - Throw an illegalPosition if the Mow coordinate are out of bound
	 */
	public void addMow(Mow newMow) throws IllegalPositionException;

	/**
	 * Move the mow according to the list of {@link MovementEnum}
	 * @param movementList
	 * @param mow
	 */
	public void moveMow(List<MovementEnum> movementList, Mow mow);

	/**
	 * @return the {@link Mow} present on the lawn
	 */
	public List<Mow> getMowList();

	/**
	 * @return the lawn's length
	 */
	public int getLength();

	/**
	 * @return the lawn's width
	 */
	public int getWidth();

}