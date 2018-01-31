import java.util.Scanner;

public class MainClass {
    public static void main(String[] Args) {
        Scanner reader = new Scanner(System.in);
        String output = "";
        System.out.print("Input: \n");
        int testCases = Integer.parseInt(reader.nextLine());
        if (testCases >= 1 && testCases <= 10)
        {
            for (int i = 0; i < testCases; i++)
            {
                String passPhrase = reader.nextLine().toLowerCase();
                int passwordLength = Integer.parseInt(reader.nextLine());
                String password = reader.nextLine();
                if(passPhrase.charAt(passPhrase.length() - 1) == '.')
                {
                    String[] segments = Segments.ConvertPhraseToSegments(passPhrase);
                    if(segments.length >= 1 && segments.length <= 100)
                    {
                        String result = Segments.CompareSegmentsAndFindResult(segments, password, passwordLength);
                        output += "Case " + (i+1) + ": " + result + "\n";
                    }

                }
            }
        }
        reader.close();
        System.out.print(output);
    }
}
