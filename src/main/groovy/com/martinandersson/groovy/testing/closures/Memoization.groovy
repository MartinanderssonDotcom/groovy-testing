package com.martinandersson.groovy.testing.closures

import groovy.transform.Memoized
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Memoization
{
    static void main(ignored) {
        Closure multiplyByTwo = { int val, AtomicInteger counter ->
            counter.incrementAndGet()
            val*2
        }.memoizeAtMost(2)
        
        runTestUsing multiplyByTwo
        runTestUsing Memoization.&multiplyByTwo
    }
    
    @Memoized(maxCacheSize = 2)
    static int multiplyByTwo(int val, AtomicInteger counter) {
        counter.incrementAndGet()
        val*2
    }
    
    static void runTestUsing(Closure x) {
        AtomicInteger c = new AtomicInteger()
        
        // Not cached:
        assert x(1, c) == 2
        assert c.get() == 1
        
        // Is cached:
        assert x(1, c) == 2
        assert c.get() == 1
        
        // We hit the max:
        assert x(2, c) == 4
        assert c.get() == 2
        
        // Both cached:
        x(1, c)
        x(2, c)
        assert c.get() == 2
        
        // We exceed the max:
        assert x(3, c) == 6
        assert c.get() == 3
        
        // Which cached value was removed?
        // Least recently used, i.e., 1 (think "oldest used").
        
        // Does not hit counter:
        x(2, c)
        x(3, c)
        assert c.get() == 3
        
        // Do hit:
        x(1, c)
        assert c.get() == 4
    }
}