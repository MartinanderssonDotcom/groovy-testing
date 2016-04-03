package com.martinandersson.groovy.testing.operators

/**
 * Using the subscript operator on a Set.
 * 
 * @author Martin Anderssson (webmaster at martinandersson.com)
 */
class SubscriptOnSet
{
    static void main(ignored) {
        Set ints = [1, 2, 3]
        
        assert ints.class == java.util.LinkedHashSet
        assert ints[0] == 1
        
        try {
            def x = ints[0..1]
            assert false
        }
        catch (groovy.lang.MissingMethodException e) {
            // "No signature of method: java.util.LinkedHashSet.getAt() is applicable for argument types: (groovy.lang.IntRange) values: [0..1]"
            println e
        }
        
        try {
            ints[0] = 9
            assert false
        }
        catch (groovy.lang.MissingMethodException e) {
            // "No signature of method: java.util.LinkedHashSet.putAt() is applicable for argument types: (java.lang.Integer, java.lang.Integer) values: [0, 9]"
            println e
        }
    }
}