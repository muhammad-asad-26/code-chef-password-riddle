public class Segments {

    //Private Members
    private static final String[] NUMBER_SPELLINGS = {
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
    };
    private static int[] oddNumbers = {1, 3, 5, 7, 9};
    private static int[] evenNumbers = {0, 2, 4, 6, 8};

    //Static Methods
    public static String[] ConvertPhraseToSegments(final String phrase) {
        String phrase_local = phrase.substring(0, phrase.length() -1);
        return phrase_local.split(",");
    }

    public static String CompareSegmentsAndFindResult(final String[] segments, final String password, final int passwordLength) {
        String password_passed = password;
        int passwordLength_passed = passwordLength;
        if (EvaluateSegments(segments, password_passed, passwordLength_passed))
            return "Not";

        return "May be";
    }

    private static boolean EvaluateSegments(String[] segments, String password_passed, int passwordLength_passed) {
        for (int index = segments.length -1; index >= 0; index--) {
            String[] literals = segments[index].trim().split(" ");
            if(literals.length >= 1 && literals.length <= 600000) {
                //Evaluate the length of the chunk to compare
                int length = evaluateLengthFromLiterals(password_passed, passwordLength_passed, literals);

                //Get the chunk for the password for comparison on the basis of literals
                String enteredPasswordChunkToCompare = password_passed.substring(password_passed.length() - length);

                //Removing the used chunk from the original password
                password_passed = password_passed.substring(0, password_passed.length() - length);
                passwordLength_passed -= length;

                //Check if the last literal is odd or even. Then get the number from the entered password
                // and convert it to number word.
                if (literals[literals.length - 1].equals("odd") || literals[literals.length - 1].equals("even")) {
                    literals[literals.length - 1] = Segments.ConvertToSpelling(Character.getNumericValue(enteredPasswordChunkToCompare.charAt(0)));
                }

                // Generate a temporary password to compare with the original password chunk
                String generateTempPassword = generatePassword(Segments.ConvertToDigit(literals[literals.length - 1]), length);

                if (!enteredPasswordChunkToCompare.equals(generateTempPassword)) {
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    private static String generatePassword(int digit, int length) {
        StringBuilder generateTempPassword = new StringBuilder();
        int x = 0;
        while(x < length) {
            generateTempPassword.append(digit);
            x++;
        }
        return generateTempPassword.toString();
    }

    private static int evaluateLengthFromLiterals(String password_passed, int passwordLength_passed, String[] literals) {
        int length = Segments.ConvertToDigit(literals[0]);
        for(int i = 1;i < literals.length - 1 ;i++) {
            switch (literals[i]) {
                case "odd":
                    length = evaluateEvenOrOddLength(password_passed, passwordLength_passed, length, oddNumbers);
                    break;
                case "even":
                    length = evaluateEvenOrOddLength(password_passed, passwordLength_passed, length, evenNumbers);
                    break;
                default:
                    length *= Segments.ConvertToDigit(literals[i]);
                    break;
            }
        }
        return length;
    }

    private static int evaluateEvenOrOddLength(String password_passed, int passwordLength_passed, int length, final int[] arrayPassed) {
        for(int index = arrayPassed.length - 1; index >= 0; index-- ) {
            int chunkLength = length * arrayPassed[index];
            if(chunkLength <= passwordLength_passed) {
                String password_chunk = password_passed.substring(password_passed.length() - chunkLength);
                if (password_chunk.charAt(0) == password_chunk.charAt(password_chunk.length() - 1)) {
                    length *= arrayPassed[index];
                    break;
                }
            }
        }
        return length;
    }

    private static int ConvertToDigit(final String number) {
        for (int i = 0; i < NUMBER_SPELLINGS.length; i++) {
            if (NUMBER_SPELLINGS[i].equals(number)) {
                return i;
            }
        }
        return 0;
    }

    private static String ConvertToSpelling(final int number)
    {
        return NUMBER_SPELLINGS[number];
    }
}
