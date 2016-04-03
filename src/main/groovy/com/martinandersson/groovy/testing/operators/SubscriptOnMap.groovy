package com.martinandersson.groovy.testing.operators

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class SubscriptOnMap
{
    static void main(ignored) {
        def map = ['one': 1, 'two': 2]
        
        // Really weird:
        assert map.class.is(null)
        assert map.getClass().is(LinkedHashMap)
        
        assert map['one'] == 1
        assert map.one == 1
        assert map.'one' == 1
        
        assert map == [one: 1, two: 2]
        
        // Using a variable reference in the declaration require parenthesis:
        def one = 'x'
        assert [one: 1] == ['one': 1]
        assert [(one): 1] == ['x': 1]
        
        // But not when you look it up:
        map = [(one): 1]
        assert map[one] == 1
    }
}