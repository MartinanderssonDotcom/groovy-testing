package com.martinandersson.groovy.testing.ood

import org.codehaus.groovy.runtime.typehandling.GroovyCastException

/**
 * We find that a class that has no constructor get two provided. One default
 * no-arg constructor, and one that accept a map (named parameters, used by
 * Groovy itself).<p>
 * 
 * We also find that if a class has a constructor with only 1 argument, then
 * this constructor can be called without an argument provided even though the
 * argument is not optional (!).<p>
 * 
 * This "feature" will thank god be removed: http://stackoverflow.com/a/13603240/1268003
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Constructors
{
    static main(ignored) {
        def p1 = ['Kalle', 12] as Person
        assert p1.class == Person
        assert p1.toString() == '[Kalle, 12]'
        
        Person p2 = ['Kalle', 12]
        assert p2.class == Person
        assert p2.toString() == '[Kalle, 12]'
        
        try {
            // Missing a param:
            p1 = ['Kalle'] as Person
            assert false
        }
        catch (GroovyCastException e) {
            /*
             * "org.codehaus.groovy.runtime.typehandling.GroovyCastException:
             *  Cannot cast object '[Kalle]' with class 'java.util.ArrayList' to
             *  class 'com.martinandersson.groovy.testing.ood.Person' due to:
             *  groovy.lang.GroovyRuntimeException: Could not find matching
             *  constructor for:
             *  com.martinandersson.groovy.testing.ood.Person(java.lang.String)"
             *  
             *  However, do not that a cause has not been set.
             */
            println e
            assert e.cause == null
        }
        
        // A constructor that accept named arguments in Person does not exist:
        try {
            Person(name: 'Kalle', age: 12)
            assert false
        }
        catch (MissingMethodException e) {
            assert e.class == MissingMethodException
        }
        
        // Address has no constructor declared and will get a constructor that accept named arguments:
        new Address()
        new Address(street: 'Banan', houseNumber: 1)
        new Address(street: 'Banan')
        new Address(houseNumber: 1)
        
        try {
            // Default constructor does not exist in Person:
            new Person()
        }
        catch (GroovyRuntimeException e) {
            assert e.class == GroovyRuntimeException
        }
        
        // Okay, expected:
        assert new ValueWrapper('Banan').val == 'Banan'        
        
        // But ValueWrapper who has a 1-arg constructor is callable without an argument (!):
        assert new ValueWrapper().val == 'something is null'
        
        assert new ValueWrapper(something: 'Banan').val == [something: 'Banan']
    }
}

class Person
{
    String name
    int age
    
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String toString() {
        // lol, implicit invocation of List.toString():
        [name, age]
    }
}

class Address
{
    String street
    int houseNumber
}

class ValueWrapper
{
    def val
    
    ValueWrapper(something) {
        val = something ?: 'something is null'
    }
}