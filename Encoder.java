import java.util.HashMap;
import java.util.Map;

public class Encoder {
    private HashMap<Character, Integer> refTable;
    private char offset;

    public Encoder(HashMap<Character, Integer> referenceTable, char offsetCharacter) {
        this.refTable = referenceTable;
        this.offset = offsetCharacter;
    }

    public String encode(String plainText) {
        String encodedString = String.valueOf(offset);
        Integer offsetVal = refTable.get(offset); 
        if (offsetVal == null) {
            throw new IllegalArgumentException("Choose a valid offset characted based on the reference table");
        }
    
        for (char letter : plainText.toCharArray()) {
            if (!refTable.containsKey(letter)) {
                encodedString += letter;
            } else {
                int originalValue = refTable.get(letter);
                int newValue = (originalValue - offsetVal + refTable.size()) % refTable.size();
                char newLetter = getKeyByValue(refTable, newValue);
                encodedString += newLetter;
            }
        }
    
        return encodedString;
    }
    
    public String decode(String encodedText) {
        String plainString = "";
        Integer offsetVal = refTable.get(offset); // Use Integer instead of int to handle null values
        if (offsetVal == null) {
            throw new IllegalArgumentException("Choose a valid offset characted based on the reference table");
        }
    
        for (char letter : encodedText.toCharArray()) {
            if (!refTable.containsKey(letter)) {
                plainString += letter;
            } else {
                int originalValue = refTable.get(letter);
                int newValue = (originalValue + offsetVal) % refTable.size();
                char newLetter = getKeyByValue(refTable, newValue);
                plainString += newLetter;
            }
        }
    
        return plainString;
    }
    

    private char getKeyByValue(HashMap<Character, Integer> map, int value) {
        
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        // If value not found, return a default character or handle error as appropriate
        System.out.println("Value: " + value);
        System.out.println("Character not found");
        return ' ';
    }

    public static void main(String[] args) {
        HashMap<Character, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < 26; i++) {
            char letter = (char) ('A' + i);
            hashMap.put(letter, i);
        }

        for (int i = 26; i < 36; i++) {
            char digit = (char) ('0' + (i - 26));
            hashMap.put(digit, i);
        }

        hashMap.put('(', 36);
        hashMap.put(')', 37);
        hashMap.put('*', 38);
        hashMap.put('+', 39);
        hashMap.put(',', 40);
        hashMap.put('-', 41);
        hashMap.put('.', 42);
        hashMap.put('/', 43);

        // CHANGE VARIABLES HERE FOR TESTING
        char offset = 'E'; 
        String plainText = "HELLO WORLD";
        String encodedText = "GDKKN VNQKC";

        System.out.println("Initial Plain Text: " + plainText);
        System.out.println("Initial Encoded Text: " + encodedText);

        // Create Encoder instance with the reference table and offset character
        Encoder encoder = new Encoder(hashMap, offset);

        System.out.println();

        // Use encoder object for encoding or decoding
        System.out.println("Encoded Text (After Encoding Function): " + encoder.encode(plainText));
        System.out.println("Plain Text (After Decoding Function): " + encoder.decode(encodedText));
        

    }
}
