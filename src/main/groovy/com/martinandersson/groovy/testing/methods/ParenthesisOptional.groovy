package com.martinandersson.groovy.testing.methods

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class ParenthesisOptional
{
    static void main(ignored) {
        printOneArg 123
        printTwoArgs 123, 456
        
        List ints = [1, 2, 3]
        
        int zero = ints.indexOf(1)
        assert zero == 0
        
        zero = ints.indexOf 1
        assert zero == 0
        
        // Apparently, omitting parenthese can not be done if the "expression" is involved in anything else:
//        (ints.indexOf 1)
        
        // I believe this is a bug/issue with Groovy. See:
        //    http://stackoverflow.com/questions/34711081
        //    http://stackoverflow.com/questions/5061454
    }
    
    static void printOneArg(a) {
        println "\"printing\" $a"
    }
    
    static void printTwoArgs(a, b) {
        println "\"printing\" $a and $b"
    }
}