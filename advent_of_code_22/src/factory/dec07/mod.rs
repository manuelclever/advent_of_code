use crate::solve::Solve;
use crate::input;

use std::fs::File;
use std::io::{BufRead, BufReader};


pub struct Calculator {}

impl Solve for Calculator {
    fn solve_1(&self) -> i128 {
        Calculator::get_tree();
        0
    }
    fn solve_2(&self) -> i128 {
        0
    }
}

impl Calculator {

    fn get_tree() -> Node {
        let reader: BufReader<File> = input::get_buffered_reader_p1("07");
        let mut cur_dir: String = String::from("");
        for (_index, line) in reader.lines().enumerate() {
            let splices: Vec<&str> = line.unwrap().split(" ").collect();

            if (splices.get(0).unwrap().eq(&"$")) & 
            (splices.get(1).unwrap().eq(&"cd")) {
                cur_dir = String::from(splices.get(2).unwrap());
            }
        }
        Node{parent: Box::new(None), children: Box::new(None), data: Item{name: String::from(""), size: None}}
    }

    
}

struct Node {
    parent: Box<Option<Node>>,
    children: Box<Option<Vec<Node>>>,
    
    data: Item,
}

struct Item {
    name: String,
    size: Option<u128>
}
