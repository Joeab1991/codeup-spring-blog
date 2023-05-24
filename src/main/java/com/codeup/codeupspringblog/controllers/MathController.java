package com.codeup.codeupspringblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MathController {

	@GetMapping("/add/{number1}/and/{number2}")
	@ResponseBody
	public String add(@PathVariable int number1, @PathVariable int number2) {
		return number1 + " + " + number2 + " = " + (number1 + number2);
	}

	@GetMapping("/subtract/{number1}/from/{number2}")
	@ResponseBody
	public String subtract(@PathVariable int number1, @PathVariable int number2) {
		return number2 + " - " + number1 + " = " + (number2 - number1);
	}

	@GetMapping("/multiply/{number1}/and/{number2}")
	@ResponseBody
	public String multiply(@PathVariable int number1, @PathVariable int number2) {
		return number1 + " * " + number2 + " = " + (number1 * number2);
	}

	@GetMapping("/divide/{number1}/by/{number2}")
	@ResponseBody
	public String divide(@PathVariable int number1, @PathVariable int number2) {
		return number1 + " / " + number2 + " = " + (number1 / number2);
	}
}
