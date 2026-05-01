package collections;
public class StringDemo {
    public static void main(String[] args) {
        // Create a string
        String str = "Hello, World!";
        System.out.println("Original string: " + str);

        // String length
        System.out.println("String length: " + str.length());

        // Accessing characters
        System.out.println("First character: " + str.charAt(0));
        System.out.println("Last character: " + str.charAt(str.length() - 1));

        // Substring
        String substring = str.substring(7, 12);
        System.out.println("Substring (7, 12): " + substring);

        // Concatenation
        String greeting = "Hello";
        String name = "Alice";
        String message = greeting + ", " + name + "!";
        System.out.println("Concatenated string: " + message);

        // String comparison
        String str1 = "Hello";
        String str2 = "hello";
        System.out.println("str1 equals str2: " + str1.equals(str2));
        System.out.println("str1 equalsIgnoreCase str2: " + str1.equalsIgnoreCase(str2));

        // String to uppercase and lowercase
        System.out.println("Uppercase: " + str.toUpperCase());
        System.out.println("Lowercase: " + str.toLowerCase());

        // Trimming whitespace
        String paddedStr = "   Hello, World!   ";
        System.out.println("Trimmed string: '" + paddedStr.trim() + "'");
    }
}
