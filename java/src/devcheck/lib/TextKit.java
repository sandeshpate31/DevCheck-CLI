package devcheck.lib;

/** Reusable text helpers used by the Java CLI. */
public final class TextKit {
    private TextKit() {}
    public static String reverse(String value) { return new StringBuilder(value).reverse().toString(); }
    public static boolean isPalindrome(String value) {
        String clean = value.toLowerCase().replaceAll("[^a-z0-9]", "");
        return clean.equals(new StringBuilder(clean).reverse().toString());
    }
    public static int wordCount(String value) {
        String clean = value.trim(); return clean.isEmpty() ? 0 : clean.split("\\s+").length;
    }
}
