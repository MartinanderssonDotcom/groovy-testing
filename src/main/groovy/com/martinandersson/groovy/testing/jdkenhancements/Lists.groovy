package com.martinandersson.groovy.testing.jdkenhancements

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Lists
{
    static void main(ignored) {
        // List.plus() return a new instance.
        
        List ints = [1, 2, 3]
        List another = ints + 4
        
        assert ints == [1, 2, 3]
        assert another == [1, 2, 3, 4]
        
        // If appending to same list, use '+=' (or '<<')
        ints += 4
        assert ints == [1, 2, 3, 4]
        
        ints.each {
            assert it > 0 && it < 5
        }
        
        ints.eachWithIndex { val, index ->
            assert ints.indexOf(val) == index
        }
        
        // TODO: See Maps.groovy in this package. Why does Map provide an overloaded
        //       each and List don't (list require ugly eachWithIndex)?
        
        try {
            ints.each { val, index -> }
            assert false
        }
        catch (MissingMethodException e) {
            // No signature of method: [..]$_main_closure3.doCall() is applicable for argument types: (java.lang.Integer) values: [1]
            println e
        }
    }
}