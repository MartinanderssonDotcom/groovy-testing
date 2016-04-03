package com.martinandersson.groovy.testing.jdkenhancements

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Maps
{
    static void main(ignored) {
        Map x = [a:1]
        
        x.each { e ->
            e.class == Map.Entry
            assert e.key == 'a'
            assert e.value == 1
        }
        
        x.each { k, v ->
            assert k == 'a'
            assert v == 1
        }
    }
}