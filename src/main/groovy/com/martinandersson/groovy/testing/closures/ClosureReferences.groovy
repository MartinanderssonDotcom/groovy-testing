package com.martinandersson.groovy.testing.closures

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class ClosureReferences
{
    static void main(ignored) {
        Closure p = ClosureReferences.&say
        
        p "Hello"
        
        p = this.&say
        
        p "World"
        
        // This does not compile:
//        p = .&say
//        p = &say
        
    }
    
    static void say(what) {
        println what
    }
}