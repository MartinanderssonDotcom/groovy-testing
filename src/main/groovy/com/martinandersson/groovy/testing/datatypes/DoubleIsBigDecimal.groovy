package com.martinandersson.groovy.testing.datatypes

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
class DoubleIsBigDecimal
{
    static void main(ignored) {
        doubleTypeWouldBrakeThisExample();
    }
    
    static void doubleTypeWouldBrakeThisExample() {
        def funds = 1.00;
        assert funds.class.is(BigDecimal)
        
        int itemsBought = 0;
        
        for (def price = 0.10; funds >= price; price += 0.10) {
            assert price.class.is(BigDecimal)
            
            funds -= price;
            itemsBought++;
        }
        
        println "itemsBought: " + itemsBought;
        println 'Change: $' + funds;
    }
}