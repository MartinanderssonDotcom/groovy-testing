package com.martinandersson.groovy.testing.methods

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class MethodCall
{
    static main(ignored) {
        MethodCall m = new MethodCall();
        
        assert m.invokeMethod('hello', 'world') == '!'
        assert m.'hello'('world') == '!'
        assert m."${'hello'}"('world') == '!'
    }
    
    def hello(anything) {
        assert anything == 'world'
        return '!'
    }
}