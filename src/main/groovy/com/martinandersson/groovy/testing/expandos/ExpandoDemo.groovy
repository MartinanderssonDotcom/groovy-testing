package com.martinandersson.groovy.testing.expandos

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class ExpandoDemo
{
    static main(ignored) {
        // A map can dynamically add anything to it:
        Map map = [:]
        
        map.prop = {
            'Hello'
        }
        
        assert map.prop() == 'Hello'
        
        // The delegate of this closure is our demo class:
        assert map.prop.delegate == ExpandoDemo
        
        // An Expando class is not much different from a Map.
        // But, one difference is that the delegate is the Expando.
        // So this will not work on a map:
        
        map.val = 1
        map.getVal = {
            val
        }
        
        try {
            map.getVal()
            assert false
        }
        catch (MissingPropertyException e) {}
        
        // but, a similar construct work for Expandos:
        Expando expando = new Expando(val: 1)
        
        expando.getVal = {
            val
        }
        
        assert expando.getVal() == 1
    }
}