package com.martinandersson.groovy.testing.operators;

/**
 * Demonstrates the Spread Operator (*.).<p>
 * 
 * See: http://docs.groovy-lang.org/latest/html/documentation/index.html#_spread_operator
 */
class Spread
{
    static main(ignored) {
        usedOnIterable()
        usedAsMethodArgs()
        
        // There's also examples of how a map can be inlined in another map.
        // See online source referenced in JavaDoc.
        usedInsideListLiteral()
    }
    
    static usedOnIterable() {
        List runnables = [
            { println 'first'; 'first' }, { println 'second'; 'second' }
        ]
        
        assert runnables*.run() == [null, null]
        assert runnables*.call() == ['first', 'second']
        
        // Of course, we can even use an operator on null (as if null wasn't confusing enough, good work Groovy):
        assert null*.hahahaha == null
    }
    
    static usedAsMethodArgs() {
        List args = ['Hello', ', World!']
        assert concat(*args) == 'Hello, World!'
    }
    
    static String concat(arg1, arg2) {
        arg1 + arg2
    }
    
    static usedInsideListLiteral() {
        List twoAndThree = [2, 3]
        List numbers = [1, *twoAndThree, 4]
        assert numbers == [1, 2, 3, 4]
    }
}