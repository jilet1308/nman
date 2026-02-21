package com.jilet.nman.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Lang {

    // alternateNames[0] => canon name, will generate folders with those names
    Java(new String[]{"java", "jvm", "jdk", "jav"}),
    C(new String[]{"c", "clang"}),
    Cpp(new String[]{"cpp", "c++", "cplusplus"}),
    Go(new String[]{"go", "golang"}),
    Python(new String[]{"python", "py", "python3", "py3"}),
    Rust(new String[]{"rust", "rs", "rustlang"}),
    JavaScript(new String[]{"javascript", "js", "node", "nodejs"}),
    TypeScript(new String[]{"typescript", "ts", "tsc"}),
    Ruby(new String[]{"ruby", "rb"}),
    Swift(new String[]{"swift"}),
    Kotlin(new String[]{"kotlin", "kt", "kts"}),
    Scala(new String[]{"scala", "sc"}),
    CSharp(new String[]{"csharp", "c#", "cs", "dotnet"}),
    PHP(new String[]{"php", "php8"}),
    Perl(new String[]{"perl", "pl"}),
    Lua(new String[]{"lua"}),
    R(new String[]{"r", "rlang"}),
    Dart(new String[]{"dart", "flutter"}),
    Elixir(new String[]{"elixir", "ex", "exs"}),
    Erlang(new String[]{"erlang", "erl"}),
    Haskell(new String[]{"haskell", "hs"}),
    Clojure(new String[]{"clojure", "clj", "cljs"}),
    OCaml(new String[]{"ocaml", "ml"}),
    FSharp(new String[]{"fsharp", "f#", "fs"}),
    Zig(new String[]{"zig"}),
    Nim(new String[]{"nim"}),
    Julia(new String[]{"julia", "jl"}),
    ObjectiveC(new String[]{"objectivec", "objc", "obj-c"}),
    Assembly(new String[]{"assembly", "asm", "nasm", "masm"}),
    Bash(new String[]{"bash", "sh", "shell", "zsh"}),
    PowerShell(new String[]{"powershell", "pwsh", "ps1"}),
    SQL(new String[]{"sql", "plsql", "tsql", "mysql", "postgresql"}),
    Groovy(new String[]{"groovy", "gvy"}),
    MATLAB(new String[]{"matlab", "m"}),
    Fortran(new String[]{"fortran", "f90", "f95"}),
    COBOL(new String[]{"cobol", "cob"}),
    Lisp(new String[]{"lisp", "commonlisp", "cl"}),
    Scheme(new String[]{"scheme", "scm", "racket"}),
    Prolog(new String[]{"prolog", "pl"}),
    Ada(new String[]{"ada", "adb", "ads"}),
    Pascal(new String[]{"pascal", "pas", "delphi"}),
    D(new String[]{"d", "dlang"}),
    V(new String[]{"v", "vlang"}),
    Crystal(new String[]{"crystal", "cr"}),
    Solidity(new String[]{"solidity", "sol"}),
    Vyper(new String[]{"vyper", "vy"}),
    Move(new String[]{"move"}),
    WASM(new String[]{"wasm", "webassembly", "wat"}),
    Carbon(new String[]{"carbon"});

    @Getter
    private final String[] alternateNames;
}
