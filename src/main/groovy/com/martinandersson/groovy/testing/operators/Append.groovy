package com.martinandersson.groovy.testing.operators

/**
 * 
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class Append
{
    static void main(String... ignored) {
        def msg = 'hello'
        assert msg.class == String
        
        msg += ' world'
        assert msg == 'hello world'
        
        // '<<' ( leftShift() ) operator is some kind of append thing.
        // When '<<' is used on a String, it returns a StringBuffer:
        def builder = msg << '!'
        assert builder.class == StringBuffer
        
        assert builder.toString() == 'hello world!'
        
        // msg variable untouched:
        assert msg.class == String
        assert msg == 'hello world'
        
        // '<<=' ("Bitwise Left Shift Assign Operator") can be used to make the msg variable change to a StringBuffer.
        msg <<= '!'
        assert msg.class == StringBuffer
        assert msg.toString() == 'hello world!'
        
        // ..but that only applies if 'msg' is def/Object. If String, the '<<=' make a new String:
        String typed = 'hello'
        String backup = typed
        typed <<= ' world!'
        assert typed == 'hello world!'
        assert typed.class == String
        // Thank god at least we got a new reference:
        assert !typed.is(backup)
        
        
        // Q: If I append on a list, will I get back a new list?
        // A: no.
        
        List ints = [1, 2, 3]
        ints << 4
        assert ints == [1, 2, 3, 4]
        assert ints.is(ints << 5)
        assert ints == [1, 2, 3, 4, 5]
        
        // The '<<=' operator in this case has no point to it (google won't reveal shit about this operator really):
        assert ints.is(ints <<= 6)
        assert ints == [1, 2, 3, 4, 5, 6]
    }
}