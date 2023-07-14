extern crate core;

mod factory;
mod solve;
mod input;

use crate::factory::create_calculator;
use std::io;
use std::num::ParseIntError;

fn main() {
    let day: i8;
    loop {
        let mut input = String::new();
        println!("Please enter a day(1-24):");
        match io::stdin().read_line(&mut input) {
            Ok(_) => {
                let mut result: Result<i8, ParseIntError> = input.trim().parse();
                if is_between(&mut result, 1, 24) {
                    day = result.unwrap();
                    break;
                } else {
                    continue;
                }
            }
            Err(_) => {
                eprintln!("Input couldn't not be read. Please try again.");
                continue;
            }
        }
    }

    let part: i8;
    loop {
        let mut input = String::new();
        println!("Please enter a part(1 or 2):");
        match io::stdin().read_line(&mut input) {
            Ok(_) => {
                let mut result: Result<i8, ParseIntError> = input.trim().parse();
                if is_between(&mut result, 1, 2) {
                    part = result.unwrap();
                    break;
                } else {
                    continue;
                }
            }
            Err(_) => {
                eprintln!("Input couldn't not be read. Please try again.");
                continue;
            }
        }
    }

    match create_calculator(&day) {
        Ok(calculator) => {
            let solution: i128;
            if part == 1 {
                solution = calculator.solve_1();
            } else {
                solution = calculator.solve_2();
            }
            println!("The solution of Day {} Part {} is {}", day, part, solution);
        }
        Err(_) => println!("No solution for this day yet."),
    }
}

fn is_between(result: &mut Result<i8, ParseIntError>, start: i8, end: i8) -> bool {
    match result {
        Ok(_) => {
            if (result.as_ref().unwrap() < &start) | (result.as_ref().unwrap() > &end) {
                eprintln!(
                    "Input is smaller than {} or bigger than {}. Please try again.",
                    start, end
                );
                return false;
            }
        }
        Err(_) => {
            eprintln!("Input couldn't not be parsed. Please try again.");
            return false;
        }
    }
    true
}
