package com.martinandersson.groovy.testing.closures

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class TypeSafety
{
    static void main(ignored) {
        Closure doubleMyInt = { int x ->
            x*2
        }
        
        assert doubleMyInt(2) == 4
        
        try {
            // Previous and next assertment would both work if param 'x' was declared to be of type double.
            doubleMyInt(2d)
            assert false
        }
        catch (MissingMethodException e) {
            // [..] No signature of method: [..]TypeSafety$_main_closure1.call() is applicable for argument types: (java.lang.Double) values: [2.0]
            println e
        }
    }
}