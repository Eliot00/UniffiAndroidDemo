uniffi::include_scaffolding!("hello");

pub fn rust_greeting(to: String) -> String {
    return format!("Hello, {}!", to);
}
