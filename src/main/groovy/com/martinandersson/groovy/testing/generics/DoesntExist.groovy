package com.martinandersson.groovy.testing.generics

/**
 * 
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class DoesntExist
{
    static void main (ignored) {
        /*
          The following example is stolen from "Groovy in Action, 2nd Ed, p. 101
          and "demonstrates that lists can contain mixture of types" - as if that
          would be a language feature. The fact that the author uses def all over
          the place and doesn't appear (at this point) to know much about
          generics has nothing to do with it?
        */
       
        def expr = ''
        
        for (i in [1, '*', 5]) {
            expr += i
        }
        
        assert expr == '1*5'
        
        // (end example)
        
        List<Integer> wtf = [1, '*', 5]
        wtf.add("Hello World")
        
        try {
            int x = wtf.last()
            assert false
        }
        catch (org.codehaus.groovy.runtime.typehandling.GroovyCastException e) {
            // "Cannot cast object 'Hello World' with class 'java.lang.String' to class 'int'"
            println e
        }
        
        // .. so in retrospect, yeah they can contain whatever and generics has no effect? TODO: Explain!
    }
}