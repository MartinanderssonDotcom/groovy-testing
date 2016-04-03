package com.martinandersson.groovy.testing.keywords

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Assert
{
    static void main(ignored) {
        assert true
        
        // Comma, only in Groovy (not Java)
        try {
            assert false, 'my message'
        }
        catch (AssertionError e) {
            assert e.message == 'my message. Expression: false'
        }
        
        try {
            assert false : 'But groovy may use a colon too.'
            // i.e., book "Groovy in Action", 2nd Edition, lie on page 156 which says:
            // "a colon instead of a comma"
            // Actually, they lie two times on this page:
            //   They say that Groovy's assert CAN be enabled/disabled.
            // It is the other way around.
        }
        catch (AssertionError e) {
            // Two dots (very very smart implementation.. not):
            assert e.message == 'But groovy may use a colon too.. Expression: false'
        }
    }
}