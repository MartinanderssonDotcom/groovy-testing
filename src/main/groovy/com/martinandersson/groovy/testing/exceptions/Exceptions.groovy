package com.martinandersson.groovy.testing.exceptions

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Exceptions
{
    static void main(ignored) {
        try {
            throwRuntime()
            assert false
        }
        catch (RuntimeException e) {
            assert e.class == RuntimeException
            assert e.message == "Runtime!"
            assert e.cause == null
            e.printStackTrace()
        }
        
        println()
        
        try {
            throwChecked()
            assert false
        }
        /*
         * Checked exceptions doesn't need to be catched, just like the method
         * doesn't need to have it in the method signature.
         */
        catch (IOException e) {
            assert e.class == IOException
            assert e.message == "Checked!"
            assert e.cause == null
            e.printStackTrace()
        }
    }
    
    static void throwRuntime() {
        throw new RuntimeException("Runtime!")
    }
    
    static void throwChecked() {
        throw new IOException("Checked!")
    }
}