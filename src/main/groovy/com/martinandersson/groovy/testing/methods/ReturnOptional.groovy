package com.martinandersson.groovy.testing.methods

/**
 *
 * @author Martin Andersson ()
 */
class ReturnOptional
{
    static main(ignored) {
        assert noReturn() == null
    }
    
    static String noReturn() {
        
    }
}