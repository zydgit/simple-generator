package com.hand.hls.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MathUtils {

    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(Double.toString(((Number) value).doubleValue()));
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static BigDecimal nvlToZero(BigDecimal value) {
        if(value == null) {
            return BigDecimal.ZERO;
        }
        return value.multiply(new BigDecimal("100000000"));
    }


    public static BigDecimal nvl2Zero(BigDecimal value) {
        if(value == null) {
            return BigDecimal.ZERO;
        }
        return value;
    }


    public static BigDecimal div(Integer v1, Integer v2) {
        if (v1 == null) {
            return BigDecimal.ZERO;
        }

        if (v2 == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 16,BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            return BigDecimal.ZERO;
        }

        if (v2 == null) {
            return BigDecimal.ZERO;
        }

        return v1.divide(v2, 16,BigDecimal.ROUND_HALF_UP);
    }




   public static Boolean compare(String leftVal, String rightVal, String operator) {

       if (leftVal == null || rightVal == null) {
           return false;
       }
       switch (operator) {
           case "=":
               if (leftVal.equals(rightVal)) {
                   return true;
               }
               break;
           case "!=":
               if (!leftVal.equals(rightVal)) {
                   return true;
               }
               break;
           case ">":
               if (new BigDecimal(leftVal).compareTo(new BigDecimal(rightVal)) == 1) {
                   return true;
               }
               break;
           case "<":
               if (new BigDecimal(leftVal).compareTo(new BigDecimal(rightVal)) == -1) {
                   return true;
               }
               break;
           case ">=":
               if (new BigDecimal(leftVal).compareTo(new BigDecimal(rightVal)) != -1) {
                   return true;
               }
               break;
           case "<=":
               if (new BigDecimal(leftVal).compareTo(new BigDecimal(rightVal)) != 1) {
                   return true;
               }
               break;
           case "in":
               if (rightVal.contains(leftVal)) {
                   return true;
               }
               break;
           case "like":
               if (leftVal.contains(rightVal)) {
                   return true;
               }
               break;
       }
       return false;
   }
}
