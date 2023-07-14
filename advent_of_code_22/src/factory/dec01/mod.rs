use crate::input;
use crate::solve::Solve;
use std::cmp::Ordering::Less;
use std::io::BufRead;

pub struct Calculator {}

impl Solve for Calculator {
    fn solve_1(&self) -> i128 {
        let v: Vec<Vec<i32>> = Calculator::get_calories();

        let mut highest: &i32 = &0;
        for vec in &v {
            let cur: &i32 = vec.get(vec.len() - 1).unwrap();

            match highest.cmp(cur) {
                Less => highest = cur,
                _ => {}
            }
        }
        i128::from(highest.clone())
    }
    fn solve_2(&self) -> i128 {
        let v: Vec<Vec<i32>> = Calculator::get_calories();

        let mut highest: Vec<i32> = vec![0, 0, 0];
        for vec in &v {
            let cur: &i32 = vec.get(vec.len() - 1).unwrap();

            if highest.get(0).unwrap().cmp(&cur) == Less {
                highest.insert(0, cur.clone());
            } else if highest.get(1).unwrap().cmp(&cur) == Less {
                highest.insert(1, cur.clone());
            } else if highest.get(2).unwrap().cmp(&cur) == Less {
                highest.insert(2, cur.clone());
            }
        }
        let sum: i32 = highest.get(0).unwrap() + highest.get(1).unwrap() + highest.get(2).unwrap();
        i128::from(sum)
    }
}

impl Calculator {
    fn get_calories() -> Vec<Vec<i32>> {
        let reader = input::get_buffered_reader_p1("01");

        let mut v: Vec<Vec<i32>> = Vec::new();
        let mut calorie_set: Vec<i32> = Vec::new();
        for (_index, line) in reader.lines().enumerate() {
            let calories: i32;
            match line.unwrap().parse() {
                Ok(value) => calories = value,
                Err(_) => calories = 0,
            }

            if calories != 0 {
                calorie_set.push(calories);
            } else {
                let mut sum: i32 = 0;

                for value in &calorie_set {
                    sum += value;
                }
                calorie_set.push(sum);
                v.push(calorie_set.clone());
                calorie_set = Vec::new();
            }
        }
        v
    }
}
