package com.martinandersson.groovy.testing.ood

import org.codehaus.groovy.reflection.CachedField
import java.lang.reflect.Modifier;

/**
 * We find that 1) absence of an access modifier define a property (getter and
 * setter generated). With private, protected or public, no accessors are
 * generated. 2) private and protected keyword is useless. Field can be read and
 * written anyways (same for methods, see AccessModifiers.groovy). 3) A
 * final field (with no access modifier) tell Groovy to not create the setter
 * (getter is provided).
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Properties
{
    static main(ignored) {
        testNoModifier()
        testProtectedField()
        testPublicField()
        testPrivateField()
        testFinalField()
        testStaticField()
    }
    
    /**
     * We find that with no modifier, getters and setters are generated.<p>
     * 
     * This is expected. No big suprises:
     * 
     * <pre>
     *     "You can define a property with [..] an absent access modifier"
     *     Source: http://docs.groovy-lang.org/latest/html/documentation/#properties
     * </pre>
     * 
     * Same source also say that a field need a "mandatory access modifier"
     * which obviously isn't true.<p>
     * 
     * Also note that the field is reported to be private. Hence, public is not
     * default, unlike what the previously quoted page says (assuming 'property'
     * = 'field'):
     * 
     * <pre>
     *     "[Class] declarations and any property or method without an access modifier are public."
     * </pre>
     * 
     * This fallacy has spread like a wildfire across internet of morons. But it
     * is only classes and methods that are "public by default". Fields can be
     * <i>viewed</i> as such because Groovy will setup a pair of accessors.
     */
    static void testNoModifier() {
        NoModifier nm = new NoModifier()
        
        // Can read field:
        assert nm.x == 0
        
        // Can read field directly:
        assert nm.@x == 0
        
        // Getter created:
        assert nm.getX() == 0
        
        // Can write field:
        nm.x = 1
        assert nm.x == 1
        
        // Can write field directly:
        nm.@x = 2
        assert nm.x == 2
        
        // Setter created:
        nm.setX(3)
        assert nm.x == 3
        
        // Has property:
        MetaProperty metaProp = nm.hasProperty('x')
        assert metaProp != null
        assert metaProp instanceof MetaProperty
        assert metaProp.class == MetaBeanProperty
        
        // ..with getter and setter:
        assert metaProp.getter.toString() == 'public int com.martinandersson.groovy.testing.ood.NoModifier.getX()'
        assert metaProp.setter.toString() == 'public void com.martinandersson.groovy.testing.ood.NoModifier.setX(int)'
        
        // Property is public, field is private and non-final:
        assert metaProp.modifiers == Modifier.PUBLIC
        assert metaProp.field.modifiers == Modifier.PRIVATE
        assert metaProp.field.final == false
        
        // Has property in map:
        assert nm.properties.containsKey('x')
    }
    
    /**
     * Getters and setters is not created and protected field in another package
     * is accessible from this package.
     */
    static void testProtectedField() {
        // Fails lol:
//        com.martinandersson.groovy.testing.ood.lib.Person p
//                = new com.martinandersson.groovy.testing.ood.lib.Person()
        
        // Has to be on same line:
        com.martinandersson.groovy.testing.ood.lib.Person p = new com.martinandersson.groovy.testing.ood.lib.Person()        
        
        // Can read field:
        assert p.id == 1
        
        // Can read field directly:
        assert p.@id == 1
        
        // Getter does not exist:
        try {
            p.getId()
            assert false
        }
        catch (MissingMethodException e) { }
        
        // Can write field:
        p.id = 1
        assert p.id == 1
        
        // Can write field directly:
        p.@id = 2
        assert p.id == 2
        
        // Setter does not exist:
        try {
            p.setId(3)
            assert false
        }
        catch (MissingMethodException e) { }
        
        // Getter and setter does not exist. But, it still has property:
        // (Obviously, Groovy's class hierarchy here is fucked up as is the absense of a proper definition of "property")
        MetaProperty cachedField = p.hasProperty('id')
        assert cachedField != null
        assert cachedField instanceof MetaProperty
        assert cachedField.class == CachedField
        assert cachedField.final == false
        
        // Property is protected, field is protected and non-final:
        assert cachedField.modifiers == Modifier.PROTECTED
        assert cachedField.final == false
        
        // Does not have property in map (contrary to what hasProperty() imply):
        assert !p.properties.containsKey('id')
    }
    
    /**
     * Groovy docs claim:
     * 
     * <pre>
     *     "Public fields are turned into properties automatically [..]."
     *     Source: http://docs.groovy-lang.org/latest/html/documentation/#_class
     * </pre>
     * 
     * So this test should not be different from the last one, but.. it is.<p>
     * 
     * Getters and setters is not created.
     */
    static void testPublicField() {
        PublicField pm = new PublicField()
        
        // Can read field:
        assert pm.x == 0
        
        // Can read field directly:
        assert pm.@x == 0
        
        // Getter does not exist:
        try {
            pm.getX()
            assert false
        }
        catch (MissingMethodException e) { }
        
        // Can write field:
        pm.x = 1
        assert pm.x == 1
        
        // Can write field directly:
        pm.@x = 2
        assert pm.x == 2
        
        // Setter does not exist:
        try {
            pm.setX(3)
            assert false
        }
        catch (MissingMethodException e) { }
        
        // Getter and setter does not exist. But, it still has property:
        // (Obviously, Groovy's class hierarchy here is fucked up as is the absense of a proper definition of "property")
        MetaProperty cachedField = pm.hasProperty('x')
        assert cachedField != null
        assert cachedField instanceof MetaProperty
        assert cachedField.class == CachedField
        assert cachedField.final == false
        
        // Property is public, field is public and non-final:
        assert cachedField.modifiers == Modifier.PUBLIC
        assert cachedField.final == false
        
        // Does not have property in map (contrary to what hasProperty() imply):
        assert !pm.properties.containsKey('x')
    }
    
    /**
     * We find that with a private modifier, the behavior is exactly the same as
     * with a public modifier, except the field is reported to be private.<p>
     * 
     * If you google, it appears that "private" is always ignored by Groovy
     * despite documentation and literature telling otherwise.<p>
     * 
     * <pre>
     *     "Groovy classes are very similar to Java classes, being compatible to
     *      those ones at JVM level. They may have methods and fields/properties,
     *      which can have the same modifiers (public, protected, private,
     *      static, etc) as Java classes."
     *     Source: http://docs.groovy-lang.org/latest/html/documentation/#_class
     * </pre>
     * 
     * <pre>
     *     "Groovy uses Java’s modifiers—the keywords private, protected, and
     *      public for modifying visibility; final for disallowing reassignment;
     *      and static to denote class variables. A nonstatic field is also known
     *      as an instance variable. These modifiers all have the same meaning
     *      as in Java."
     *     Source: Book "Groovy in Action", 2nd Ed, p. 166.
     * </pre>
     */
    static void testPrivateField() {
        PrivateField pf = new PrivateField()
        
        // Can read field:
        assert pf.x == 0
        
        // Can read field directly:
        assert pf.@x == 0
        
        // Getter does not exist:
        try {
            pf.getX()
            assert false
        }
        catch (MissingMethodException e) { }
        
        // Can write field:
        pf.x = 1
        assert pf.x == 1
        
        // Can write field directly:
        pf.@x = 2
        assert pf.x == 2
        
        // Setter does not exist:
        try {
            pf.setX(3)
            assert false
        }
        catch (MissingMethodException e) { }
        
        // Getter and setter does not exist. But, it still has property:
        // (Obviously, Groovy's class hierarchy here is fucked up as is the absense of a proper definition of "property")
        MetaProperty cachedField = pf.hasProperty('x')
        assert cachedField != null
        assert cachedField instanceof MetaProperty
        assert cachedField.class == CachedField
        assert cachedField.final == false
        
        // Property is public, field is private and non-final:
        assert cachedField.modifiers == Modifier.PRIVATE
        assert cachedField.final == false
        
        // Does not have property in map (contrary to what hasProperty() imply):
        assert !pf.properties.containsKey('x')
    }
    
    /**
     * <pre>
     *     "If a property is declared final, no setter is generated [..]."
     *     Source: http://docs.groovy-lang.org/latest/html/documentation/#fields
     * </pre>
     * 
     * So this test behave much like the test with no modifier. The big
     * difference is that we don't get a setter.<p>
     * 
     * Note local final variables can be mutated. I.e., final keyword for local
     * variables mean shit. See {@code Final.groovy}.
     */
    static void testFinalField() {
        FinalField ff = new FinalField()
        
        // Can read field:
        assert ff.x == 0
        
        // Can read field directly:
        assert ff.@x == 0
        
        // Getter created:
        assert ff.getX() == 0
        
        // Can not write field using property access:
        try {
            ff.x = 1
            assert false
        }
        catch (ReadOnlyPropertyException e) { }
        
        // Can not write field directly:
        try {
            ff.@x = 2
            assert false
        }
        catch (GroovyRuntimeException e) {
            // "Cannot set the property 'x' because the backing field is final."
        }
        
        // Setter does not exist:
        try {
            ff.setX(3)
            assert false
        }
        catch (MissingMethodException e) { }
        
        // Has property:
        MetaProperty metaProp = ff.hasProperty('x')
        assert metaProp != null
        assert metaProp instanceof MetaProperty
        assert metaProp.class == MetaBeanProperty
        
        // ..with getter, but no setter:
        assert metaProp.getter.toString() == 'public final int com.martinandersson.groovy.testing.ood.FinalField.getX()'
        assert metaProp.setter == null
        
        // Property 'modifiers' return only what the getter modifiers is, field is private and non-final:
        assert metaProp.modifiers == metaProp.getter.modifiers
        assert Modifier.isFinal(metaProp.field.modifiers)
        assert metaProp.field.final == true
        
        // Has property in map:
        assert ff.properties.containsKey('x')
    }
    
    static void testStaticField() {
        StaticField sf = new StaticField()
        
        // Can read field:
        assert sf.x == 0
        
        // Can read field directly:
        assert sf.@x == 0
        
        // Getters created (!):
        assert sf.getX() == 0
        assert StaticField.getX() == 0
        
        // Can write field:
        sf.x = 1
        assert sf.x == 1
        
        // Can write field directly:
        sf.@x = 2
        assert sf.x == 2
        
        // Setters created (!):
        sf.setX(3)
        assert sf.x == 3
        StaticField.setX(4)
        assert sf.x == 4
        
        // Has property:
        MetaProperty metaProp = sf.hasProperty('x')
        assert metaProp != null
        assert metaProp instanceof MetaProperty
        assert metaProp.class == MetaBeanProperty
        
        // ..with getter and setter (!):
        assert metaProp.getter.toString() == 'public static int com.martinandersson.groovy.testing.ood.StaticField.getX()'
        assert metaProp.setter.toString() == 'public static void com.martinandersson.groovy.testing.ood.StaticField.setX(int)'
        
        // Property is XXX, field is XXX and non-final:
        assert metaProp.modifiers == 9
        assert metaProp.field.modifiers == 10
        assert metaProp.field.final == false
        
        // Has property in map:
        assert sf.properties.containsKey('x')
    }
}

class NoModifier
{
    int x
}

class ProtectedField
{
    protected int x
}

class PublicField
{
    public int x
}

class PrivateField
{
    private int x
}

class FinalField
{
    final int x
}

class StaticField
{
    static int x
}