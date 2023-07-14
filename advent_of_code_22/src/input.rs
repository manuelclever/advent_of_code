use std::fs::File;
use std::io::BufReader;

pub fn get_buffered_reader_p1(day: &str) -> BufReader<File> {
    let filename: &str = "input1.txt";
    get_buffered_reader(day, filename)
}

fn get_buffered_reader(day: &str, filename: &str) -> BufReader<File> {
    let path: &str = "src/factory/dec";
    let full_path: String = format!("{}{}{}{}", path, day, "/", filename);

    let file: File = File::open(full_path).unwrap();
    BufReader::new(file)
}