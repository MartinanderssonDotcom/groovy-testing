package com.martinandersson.groovy.testing.methods

/**
 *
 * @author p8andem2
 */
class ReturnType
{
    // Methods with an explicit modifier may ommitt the return type:
    static main(ignored) {}
    static hello() {}
    
    private world() {}
    
    // Does not compile:
//    world() {}
    
    /*
     * An explicit modifier is needed "so that the compiler can make a
     * difference between a method declaration and a method call".
     * 
     * Source: http://docs.groovy-lang.org/next/html/documentation/core-semantics.html
     */
}