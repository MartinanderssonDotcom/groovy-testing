package com.martinandersson.groovy.testing.ood

import com.martinandersson.groovy.testing.ood.lib.Person

/**
 * We learn that private and protected mean shit in Groovy.<p>
 * 
 * We also learned in Properties.groovy that same apply for fields marked as
 * private and protected.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class AccessModifiers
{
    static main(args) {
        Person p = new Person();
        
        // Methods (and classes and constructors) are public by default;
        assert p.noModifier() == 'noModifier'
        
        // The private keyword is simply ignored by groovy:
        assert p.privateModifier() == 'privateModifier'
        
        // ..same for protected:
        assert p.protectedModifier() == 'protectedModifier'
    }
}