mod dec01;
mod dec02;
mod dec03;
mod dec04;
mod dec05;
mod dec06;
mod dec07;

use crate::solve::Solve;
use std::fmt::Error;

pub fn create_calculator(day: &i8) -> Result<Box<dyn Solve>, Error> {
    match day {
        1 => Ok(Box::new(dec01::Calculator {})),
        2 => Ok(Box::new(dec02::Calculator {})),
        3 => Ok(Box::new(dec03::Calculator {})),
        4 => Ok(Box::new(dec04::Calculator {})),
        5 => Ok(Box::new(dec05::Calculator {})),
        6 => Ok(Box::new(dec06::Calculator {})),
        7 => Ok(Box::new(dec07::Calculator {})),
        _ => Err(Error),
    }
}
