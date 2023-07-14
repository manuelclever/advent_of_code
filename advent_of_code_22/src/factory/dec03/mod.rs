use crate::solve::Solve;
use crate::input;
use std::io::BufRead;

pub struct Calculator {}

impl Solve for Calculator {
    fn solve_1(&self) -> i128 {
        let v: Vec<Vec<char>> = Calculator::get_rucksack_content();

        let mut items: Vec<char> = Vec::new();
        for rucksack in v {
            items.push(Calculator::find_duplicate(Calculator::get_compartments(
                rucksack.clone(),
            )));
        }
        Calculator::sum_priorities(items)
    }
    fn solve_2(&self) -> i128 {
        let v: Vec<Vec<char>> = Calculator::get_rucksack_content();

        let mut items: Vec<char> = Vec::new();
        let mut i: usize = 0;
        while i < v.len() {
            let triplet: [&Vec<char>; 3] = [
                v.get(i).unwrap(),
                v.get(i + 1).unwrap(),
                v.get(i + 2).unwrap(),
            ];
            items.push(Calculator::find_duplicate_in_three(&triplet));

            i += 3;
        }
        Calculator::sum_priorities(items)
    }
}

impl Calculator {
    fn get_rucksack_content() -> Vec<Vec<char>> {
        let reader = input::get_buffered_reader_p1("03");

        let mut v: Vec<Vec<char>> = Vec::new();
        for (_index, line) in reader.lines().enumerate() {
            let line = line.unwrap();
            let arr: Vec<char> = line.chars().collect();
            v.push(arr);
        }
        v
    }

    fn get_compartments(v: Vec<char>) -> [Vec<char>; 2] {
        [
            v[0..(v.len() / 2)].to_vec(),
            v[(v.len() / 2)..(v.len())].to_vec(),
        ]
    }

    fn find_duplicate(v: [Vec<char>; 2]) -> char {
        for a in v[0].clone() {
            for b in v[1].clone() {
                if a == b {
                    return a.clone();
                }
            }
        }
        '0'
    }

    fn find_duplicate_in_three(v: &[&Vec<char>; 3]) -> char {
        for a in v[0].clone() {
            for b in v[1].clone() {
                for c in v[2].clone() {
                    if (a == b) & (a == c) {
                        return a.clone();
                    }
                }
            }
        }
        '0'
    }

    fn sum_priorities(v: Vec<char>) -> i128 {
        let mut sum: i128 = 0;

        for c in v {
            let dec: i128 = c as i128;

            if dec >= 65 && dec <= 90 {
                //uppercase
                sum += dec - 38;
            } else if dec >= 91 && dec <= 122 {
                //lowercase
                sum += dec - 96;
            }
        }
        sum
    }
}
