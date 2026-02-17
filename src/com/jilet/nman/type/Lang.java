package com.jilet.nman.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Lang {

    // alternateNames[0] => canon name, will generate folders with those names
    Java(new String[]{"java", "jvm", "jdk", "jav"}),
    C(new String[]{"c", "clang"}),
    Cpp(new String[]{"cpp", "c++", "cplusplus"}),
    Go(new String[]{"go", "golang"});


    @Getter
    private final String[] alternateNames;
}
