package com.martinandersson.groovy.testing.operators

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Switch
{
    static void main(ignored) {
        // This wouldn't compile in Java. But Groovy's switch is really one long if else if..
        switch ("abc") {
            case "abc": assert true; break
            case "abc:": assert false
        }
        
        // "Collection case values match if the switch value is contained in the collection. This also includes ranges (since they are Lists)"
        // Source: http://groovy-lang.org/semantics.html#_switch_case
        
        switch ([1, 2, 3]) {
            case [1, ['a', 'b']]:
                assert false
            case [1, 2, 3]:
                assert false
            case [1, 2, 3, 4]:
                assert false
            // This is the shit:
            case [1, [1, 2, 3], 3]:
                assert true
                break;
            default:
                assert false
        }
        
        // Q: Using two different lists with the same contents, where the fuck does a switch go?
        // A: first case that match.

        List one = new ArrayList<>() << 'x',
             two = new ArrayList<>() << 'x'
             
        assert !one.is(two)
        assert one == two
        
        // one is picked:
        switch ('x') {
            case one:
                assert true
                break
            case two:
            default:
                assert false
        }
        
        // two is picked:
        switch ('x') {
            case two:
                assert true
                break
            case one:
            default:
                assert false
        }
    }
}