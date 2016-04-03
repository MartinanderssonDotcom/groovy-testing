package com.martinandersson.groovy.testing.methods

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Parameters
{
    static main(ignored) {
        // This would not compile in Java:
        vararg(1, 2, 3);
        
        // Lists or "named arguments" are not supported (Java bytecode cannot store parameter names):
        try {
            foo(['something', 'whatever'])
            assert false
        }
        catch (MissingMethodException e) {
            assert e.class == MissingMethodException
        }
        
        try {
            foo([whatever: 'whatever', something: 'something'])
            assert false
        }
        catch (MissingMethodException e) {
            assert e.class == MissingMethodException
        }
        
        try {
            foo whatever: 'whatever', something: 'something'
            assert false
        }
        catch (MissingMethodException e) {
            assert e.class == MissingMethodException
        }
        
        // If method accept one argument, a map, then all of a sudden "named arguments" work.
        // Groovy make the named arguments into a map:
        assert bar1(first: 1, second: 2) == 2
        
        // "Non-named" arguments are normal arguments:
        assert bar2(first: 1, second: 2, '!') == '2!'
        
        // May use optional arguments too:
        assert bar3(first: 1, second: 2) == 123
        
        // The map will always be the first argument, however client position it:
        assert bar4('xxx', one: 1, two: 2, 'yyy') == '[[one:1, two:2], xxx, yyy]'
        
        // 1-arg methods and constructors can actually be called without providing the argument!
        // To be removed: http://stackoverflow.com/a/13603240/1268003
        assert echo() == null
    }
    
    static vararg(Object[] whatever) {
        assert whatever.length == 3
    }
    
    static foo(something, whatever) {
    }
    
    static bar1(Map args) { args.size() }
    
    static bar2(args, something) { args.size() + something }
    
    static bar3(args, something = 123) { something }
    
    // Don't use myMap.class! That try to lookup a key, and return null. hahahaha...
    static bar4(first, second, third) {
        [first.toString(), second.toString(), third.toString()].toString()
    }
    
    static def echo(something) {
        something
    }
}