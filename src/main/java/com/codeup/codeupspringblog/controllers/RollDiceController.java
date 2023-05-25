package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Coffee;
import com.codeup.codeupspringblog.models.DiceRoll;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RollDiceController {

	@RequestMapping("/roll-dice")
	public String rollDice() {
		return "roll-dice";
	}

	@GetMapping("/roll-dice/{n}")
	public String luckyNumber(@PathVariable String n, Model model){
		DiceRoll diceRolls = new DiceRoll(Arrays.asList(
				(int)Math.floor(Math.random() * 6 + 1),
				(int)Math.floor(Math.random() * 6 + 1),
				(int)Math.floor(Math.random() * 6 + 1),
				(int)Math.floor(Math.random() * 6 + 1),
				(int)Math.floor(Math.random() * 6 + 1),
				(int)Math.floor(Math.random() * 6 + 1)
		));

		model.addAttribute("diceRolls", diceRolls);
		model.addAttribute("guess", n);

		List<Integer> rolls = diceRolls.getDiceRolls();
		int correctGuesses = 0;
		int number = Integer.parseInt(n);

		for (int roll : rolls)
		{
			if (number == roll) {
				correctGuesses++;
			}
		}
		model.addAttribute("correctGuesses", correctGuesses);

		return "roll-dice";
	}
}
