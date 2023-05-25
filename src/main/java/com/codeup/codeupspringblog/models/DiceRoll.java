package com.codeup.codeupspringblog.models;

import java.util.List;

public class DiceRoll {
	private List<Integer> diceRolls;

	public DiceRoll(List<Integer> diceRolls) {
		this.diceRolls = diceRolls;
	}

	public List<Integer> getDiceRolls() {
		return diceRolls;
	}

	public void setDiceRolls(List<Integer> diceRolls) {
		this.diceRolls = diceRolls;
	}
}
