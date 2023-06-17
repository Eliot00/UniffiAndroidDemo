use std::env;
use std::fs::{read_to_string, OpenOptions};
use std::io::Write;
use std::path::PathBuf;

uniffi::include_scaffolding!("hello");

pub fn rust_greeting(to: String) -> String {
    return format!("Hello, {}!", to);
}

pub fn rust_read() -> String {
    let path = PathBuf::from("/data/data/com.example.uniffidemo/files/foo.txt");
    read_to_string(path).unwrap_or(String::from("failed"))
}

pub fn rust_write() {
    let path = PathBuf::from("/data/data/com.example.uniffidemo/files/foo.txt");
    let mut file = OpenOptions::new()
        .write(true)
        .create(true)
        .open(path)
        .unwrap();

    file.write_all(b"hello").unwrap()
}
