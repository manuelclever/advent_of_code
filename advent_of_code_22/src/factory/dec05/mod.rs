use crate::solve::Solve;
use crate::input;
use std::collections::HashMap;
use std::io::BufRead;

pub struct Calculator {}

impl Solve for Calculator {
    fn solve_1(&self) -> i128 {
        let instructions: Vec<[usize; 3]> = Calculator::get_instructions();
        let mut stacks: HashMap<usize, Vec<char>> = Calculator::get_stacks();

        for instr in instructions {
            Calculator::mov(instr, &mut stacks);
        }
        println!("{}", Calculator::get_top_crates(&stacks));
        0
    }
    fn solve_2(&self) -> i128 {
        let instructions: Vec<[usize; 3]> = Calculator::get_instructions();
        let mut stacks: HashMap<usize, Vec<char>> = Calculator::get_stacks();

        for instr in instructions {
            Calculator::mov_as_stack(instr, &mut stacks);
        }
        println!("{}", Calculator::get_top_crates(&stacks));
        0
    }
}

impl Calculator {
    fn get_stacks() -> HashMap<usize, Vec<char>> {
        let reader = input::get_buffered_reader_p1("05");

        let mut m: HashMap<usize, Vec<char>> = HashMap::new();
        for (_index, line) in reader.lines().enumerate() {
            let chars: Vec<char> = line.unwrap().chars().collect();
            if (chars.len() == 0) | (chars[1] == '1') {
                break;
            }

            let mut tower: usize = 1;
            let mut i: usize = 0;
            while i < chars.len() - 1 {
                let c: char = chars[i + 1];

                if c != ' ' {
                    if m.get(&tower).is_none() {
                        m.insert(tower.clone(), vec![c]);
                    } else {
                        let test: &mut Vec<char> = m.get_mut(&tower).unwrap();
                        test.push(c);
                    }
                }
                i += 4;
                tower += 1;
            }
        }
        m
    }

    fn get_instructions() -> Vec<[usize; 3]> {
        let reader = input::get_buffered_reader_p1("05");

        let mut v: Vec<[usize; 3]> = Vec::new();
        for (_index, line) in reader.lines().enumerate() {
            let chars: Vec<char> = line.as_ref().unwrap().chars().collect();
            if chars.len() > 0 {
                if chars[0] == 'm' {
                    let line: String = line.unwrap();
                    let split: Vec<&str> = line.split_whitespace().collect();
                    let instr: [usize; 3] = [
                        split.get(3).unwrap().parse().unwrap(),
                        split.get(5).unwrap().parse().unwrap(),
                        split.get(1).unwrap().parse().unwrap(),
                    ];
                    v.push(instr);
                }
            }
        }
        v
    }

    fn mov(instr: [usize; 3], stacks: &mut HashMap<usize, Vec<char>>) {
        let source: &mut Vec<char> = stacks.get_mut(&instr[0]).unwrap();
        let mut crat: Vec<char> = Vec::new();
        let mut i: usize = 0;
        while i < instr[2] {
            let c: char = source.remove(0);
            crat.push(c);
            i += 1;
        }

        let target: &mut Vec<char> = stacks.get_mut(&instr[1]).unwrap();
        for c in crat {
            target.insert(0, c);
        }
    }

    fn mov_as_stack(instr: [usize; 3], stacks: &mut HashMap<usize, Vec<char>>) {
        let source: &mut Vec<char> = stacks.get_mut(&instr[0]).unwrap();
        let mut crat: Vec<char> = Vec::new();
        let mut i: usize = 0;
        while i < instr[2] {
            let c: char = source.remove(0);
            crat.push(c);
            i += 1;
        }

        let target: &mut Vec<char> = stacks.get_mut(&instr[1]).unwrap();
        let mut i: isize = (crat.len() - 1) as isize;
        while i >= 0 {
            target.insert(0, crat.get(i as usize).unwrap().clone());
            i -= 1;
        }
    }

    fn get_top_crates(stacks: &HashMap<usize, Vec<char>>) -> String {
        let mut i: usize = 1;
        let mut s: String = String::from("");
        while i <= stacks.len() {
            s.push_str(stacks.get(&i).unwrap().get(0).unwrap().to_string().as_str());
            i += 1;
        }
        s
    }
}
