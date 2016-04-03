package com.martinandersson.groovy.testing.closures

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Composition
{
    static void main(ignored) {
        Closure divideByTwo = { it / 2 },
                addByTen    = { it + 10 }
        
        Closure divThenAdd = divideByTwo >> addByTen,
                addThenDiv = divideByTwo << addByTen
        
        assert divThenAdd(2) == 11
        assert addThenDiv(2) == 6
    }
}