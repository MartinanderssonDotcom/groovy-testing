package com.martinandersson.groovy.testing.operators

/**
 * The Elvis operator is a shortened version of the ternary operator. The Java
 * version would require two evaluations of the true-part. This is in fact an
 * improvement, or helpful operator, to use.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Elvis
{
    static int callCount
    
    static main(ignored) {
        // Groovy less verbose, call msg() only once:
        def something = msg() ?: 'Default val.'
        assert something == 'msg'
        assert callCount == 1
        
        // Java more verbose, call msg() twice:
        something = msg() != null ? msg() : 'Default val.'
        assert something == 'msg'
        assert callCount == 3
    }
    
    static String msg() {
        ++callCount
        "msg"
    }
}