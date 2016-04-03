package com.martinandersson.groovy.testing.collections

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class ConcurrentModification
{
    static void main(ignored) {
        list()
        map()
    }
    
    static void list() {
        List x = ['a', 'b', 'c']
        
        // Groovy, can use subscript operator to do concurrent modification:
        x.forEach {
            // It's actually the subscript operator impl. that doesn't behave in a consistent manner.
            x[x.indexOf(it)] = it.toUpperCase()
        }
        
        assert x == ['A', 'B', 'C']
        
        // Any other access and we're doomed, even in Groovy:
        try {
            x.forEach {
                int pos = x.indexOf(it)
                x.remove(pos)
                x.add(pos, it.toLowerCase())
            }
            assert false
        }
        catch (ConcurrentModificationException e) {
            assert x == ['a', 'B', 'C']
        }
        
        // .. true also for Groovy's each:
        try {
            x.each {
                int pos = x.indexOf(it)
                x.remove(pos)
                x.add(pos, it.toLowerCase())
            }
            assert false
        }
        catch (ConcurrentModificationException e) {
            assert x == ['a', 'B', 'C']
        }
    }
    
    static void map() {
        Map x = [a:1, b:2, c:3]
        
        // In both Java and Groovy, Map has no concurrent modification limitations:
        
        x.forEach { k, v ->
            x[k] = v*2
        }
        
        assert x == [a:2, b:4, c:6]
        
        try {
            x.forEach { k, v ->
                x.put(k, v.intdiv(2))
            }
        }
        catch (ConcurrentModificationException e) {
            assert false
        }
        
        assert x == [a:1, b:2, c:3]
        
        try {
            x.each { k, v ->
                x.put(k, v*2)
            }
        }
        catch (ConcurrentModificationException e) {
            assert false
        }
        
        assert x == [a:2, b:4, c:6]
    }
}