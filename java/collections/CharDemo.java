package collections;
public class CharDemo {
    public static void main(String[] args) {
        // Create a character
        char ch = 'A';
        System.out.println("Character: " + ch);

        // Character to uppercase and lowercase
        System.out.println("Uppercase: " + Character.toUpperCase(ch));
        System.out.println("Lowercase: " + Character.toLowerCase(ch));
        // Check if character is a letter, digit, or whitespace
        System.out.println("Is letter: " + Character.isLetter(ch));
        System.out.println("Is digit: " + Character.isDigit(ch));
        System.out.println("Is whitespace: " + Character.isWhitespace(ch));
        // Check if character is uppercase or lowercase
        System.out.println("Is uppercase: " + Character.isUpperCase(ch));
        System.out.println("Is lowercase: " + Character.isLowerCase(ch));   


        // Convert character to its ASCII value
        int asciiValue = (int) ch;
        System.out.println("ASCII value: " + asciiValue);
        // Convert ASCII value back to character
        char charFromAscii = (char) asciiValue;
        System.out.println("Character from ASCII value: " + charFromAscii);

        //coverting char to string
        String charAsString = Character.toString(ch);
        System.out.println("Character as string: " + charAsString); 

        //convert string to char
        String str = "Hello";
        char firstChar = str.charAt(0);
        System.out.println("First character of string: " + firstChar);  


        //convert char to int
        int charAsInt = ch;
        System.out.println("Character as int: " + charAsInt);       

        //convert int to char
        char intAsChar = (char) charAsInt;
        System.out.println("Int as character: " + intAsChar);

        //char operations
        char nextChar = (char) (ch + 1);
        System.out.println("Next character: " + nextChar);
        char prevChar = (char) (ch - 1);
        System.out.println("Previous character: " + prevChar);

        //char comparison
        char char1 = 'A';
        char char2 = 'B';
        System.out.println("char1 equals char2: " + (char1 == char2));
        System.out.println("char1 less than char2: " + (char1 < char2));

        //char to unicode
        int unicodeValue = ch;
        System.out.println("Unicode value: " + unicodeValue);
        //unicode to char
        char charFromUnicode = (char) unicodeValue;
        System.out.println("Character from Unicode value: " + charFromUnicode);

        //most common char operations in the algorithms
        char c1 = 'a';
        char c2 = 'b';
        System.out.println("Difference between c1 and c2: " + (c2 - c1));
        System.out.println("Is c1 a vowel: " + (c1 == 'a' || c1 == 'e' || c1 == 'i' || c1 == 'o' || c1 == 'u'));   
         

    }
}
