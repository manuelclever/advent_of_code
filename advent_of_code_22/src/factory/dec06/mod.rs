use crate::solve::Solve;
use crate::input;
use std::io::BufRead;

pub struct Calculator {}

impl Solve for Calculator {
    fn solve_1(&self) -> i128 {
        let v: Vec<String> = Calculator::get_signal();
        let pos: i128 = Calculator::get_pos_first_unique_of(4, v.get(0).unwrap()); 
        pos
    }
    fn solve_2(&self) -> i128 {
        let v: Vec<String> = Calculator::get_signal();
        let pos: i128 = Calculator::get_pos_first_unique_of(14, v.get(0).unwrap()); 
        pos
    }
}

impl Calculator {
    fn get_signal() -> Vec<String> {
        let reader = input::get_buffered_reader_p1("06");

        let mut v: Vec<String> = Vec::new();
        for (_index, line) in reader.lines().enumerate() {
            v.push(line.unwrap());
        }
        v
    }

    fn get_pos_first_unique_of(amount: usize, signal: &String) -> i128 {
        let chars: Vec<char> = signal.chars().collect();

        let mut i: usize = 0;
        while i < chars.len() - amount {
            let mut package: Vec<&char> = Vec::new();

            let mut j = i;
            while j < (i + amount) {
                package.push(chars.get(j).unwrap());
                j += 1;
            }

            if Calculator::is_different(package) {
                return (i + amount) as i128;
            }
            i += 1;
        }
        0
    }

    fn is_different(package: Vec<&char>) -> bool {
        let mut i: usize = 0;

        while i < package.len() {

            let mut j: usize = 0;
            while j < package.len() {
                if (i != j) & (package.get(i).unwrap() == package.get(j).unwrap()) {
                    return false;
                }
                j += 1;
            }
            i += 1;
        }
        true
    }
    
}
