
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
        if (str.length() > 1) {
            return str.substring(1);
        }
        return "";
	}

    public static char head(String str) {
        if (str.length() > 0) {
            return str.charAt(0);
        }
        return '\0';
    }

	public static int levenshtein(String word1, String word2) {

        // case insensitive
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();

        int word1Length = word1.length();
        int word2Length = word2.length();

        if (word1Length == 0 || word2Length == 0) {
            return Math.max(word1Length, word2Length);
        }

        // case first letter is the same;
        if (head(word1) == head(word2)) {
            return levenshtein(tail(word1), tail(word2));
        }

        return Math.min(Math.min(levenshtein(tail(word1), word2), levenshtein(word1, tail(word2))), levenshtein(tail(word1), tail(word2))) + 1;
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

        dictionary = in.readAllLines();
		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
        String mostCloseWord = word;
        int mostCloseDistance = threshold + 1;

        for (int i = 0; i < dictionary.length; ++i) {
            int levenshteinDistance = levenshtein(word, dictionary[i]);
            if (levenshteinDistance < mostCloseDistance) {
                mostCloseWord = dictionary[i];
                mostCloseDistance = levenshteinDistance;
            }
        }
        return mostCloseWord;
	}

}
