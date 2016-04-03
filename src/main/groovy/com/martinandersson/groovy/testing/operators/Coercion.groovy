package com.martinandersson.groovy.testing.operators

/**
 * The 'as' operator.<p>
 * 
 * Sources:
 * <pre>
 *   http://groovy-lang.org/releasenotes/groovy-2.2.html#Groovy2.2releasenotes-Implicitclosurecoercion
 *   http://groovy-lang.org/operators.html#_coercion_operator
 * </pre>
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Coercion
{
    static main(ignored) {
        int one = 1
        
        try {
            // Second source claim this will fail:
            String s = (String) one
            // ..that is completely false.
            assert s == '1'
        }
        catch (ClassCastException e) {
            assert false
        }
        
        // Alternative:
        
        assert "1" == one as String
    }
}

