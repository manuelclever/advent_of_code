use crate::solve::Solve;
use crate::input;
use std::io::BufRead;

pub struct Calculator {}

impl Solve for Calculator {
    fn solve_1(&self) -> i128 {
        let pairs: Vec<[[u32; 2]; 2]> = Calculator::get_pairs();

        let mut sum: i128 = 0;
        for pair in pairs {
            if Calculator::is_in_range(pair[0], pair[1]) {
                sum += 1;
            }
        }
        sum
    }
    fn solve_2(&self) -> i128 {
        let pairs: Vec<[[u32; 2]; 2]> = Calculator::get_pairs();

        let mut sum: i128 = 0;
        for pair in pairs {
            if Calculator::is_overlapping(pair[0], pair[1]) {
                sum += 1;
            }
        }
        sum
    }
}

impl Calculator {
    fn get_pairs() -> Vec<[[u32; 2]; 2]> {
        let reader = input::get_buffered_reader_p1("04");

        let mut v: Vec<[[u32; 2]; 2]> = vec![];
        for (_index, line) in reader.lines().enumerate() {
            let line: String = line.unwrap();
            let split: Vec<&str> = line.split(&['-', ','][..]).collect();
            let pair: [[u32; 2]; 2] = [
                [
                    split.get(0).unwrap().parse().unwrap(),
                    split.get(1).unwrap().parse().unwrap(),
                ],
                [
                    split.get(2).unwrap().parse().unwrap(),
                    split.get(3).unwrap().parse().unwrap(),
                ],
            ];
            v.push(pair);
        }
        v
    }

    fn is_in_range(a: [u32; 2], b: [u32; 2]) -> bool {
        (a[0] >= b[0]) & (a[1] <= b[1]) | (b[0] >= a[0]) & (b[1] <= a[1])
    }

    fn is_overlapping(a: [u32; 2], b: [u32; 2]) -> bool {
        (a[0] >= b[0]) & (a[0] <= b[1])
            | (a[1] >= b[0]) & (a[1] <= b[1])
            | (b[0] >= a[0]) & (b[0] <= a[1])
            | (b[1] >= a[0]) & (b[1] <= a[1])
    }
}
