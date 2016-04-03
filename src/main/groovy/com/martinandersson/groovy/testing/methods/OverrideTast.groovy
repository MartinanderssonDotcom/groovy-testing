package com.martinandersson.groovy.testing.methods

import java.lang.Override

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class OverrideTast
{
    static main(ignored) {
        MyOverride o = new MyOverride()
        assert o instanceof GroovyObject
        
        assert o.toString() == 'MyOverride.toString()'
        assert o.dump() == 'MyOverride.dump()'
        
        assert new MyOverride2().dump().class == String
        
        // Groovy doesn't add the method any more:
        assert new MyOverride3().dump() == 1
    }
}

class MyOverride
{    
    @Override
    String toString() {
        'MyOverride.toString()'
    }
    
    // Override a method added by Groovy (or Groovy does not add the method any more?):
//    @Override
    String dump() {
        'MyOverride.dump()'
    }
}

class MyOverride2
{
}

class MyOverride3
{
    int dump() {
        1
    }
}