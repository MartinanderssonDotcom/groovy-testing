package com.martinandersson.groovy.testing.collections

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Iteration
{
    static void main(ignored) {
        Collection c = null
        
        for (def something : c) {
            println 'This will never print, x is null.'
        }
        
        for (def something in c) {
            println 'This will never print, x is null.'
        }
        
        println 'But we survive to this piece of code.'
        
        // Now this is just seriously fucked up:
        for (x in new Object()) {
            println "Pojo object is not iterable, treated as Singleton collection: $x."
        }
        
        // Be mindful that the in keyword switch behavior if used in a boolean expression:
        assert 2 in [1, 2, 3]
    }
}