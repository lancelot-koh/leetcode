package collections;

public class MathDemo {
    public static void main(String[] args) {
        // Basic arithmetic operations
        int a = 10;
        int b = 5;
        System.out.println("Addition: " + (a + b));
        System.out.println("Subtraction: " + (a - b));
        System.out.println("Multiplication: " + (a * b));
        System.out.println("Division: " + (a / b));

        // Using Math class
        double x = 3.7;
        System.out.println("Ceiling of x: " + Math.ceil(x));
        System.out.println("Floor of x: " + Math.floor(x));
        System.out.println("Round of x: " + Math.round(x));

        // Power and square root
        System.out.println("2 raised to the power of 3: " + Math.pow(
            2, 3));
        System.out.println("Square root of 16: " + Math.sqrt(16));
        // Absolute value
        System.out.println("Absolute value of -5: " + Math.abs(-5));
        // Trigonometric functions
        double angle = Math.toRadians(30); // Convert degrees to radians
        System.out.println("Sine of 30 degrees: " + Math.sin(angle));
        System.out.println("Cosine of 30 degrees: " + Math.cos(angle));
        System.out.println("Tangent of 30 degrees: " + Math.tan(angle));
            
        // Random number generation
        System.out.println("Random number between 0 and 1: " + Math.random());
        System.out.println("Random integer between 0 and 100: " + (int)(
            Math.random() * 101));


        //max and min
        System.out.println("Max of 10 and 5: " + Math.max(a, b));
        System.out.println("Min of 10 and 5: " + Math.min(a, b));

        // Logarithmic functions
        System.out.println("Natural logarithm of 10: " + Math.log(10));
        System.out.println("Base 10 logarithm of 100: " + Math.log10(100));

        //abs
        System.out.println("Absolute value of -10: " + Math.abs(-10));
        // sqprt
        System.out.println("Square root of 25: " + Math.sqrt(25));
        //pow
        System.out.println("3 raised to the power of 4: " + Math.pow(3, 4));    

        //floor and ceil
        double num = 5.3;
        System.out.println("Floor of " + num + ": " + Math.floor(num));
        System.out.println("Ceiling of " + num + ": " + Math.ceil(num));

        // Infinite and NaN
        System.out.println("Positive infinity: " + Double.POSITIVE_INFINITY);
        System.out.println("Negative infinity: " + Double.NEGATIVE_INFINITY);
        System.out.println("NaN (Not a Number): " + Double.NaN);

        //Integer overflow
        int maxInt = Integer.MAX_VALUE;
        System.out.println("Max integer: " + maxInt);
        System.out.println("Max integer + 1 (overflow): " + (maxInt + 1));  

        // max and min value of Integer
        System.out.println("Max integer: " + Integer.MAX_VALUE);
        System.out.println("Min integer: " + Integer.MIN_VALUE);
        // max and min value of Double
        System.out.println("Max double: " + Double.MAX_VALUE);
        System.out.println("Min double: " + Double.MIN_VALUE);

        //conversion between integer and string
        int num1 = 123;
        String strNum = Integer.toString(num1);
        System.out.println("String representation of num1: " + strNum);
        int num2 = Integer.parseInt(strNum);
        System.out.println("Parsed integer from string: " + num2);
        //conversion between double and string
        double num3 = 3.14;
        String strNum3 = Double.toString(num3);
        System.out.println("String representation of num3: " + strNum3);
        double num4 = Double.parseDouble(strNum3);
        System.out.println("Parsed double from string: " + num4);


        //bitwise operations
        int x1 = 5; // 0101 in binary
        int y1 = 3; // 0011 in binary
        System.out.println("Bitwise AND: " + (x1 & y1)); // 0001 in binary  
        System.out.println("Bitwise OR: " + (x1 | y1)); // 0111 in binary
        System.out.println("Bitwise XOR: " + (x1 ^ y1)); //

            //bit tricks
        int n = 16; // 10000 in binary
        System.out.println("Is n a power of 2? " + ((n & (n - 1)) == 0)); // true for powers of 2
        int m = 5; // 0101 in binary
        System.out.println("Is m odd? " + ((m & 1) == 1)); // true for odd numbers  

        

    }
}
