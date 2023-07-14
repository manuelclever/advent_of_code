use crate::factory::dec02::Object::{PAPER, ROCK, SCISSORS};
use crate::factory::dec02::Points::{DRAW, LOSS, WIN};
use crate::solve::Solve;
use crate::input;
use std::io::BufRead;

pub struct Calculator {}

enum Points {
    WIN = 6,
    DRAW = 3,
    LOSS = 0,
}

enum Object {
    ROCK = 1,
    PAPER = 2,
    SCISSORS = 3,
}

impl Solve for Calculator {
    fn solve_1(&self) -> i128 {
        // A_X = Rock, B_Y = Paper, C_Z = Scissors
        let strategy: Vec<[char; 2]> = Calculator::get_strategy();

        let mut sum: i32 = 0;
        for arr in strategy {
            match arr[1] {
                'X' => sum += ROCK as i32,
                'Y' => sum += PAPER as i32,
                'Z' => sum += SCISSORS as i32,
                _ => sum += 0,
            }
            sum += Calculator::get_score(arr[1], arr[0]);
        }
        i128::from(sum)
    }
    fn solve_2(&self) -> i128 {
        // A_X = Rock, B_Y = Paper, C_Z = Scissors
        let strategy: Vec<[char; 2]> = Calculator::get_strategy();

        let mut sum: i32 = 0;
        for arr in strategy {
            sum += Calculator::get_score_from_strategy(arr[1], arr[0]);
        }
        i128::from(sum)
    }
}

impl Calculator {
    fn get_strategy() -> Vec<[char; 2]> {
        let reader = input::get_buffered_reader_p1("02");

        let mut v: Vec<[char; 2]> = Vec::new();
        for (_index, line) in reader.lines().enumerate() {
            let line = line.unwrap();
            let split: Vec<&str> = line.split(" ").collect();
            let arr: [char; 2] = [
                split.get(0).unwrap().parse().unwrap(),
                split.get(1).unwrap().parse().unwrap(),
            ];
            v.push(arr);
        }
        v
    }

    fn get_score(a: char, b: char) -> i32 {
        if a == 'A' || a == 'X' {
            //rock
            return match b {
                'A' | 'X' => DRAW as i32,
                'B' | 'Y' => LOSS as i32,
                'C' | 'Z' => WIN as i32,
                _ => LOSS as i32,
            };
        } else if a == 'B' || a == 'Y' {
            //paper
            return match b {
                'A' | 'X' => WIN as i32,
                'B' | 'Y' => DRAW as i32,
                'C' | 'Z' => LOSS as i32,
                _ => LOSS as i32,
            };
        } else if a == 'C' || a == 'Z' {
            //scissors
            return match b {
                'A' | 'X' => LOSS as i32,
                'B' | 'Y' => WIN as i32,
                'C' | 'Z' => DRAW as i32,
                _ => LOSS as i32,
            };
        }
        0
    }

    fn get_score_from_strategy(a: char, b: char) -> i32 {
        if a == 'X' {
            //lose
            return LOSS as i32
                + match b {
                    'A' => SCISSORS as i32,
                    'B' => ROCK as i32,
                    'C' => PAPER as i32,
                    _ => 0,
                };
        } else if a == 'Y' {
            //draw
            return DRAW as i32
                + match b {
                    'A' => ROCK as i32,
                    'B' => PAPER as i32,
                    'C' => SCISSORS as i32,
                    _ => 0,
                };
        } else if a == 'Z' {
            //win
            return WIN as i32
                + match b {
                    'A' => PAPER as i32,
                    'B' => SCISSORS as i32,
                    'C' => ROCK as i32,
                    _ => 0,
                };
        }
        0
    }
}
