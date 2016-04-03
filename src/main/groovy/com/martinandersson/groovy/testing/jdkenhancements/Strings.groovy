package com.martinandersson.groovy.testing.jdkenhancements

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Strings
{
    static void main(ignored) {
        String msg = 'Hello World'
        
        for (char c : msg) {
            print c
        }
        
        println()
        
        for (char c in msg) {
            print c
        }
        
        println()
        
        assert 'H' == msg[0]
    }
}