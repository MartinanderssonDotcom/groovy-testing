package com.martinandersson.groovy.testing.closures

import org.codehaus.groovy.runtime.typehandling.GroovyCastException

/**
 * 
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Scope
{
    static void main(ignored) {
        refExamination()
        unInitializedVars()
    }
    
    static refExamination() {
        Integer x = new Integer(12345678)
        assert x == 12345678
        
        Integer refCopy = x
        assert x.is(refCopy)
        
        def increment = {
            ++x
        }
        
        increment()
        
        assert x == 12345679
        
        // But was the actual reference changed? Yes.
        assert !x.is(refCopy)
        
        // Untouched:
        assert refCopy == 12345678        
    }
    
    static unInitializedVars() {
        int i1
        
        // Of course, why not:
        int r1 = i1
        assert r1 == 0
        ++i1
        assert i1 == 1
        
        // Doing the same from a closure produce weird exception, at runtime of course:
        int i2
        def c1 = {
            // ..this time, i2 is null:
            assert i2 == null
            
            // ..which if you try to use will cause the most weird exception ever:
            try {
                int r2 = i2
                assert false
            }
            catch (GroovyCastException e) {
                assert e.class == GroovyCastException
                assert e.message == "Cannot cast object 'null' with class 'null' to class 'int'. Try 'java.lang.Integer' instead"
            }
        }
        c1()
        
        // But it "work" - yes, even inside a Closure - if we use Integer:
        Integer i3
        def c2 = {
            Integer r3 = i3
            assert r3 == null // <-- null!
        }
        c2()
    }
}