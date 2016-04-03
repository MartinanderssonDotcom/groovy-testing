package com.martinandersson.groovy.testing.keywords

/**
 * The final keyword mean shit for local variables. Final local variables are
 * mutable.<p>
 * 
 * Final fields are immutable. If property based access is used, then the crash
 * happens during runtime. Accessing a final field from within a class does not
 * compile.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Final
{
    private static final int staticFinal = 1
    
    private final int nonStaticFinal = 1
    
    final int finalProp = 1
    
    
    static void main(ignored) {
        /*
           "Groovy in Action", 2nd Ed, p. 166 says:
           
               "Groovy uses Java’s modifiers—the keywords private, protected, and public for
                modifying visibility; final for disallowing reassignment; and static to denote class
                variables. A nonstatic field is also known as an instance variable. These modifiers all have
                the same meaning as in Java."
           
           This is clearly wrong:
        */
       
        final def x = 1
        println x
        
        // 'final" doesn't mean shit:
        x = 2
        println x
        
        final int y = 1
        y = 3
        println y
        
        try {
            // Compiles but crash during runtime ("cannot set readonly property"):
            new Final().nonStaticFinal = 2
            assert false
        }
        catch (ReadOnlyPropertyException e) {
            assert e.class == ReadOnlyPropertyException
        }
        
        try {
            // Compiles but crash during runtime ("cannot set the property 'nonStaticFinal' because the backing field is final")
            new Final().@nonStaticFinal = 2
            assert false
        }
        catch (GroovyRuntimeException e) {
            assert e.class == GroovyRuntimeException
        }
        
        try {
            // Compiles but crash during runtime ("cannot set readonly property")
            new Final().finalProp = 2
            assert false
        }
        catch (ReadOnlyPropertyException e) {
            assert e.class == ReadOnlyPropertyException
        }
        
        try {
            // Compiles but crash during runtime ("cannot set readonly property"):
            new Final().@finalProp = 2
            assert false
        }
        catch (GroovyRuntimeException e) {
            assert e.class == GroovyRuntimeException
        }
        
        // Does not compile:
//        staticFinal = 2
        
        println "Over and out."
    }
    
    private void setFinalField(int newVal) {
        // Does not compile: "cannot modify final field 'nonStaticFinal" outside of constructor.":
//        nonStaticFinal = newVal;
        
        // DOes not compile: "cannot modify final field 'finalProp' outside of constructor":
//        finalProp = 123;
    }
}