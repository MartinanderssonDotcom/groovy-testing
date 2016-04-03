package com.martinandersson.groovy.testing.operators

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class SubscriptOnCustom
{
    static main(ignored) {
        // Private fields, are not private.
        assert new Supertype()['x'] == 1
        assert new Subtype()['y'] == 2
        
        try {
            new Subtype()['x']
            assert false
        }
        catch (MissingPropertyException e) {
            assert e.class == MissingPropertyException
        }
        
        // This override the subscript operator:
        OverrideGet o = new OverrideGet()
        assert o['hello'] == 'getAt() got hello'
        
        // We can also override the "field access operator":
        assert o.world == 'get() got world'
        
        // Visible field does not go through get():
        assert o.y == 2
        
        // If class override both get() and getProperty(), then getProperty() is the one called:
        assert new OverrideGetAndGetProperty().blabla == "getProperty() got blabla"
        assert new OverrideGetAndGetProperty().exist == "getProperty() got exist"
        
        /*
         * "they have different places in the MOP. if you request a property then
         *  it goes through this chain: getProperty, MetaClass#getProperty, get"
         *  
         * Source: http://www.groovy-lang.org/mailing-lists.html#nabble-td5144901
         */
    }
}

class Supertype
{
    private int x = 1
    
    protected int y = 2
}

class Subtype extends Supertype
{
}

class OverrideGet extends Supertype
{
    // "Startup fails" with the override annotation. TODO: Explain.
    
//    @Override
    Object getAt(String prop) {
        "getAt() got $prop"
    }
    
//    @Override
    Object get(String prop) {
        "get() got $prop"
    }
}

class OverrideGetAndGetProperty
{
    private int exist = 1
    
//    @Override
    Object get(String prop) {
        "get() got $prop"
    }
    
    @Override // <-- This annotation work (we override from GroovyObject).
    Object getProperty(String prop) {
        "getProperty() got $prop"
    }
}