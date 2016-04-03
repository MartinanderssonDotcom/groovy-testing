package com.martinandersson.groovy.testing.methods

/**
 * According to this source:
 * <pre>
 *   http://docs.groovy-lang.org/next/html/documentation/core-metaprogramming.html
 * </pre>
 * 
 * ..then 1) invokeMethod is always called if class implements
 * GroovyInterceptable, 2-a) otherwise (class does not implement
 * GroovyInterceptable) invokeMethod is called only if method is missing.<p>
 * 
 * 2-a is bothersome because roughly speaking, JavaDoc of GroovyInterceptable
 * states that invokeMethod is only called if class implement this interface but
 * that is not the case. This JavaDoc does not explicitly forbid dispatch to
 * invokeMethod if the called method is missing, but I believe that most humans
 * will come to the conclusion - after reading the JavaDoc - that invokeMethod
 * is called <u>only if</u> class implements this interface. This conclusion is
 * wrong, hence I claim the JavaDoc is wrong.<p>
 * 
 * The previous source also show that, 2-b) if the class has a methodMissing
 * method, then this guy is called and not invokeMethod.<p>
 * 
 * More oddities exists. 3) methodMissing is never called if class implement
 * GroovyInterceptable (this is lie as proven by this test class). 4)
 * invokeMethod is never called if class does not implement GroovyInterceptable
 * but has a methodMissing method.<p>
 * 
 * 2 methods, 4 results. Yeah should most likely be redesigned to 1 single
 * invokeMethod method that either accept a boolean flag ("method exists or
 * not") or require that the class developer use another API for that
 * information (like MetaObjectProtocol.respondsTo()). Also, I believe the
 * design team should sit down and decide if the GroovyInterceptable has a true
 * signifant meaning or remove this type.<p>
 * 
 * As if all this wasn't confusing enough. invokeMethod can also be used to..
 * invoke a method. You see, if this method is not overridden, then invokeMethod
 * is a Groovy-provided lookup and dispatch mechanism. Hence, the semantics of
 * this method has two different purposes. Furthermore, the class developer may
 * also implement: get(), getAt(), getProperty() and propertyMissing(). Multiply
 * everything together and at least what I end up with is a massive headache.<p>
 * 
 * The getXXX methods are demonstrated in:
 * <pre>
 *   com.martinandersson.groovy.testing.operators.SubscriptOnCustom
 * </pre>
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class MethodAndPropertyMissing
{
    static main(ignored) {
        def obj = new HasInvokeMethodIsGI()
        
        // "implements GroovyInterceptable" cause all calls to go to invokeMethod, even for methods that does not exists:
        assert obj.echo(1) == "invokeMethod: 1"
        assert obj.xxxx(1) == 'invokeMethod: 1'
        
        // GroovyInterceptable, for whatever reason, is only a marker interface who might tell a lie:
        obj = new DoesNotHaveInvokeMethodButIsGI()
        assert obj.echo(1) == 'echo: 1'
        
        // Without the interface, invokeMethod is only called if method is missing:
        obj = new HasInvokeMethod()
        assert obj.echo(1) == "echo: 1"
        assert obj.xxxx(1) == 'invokeMethod: 1'
        
        // Without the interface and without invokeMethod, methodMissing is called for methods missing:
        obj = new HasMethodMissing()
        assert obj.echo(1) == "echo: 1"
        assert obj.xxxx(1) == 'methodMissing: 1'
        
        // methodMissing is not called for the implicit access of setProp:
        try {
            obj.prop = 123
            assert false
        }
        catch (MissingPropertyException e) {}
        
        // ..if methodMissing exist but class is GroovyInterceptable, then methodMissing is called:
        // (this should not happen according to the diagram in the referenced source)
        obj = new HasMethodMissingIsGI()
        assert obj.echo(1) == "echo: 1"
        assert obj.xxxx(1) == 'methodMissing: 1'
        
        // These two cases has expected results..
        
        obj = new HasMethodMissingAndInvokeMethod()
        assert obj.echo(1) == "echo: 1"
        assert obj.xxxx(1) == 'methodMissing: 1'
        
        obj = new HasMethodMissingAndInvokeMethodIsGI()
        assert obj.echo(1) == "invokeMethod: 1"
        assert obj.xxxx(1) == 'invokeMethod: 1'
        
        // invokeMethod does not intercept property setter:
        
        obj = new HasInvokeMethodAndPropertyMissing()
        obj.prop = 'Hello'
        assert obj.prop == 'Hello'
        
        obj.xxxx = 'World'
        assert obj.prop == 'propertyMissing: xxxx, World'
    }
}

abstract class Echo {
    def echo(something) {
        "echo: $something"
    }
}

class HasInvokeMethodIsGI extends Echo implements GroovyInterceptable
{
    def invokeMethod(String name, Object args) {
        "invokeMethod: ${args[0]}"
    }
}

class DoesNotHaveInvokeMethodButIsGI extends Echo
{
    // Empty
}

class HasInvokeMethod extends Echo
{
    def invokeMethod(String name, Object args) {
        "invokeMethod: ${args[0]}"
    }
}

class HasMethodMissing extends Echo
{
    def methodMissing(String name, Object args) {
        "methodMissing: ${args[0]}"
    }
}

class HasMethodMissingIsGI extends Echo implements GroovyInterceptable
{
    def methodMissing(String name, Object args) {
        "methodMissing: ${args[0]}"
    }
}

class HasMethodMissingAndInvokeMethod extends Echo
{
    def methodMissing(String name, Object args) {
        "methodMissing: ${args[0]}"
    }
    
    def invokeMethod(String name, Object args) {
        "invokeMethod: ${args[0]}"
    }
}

class HasMethodMissingAndInvokeMethodIsGI extends Echo implements GroovyInterceptable
{
    def methodMissing(String name, Object args) {
        "methodMissing: ${args[0]}"
    }
    
    def invokeMethod(String name, Object args) {
        "invokeMethod: ${args[0]}"
    }
}

class HasInvokeMethodAndPropertyMissing implements GroovyInterceptable
{
    String prop
    
    def invokeMethod(String name, Object arg) {
        "invokeMethod: ${arg}"
    }
    
    // Intercept missing setters:
    def propertyMissing(String name, Object arg) {
        prop = "propertyMissing: $name, $arg"
    }
}