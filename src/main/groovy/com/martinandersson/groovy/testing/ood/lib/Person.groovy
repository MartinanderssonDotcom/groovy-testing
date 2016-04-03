package com.martinandersson.groovy.testing.ood.lib

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Person
{
    protected int id = 1
    
    String noModifier() {
        'noModifier'
    }
    
    private String privateModifier() {
        'privateModifier'
    }
    
    protected String protectedModifier() {
        'protectedModifier'
    }
    
    public String publicModifier() {
        'publicModifier'
    }
}