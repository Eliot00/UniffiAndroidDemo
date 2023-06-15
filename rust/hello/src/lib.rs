use std::env;
use std::fs::read_to_string;
use std::path::PathBuf;

uniffi::include_scaffolding!("hello");

pub fn rust_greeting(to: String) -> String {
    return format!("Hello, {}!", to);
}

pub fn rust_read() -> String {
    let path = PathBuf::from("/data/data/com.example.uniffidemo/files/foo.txt");
    let current_dir = env::current_dir().unwrap();
    read_to_string(path).unwrap_or(current_dir.to_string_lossy().to_string())
}
