package com.martinandersson.groovy.testing.closures

/**
 * Groovy closures.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Closures
{
    static main(ignored) {
        def optionalArg = {
            "it: $it"
        }
        
        assert optionalArg() == 'it: null'
        assert optionalArg(1) == 'it: 1'
        
        try {
            optionalArg(1, 2)
            assert false
        }
        catch (MissingMethodException e) {}
        
        def noArg = { ->
            // Despite its name, hasProperty return MetaProperty:
            'it: ' + hasProperty('it')
        }
        
        assert noArg() == 'it: null'
        
        try {
            noArg(1)
            assert false
        }
        catch (MissingMethodException e) {}
        
        
        // The following example come from:
        // http://groovy.jmiguel.eu/groovy.codehaus.org/Scoping+and+the+Semantics+of+%22def%22.html
        
        // "it" refer only to the executing closure.
        
        def outer = {
            def inner = { it+1 }
            inner(it+1)
        }
        
        assert outer(1) == 3
        
        // Q: New instance? Yes.
        assert !getEchoClosure().is(getEchoClosure())
        
        int callCount = 0
        
        def c = {
            ++callCount
            def msg = 'Closure called.'
            println msg
            return msg
        }
        
        assert c() == 'Closure called.'
        assert callCount == 1
        
        assert c instanceof Closure
        assert c.call() == 'Closure called.'
        assert callCount == 2
        
        assert c instanceof Runnable
        assert c.run() == null
        assert callCount == 3
        
        // Cannot call arbitrary methods:
        try {
            assert c.xxx() == 'Closure called.'
            assert false
        }
        catch (MissingMethodException e) {
            assert e.class == MissingMethodException
            assert e.message ==
                'No signature of method: com.martinandersson.groovy.testing.closures.Closures$_main_closure4.xxx() is applicable for argument types: () values: []\n' +
                'Possible solutions: any(), any(), use([Ljava.lang.Object;), is(java.lang.Object), any(groovy.lang.Closure), use([Ljava.lang.Object;)'
        }
        
    }
    
    static Closure getEchoClosure() {
        return { it }
    }
}