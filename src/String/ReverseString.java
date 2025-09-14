package String;

public class ReverseString {

    public static void main(String[] args) {
        String s = "Hi! This is a beautiful day!";
        String[] words = s.split(" "); // [Hi!, This, is, a, beautiful, day]

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            String punctuation = "";

            StringBuilder reversed = new StringBuilder();



                int lastCharacterIndex = word.length()-1;
                char lastCharacter = word.charAt(lastCharacterIndex);
                if(!Character.isLetterOrDigit(lastCharacter)) {
                    punctuation = String.valueOf(lastCharacter);
                    word = word.substring(0, word.length()-1);
                }

            for (int j = word.length()-1; j >= 0; j--) {
                reversed.append(words[i].charAt(j));
            }
            reversed.append(punctuation);
            result.append(reversed);

            if (i < words.length - 1) {
                result.append(" ");
            }
        }

        System.out.println("result" + result.toString());

    }
}
//        StringBuilder result = new StringBuilder();
//
//        for(int i = 0 ; i< tokens.length; i++) {
//            StringBuilder reversed = new StringBuilder();
//
//            for(int j = tokens[i].length()-1; j>=0; j--) {
//                    reversed.append(tokens[i].charAt(j));
//            }
//            result.append(reversed);
//            if(i<tokens.length-1) {
//                result.append(" ");
//            }
//        }
//        System.out.println("result" + result);
//    }
//}
