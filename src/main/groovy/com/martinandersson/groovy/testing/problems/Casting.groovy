package com.martinandersson.groovy.testing.problems

import java.util.concurrent.atomic.AtomicInteger

/**
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Casting
{
    static void main(ignored) {
        AtomicInteger x = new AtomicInteger(10)
        assert x.class == AtomicInteger
        
        acceptAndIncrementSomething(x)
        
        // x remain untouched:
        assert x.get() == 10
    }
    
    static void acceptAndIncrementSomething(x) {
        ++x
        assert x == 11
        
        // x is locally a fucking Integer:
        assert x.class == Integer
    }
}