package com.martinandersson.groovy.testing.operators

/**
 * Using the subscript operator on a list.
 * 
 * @author Martin Anderssson (webmaster at martinandersson.com)
 */
class SubscriptOnList
{
    static void main(ignored) {
        sublists()
    }
    
    static void sublists() {
        /*
           "Groovy in Action", 2nd Ed, p. 99, claims that:
           
               "Ranges used within subscript assignments are a convenience
                feature to access Java's sublist support for lists. See also the
                JavaDoc for java.util.List#sublist."
           
            lol, nothing could be more wrong.
         */
        
        List ints = [1, 2, 3]
        
        // Error 1: if "fromIndex" and "toIndex" are the same, an empty list is returned:
        assert !ints.subList(0, 0) // "empty list is false"
        // ..Groovy return the element in place:
        assert ints[0..0] == [1]
        
        // Error 2: "toIndex" is exclusive:
        assert ints.subList(0, 1) == [1]
        // ..Groovy return two elements:
        assert ints[0..1] == [1, 2]
        
        // Error 3: sublists are read- and write through:
        List sub = ints.subList(0, 2)
        assert sub == [1, 2]
        sub[0] = 9
        assert ints[0] == 9
        ints[0] = 1
        assert sub[0] == 1
        // ..Groovy give you a new List object that is not backed by the original:
        sub = ints[0..1]
        assert sub == [1, 2]
        sub[0] = 9
        assert ints[0] == 1 // does not write through
        ints[0] = 123
        assert sub[0] == 9 // does not read through
    }
}