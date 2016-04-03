package com.martinandersson.groovy.testing.script

/*
 * From book "Groovy in Action, 2nd Ed", page 176:
 * 
 *     "If a Groovy file contains no class declaration, it’s handled as a script; that is, it’s
 *      transparently wrapped into a class of type Script. This automatically generated
 *      class has the same name as the source script filename (without the extension).
 *      The content of the file is wrapped into a run method, and an additional main
 *      method is constructed for easily starting the script."
 */

class BookWasWrong {
    static main(hmm) {
        println 'This class is totally ignored.'
        assert false
    }
}

println 'Hello, World!'

// We're not in a static context:
println toString()
assert this instanceof Script
assert this instanceof MyScript
assert getClass() == MyScript

try {
    // Calling a 1-arg method without provding one is a Groovy "feature".
    // http://stackoverflow.com/a/13603240/1268003
    MyScript.main()
    
    println "TODO: Explain why this is printed over and over again. (T${Thread.currentThread().id})"
}
catch (StackOverflowError e) {
    assert e.class == StackOverflowError
    println e
}


class BookWasTotallyWrong {}

// This would be problematic, won't compile:
//class MyScript {}