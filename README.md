# nman - New Man 📖

> **WARNING**: This project is in early development and may be unstable. Use at your own risk.

> AI-powered documentation generator for programming languages and libraries. Like `man` pages, but smarter.

![Version](https://img.shields.io/badge/version-0.0.1-blue.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)

## ✨ Features

- 🤖 **AI-Powered Documentation** - Generate detailed documentation for any function, method, or macro using Claude or ChatGPT
- 📦 **Smart Caching** - Documents are cached locally for instant access on subsequent queries
- 🌐 **Multi-Provider Support** - Choose between Anthropic (Claude) and OpenAI (ChatGPT) models
- 🎨 **Beautiful Rendering** - Documents rendered beautifully in the terminal using [Glow](https://github.com/charmbracelet/glow)
- 🔧 **Customizable** - Configure model, API key, document home, token limits, and custom renderers

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- [Glow](https://github.com/charmbracelet/glow) (for rendering) or any custom Markdown renderer (Amazing stuff btw, check it out!)

### Installation

```bash
# Download the latest release
curl -LO https://github.com/jilet1308/nman/releases/latest/download/nman-0.0.1.jar

# Create an alias (optional)
alias nman="java -jar /path/to/nman-0.0.1.jar"
```

### Initial Setup

```bash
# Set up your API key
nman setup --apiKey YOUR_API_KEY

# Optionally, configure the model (defaults to Claude)
nman setup --model Sonnet-4.5
```

## 📖 Usage

### Basic Usage

```bash
# Generate/view documentation for a function
nman java printf
nman go fmt.Println
nman c malloc
```

### Commands

| Command | Description |
|---------|-------------|
| `nman <lang> <function>` | Generate or view cached documentation |
| `nman setup` | Configure nman settings |
| `nman langs` | List supported languages and their aliases |
| `nman models` | List available AI models and providers |

### Options

| Option | Short | Description |
|--------|-------|-------------|
| `--override` | `-o` | Force regenerate documentation (ignore cache) |
| `--prompt-customize` | `-p` | Add extra context to the generation prompt |
| `--help` | `-h` | Show help message |
| `--version` | `-V` | Show version information |

### Setup Options

| Option | Short | Description |
|--------|-------|-------------|
| `--model` | `-m` | Set the AI model to use |
| `--apiKey` | `-k` | Set your API key |
| `--home` | `-e` | Set where documents are stored |
| `--maxTokens` | `-t` | Set max tokens for generation (default: 8192) |
| `--customRenderer` | `-c` | Set custom renderer command (default: glow) |

## 🌍 Supported Languages

| Language | Aliases |
|----------|---------|
| Java | `java`, `jvm`, `jdk`, `jav` |
| C | `c`, `clang` |
| C++ | `cpp`, `c++`, `cplusplus` |
| Go | `go`, `golang` |
| Python | `python`, `py`, `python3`, `py3` |
| Rust | `rust`, `rs`, `rustlang` |
| JavaScript | `javascript`, `js`, `node`, `nodejs` |
| TypeScript | `typescript`, `ts`, `tsc` |
| Ruby | `ruby`, `rb` |
| Swift | `swift` |
| Kotlin | `kotlin`, `kt`, `kts` |
| Scala | `scala`, `sc` |
| C# | `csharp`, `c#`, `cs`, `dotnet` |
| PHP | `php`, `php8` |
| Perl | `perl`, `pl` |
| Lua | `lua` |
| R | `r`, `rlang` |
| Dart | `dart`, `flutter` |
| Elixir | `elixir`, `ex`, `exs` |
| Erlang | `erlang`, `erl` |
| Haskell | `haskell`, `hs` |
| Clojure | `clojure`, `clj`, `cljs` |
| OCaml | `ocaml`, `ml` |
| F# | `fsharp`, `f#`, `fs` |
| Zig | `zig` |
| Nim | `nim` |
| Julia | `julia`, `jl` |
| Objective-C | `objectivec`, `objc`, `obj-c` |
| Assembly | `assembly`, `asm`, `nasm`, `masm` |
| Bash | `bash`, `sh`, `shell`, `zsh` |
| PowerShell | `powershell`, `pwsh`, `ps1` |
| SQL | `sql`, `plsql`, `tsql`, `mysql`, `postgresql` |
| Groovy | `groovy`, `gvy` |
| MATLAB | `matlab`, `m` |
| Fortran | `fortran`, `f90`, `f95` |
| COBOL | `cobol`, `cob` |
| Lisp | `lisp`, `commonlisp`, `cl` |
| Scheme | `scheme`, `scm`, `racket` |
| Prolog | `prolog`, `pl` |
| Ada | `ada`, `adb`, `ads` |
| Pascal | `pascal`, `pas`, `delphi` |
| D | `d`, `dlang` |
| V | `v`, `vlang` |
| Crystal | `crystal`, `cr` |
| Solidity | `solidity`, `sol` |
| Vyper | `vyper`, `vy` |
| Move | `move` |
| WASM | `wasm`, `webassembly`, `wat` |
| Carbon | `carbon` |

## 🤖 Supported Models

### Anthropic (Claude)
- Claude-Opus-4.6 (`Opus-4.6`)
- Claude-Sonnet-4.5 (`Sonnet-4.5`)
- Claude-Haiku-4.5 (`Haiku-4.5`)

### OpenAI (ChatGPT)
- ChatGPT-5.2 (`GPT-5.2`)
- ChatGPT-Mini-5 (`GPT-Mini-5`)
- ChatGPT-Nano-5 (`GPT-Nano-5`)

## 📁 Project Structure

```
~/.nman/
├── config.properties    # Configuration file
└── docs/                # Generated documentation
    ├── java/
    ├── c/
    ├── cpp/
    └── go/
```

## 🔧 Building from Source

```bash
# Clone the repository
git clone https://github.com/jilet1308/nman.git
cd nman

# Build with Maven
mvn clean package

# Run the jar
java -jar target/nman-0.0.1.jar
```

## 📋 Roadmap

- [ ] Gemini support
- [ ] Windows support (This will most likely require a full re-write)
- [ ] Different file formats for docs (e.g. HTML, PDF)
- [ ] Chocolatey & winget support
- [ ] Alternate renderers (Are there any ???)
- [ ] Homebrew formulae

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Ismail D. Cokluk** ([@jilet1308](https://github.com/jilet1308))

---

<p align="center">
  Made with ☕ and a lot of <code>man</code> page frustration
</p>
