package com.martinandersson.groovy.testing.traits

/**
 * "interfaces carrying both default implementations and state"
 * 
 * Source: http://docs.groovy-lang.org/next/html/documentation/core-traits.html
 * 
 * Basically, these are all fancy words to say "we reintroduce the problematic
 * construct of multiple inheritance because we think complexity is cool
 * shit."<p>
 * 
 * A trait is a class. And, Groovy now allow another class to extend many of
 * them. That is basically it. We can also, as demonstrated in this file, let
 * one object instance pseudo magically extend a "trait" during runtime.<p>
 * 
 * A trait cannot declare a constructor, not even a no-arg constructor or this
 * file fail to compile (of course, the illegal trait will not be marked red by
 * NetBeans IDE 8.1).
 * 
 * @author Martin Andersson (webmaster at martinadersson.com)
 */
class Trait
{
    static main(ignored) {
        Person delegate = new Person()
        IdAsString decorator = delegate as IdAsString
        
        // There is a clear link betwee these two things:
        assert "Id: $delegate.id" == decorator.getIdAsString()
        
        // But the trait is not the same object.
        assert delegate != decorator
        assert !delegate.is(decorator)
        
        // In fact, the trait is not even same type anymore:
        assert !(decorator instanceof Person)
        assert decorator.class != Person
        
        // The trait is luckily enough, instance of the trait itself:
        assert decorator instanceof IdAsString
        
        // GOTCHA. Even though the trait is instanceof IdAsString, the class is not (thank you Groovy, you are so clever):
        assert decorator.class != IdAsString
        
        // According to Java, it is not a Proxy:
        assert !java.lang.reflect.Proxy.isProxyClass(decorator.getClass())
        
        
        // A class can "implement" the trait, doesn't have to be linked during runtime:
        AnotherPerson ap = new AnotherPerson()
        assert "Id: $ap.id" == ap.getIdAsString()
        
        // This thing is an instanceof of both:
        assert ap instanceof AnotherPerson
        assert ap instanceof IdAsString
        assert ap.class == AnotherPerson
        
        // Using many traits:
        def unknownStaticType = delegate.withTraits IdAsString, IdAsDouble
        assert "Id: $delegate.id" == unknownStaticType.getIdAsString()
        assert delegate.id == unknownStaticType.getIdAsDouble()
        assert unknownStaticType instanceof IdAsString
        assert unknownStaticType instanceof IdAsDouble
    }
}

class Person
{
    int id = System.identityHashCode(this)
}

trait IdAsString
{
    String getIdAsString() {
        "Id: $id"
    }
}

class AnotherPerson implements IdAsString
{
    int id = System.identityHashCode(this)
}

trait IdAsDouble
{
    double getIdAsDouble() {
        id
    }
}