package devcheck;

import devcheck.lib.MathKit;
import devcheck.lib.TextKit;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;

/** DevCheck Java command group: 10 commands. */
public class Main {
    private static void help() {
        System.out.println("DevCheck Java commands (10): greet, calc, reverse, palindrome, count, upper, lower, stats, fileinfo, help, mathx, cloc");
        System.out.println("Examples: java greet Sandesh | calc add 2 3 | stats 2 5 8 | fileinfo README.md");
    }
    private static String join(String[] a, int from) { return String.join(" ", Arrays.copyOfRange(a, from, a.length)); }
    public static void main(String[] args) {
        try {
            if (args.length == 0 || args[0].equals("help")) { help(); return; }
            String cmd = args[0].toLowerCase(Locale.ROOT);
            switch (cmd) {
                case "mathx": {
                    if(args.length<2) throw new IllegalArgumentException("usage: mathx <mod|factorial|integrate-x2|vector-add|vector-dot|ml-predict> values");
                    String op=args[1]; double r;
                    if(op.equals("mod")) {r=MathKit.modulus(Double.parseDouble(args[2]),Double.parseDouble(args[3]));System.out.println(r);}
                    else if(op.equals("factorial")) System.out.println(MathKit.factorial(Integer.parseInt(args[2])));
                    else if(op.equals("integrate-x2")) System.out.println(MathKit.integrateX2(Double.parseDouble(args[2]),Double.parseDouble(args[3]),1000));
                    else if(op.equals("vector-add")||op.equals("vector-dot")) {
                        String[] aa=args[2].split(","),bb=args[3].split(",");double[] a=new double[aa.length],b=new double[bb.length];
                        for(int i=0;i<a.length;i++)a[i]=Double.parseDouble(aa[i]);for(int i=0;i<b.length;i++)b[i]=Double.parseDouble(bb[i]);
                        if(op.equals("vector-add"))System.out.println(Arrays.toString(MathKit.vectorAdd(a,b)));else System.out.println(MathKit.vectorDot(a,b));
                    } else if(op.equals("ml-predict")) {
                        String[] aa=args[2].split(","),bb=args[3].split(",");double[] a=new double[aa.length],b=new double[bb.length];
                        for(int i=0;i<a.length;i++)a[i]=Double.parseDouble(aa[i]);for(int i=0;i<b.length;i++)b[i]=Double.parseDouble(bb[i]);
                        System.out.println(MathKit.linearRegressionPredict(a,b,Double.parseDouble(args[4])));
                    } else throw new IllegalArgumentException("unknown mathx operation");
                    break;
                }
                case "greet":
                    if (args.length < 2) throw new IllegalArgumentException("usage: greet <name>");
                    System.out.println("Hello, " + join(args, 1) + "!"); break;
                case "calc": {
                    if (args.length != 4) throw new IllegalArgumentException("usage: calc <add|subtract|multiply|divide> <a> <b>");
                    double a = Double.parseDouble(args[2]), b = Double.parseDouble(args[3]);
                    double result;
                    switch (args[1]) {
                        case "add": result = MathKit.add(a,b); break;
                        case "subtract": result = MathKit.subtract(a,b); break;
                        case "multiply": result = MathKit.multiply(a,b); break;
                        case "divide": result = MathKit.divide(a,b); break;
                        default: throw new IllegalArgumentException("unknown operation: " + args[1]);
                    }
                    System.out.println(result); break;
                }
                case "reverse": if (args.length < 2) throw new IllegalArgumentException("usage: reverse <text>"); else System.out.println(TextKit.reverse(join(args,1))); break;
                case "palindrome": if (args.length < 2) throw new IllegalArgumentException("usage: palindrome <text>"); else System.out.println(TextKit.isPalindrome(join(args,1)) ? "Yes" : "No"); break;
                case "count": if (args.length < 2) throw new IllegalArgumentException("usage: count <text>"); else { String s=join(args,1); System.out.println("characters: " + s.length()); System.out.println("words: " + TextKit.wordCount(s)); } break;
                case "upper": if (args.length < 2) throw new IllegalArgumentException("usage: upper <text>"); else System.out.println(join(args,1).toUpperCase(Locale.ROOT)); break;
                case "lower": if (args.length < 2) throw new IllegalArgumentException("usage: lower <text>"); else System.out.println(join(args,1).toLowerCase(Locale.ROOT)); break;
                case "stats": {
                    if (args.length < 2) throw new IllegalArgumentException("usage: stats <number> [number ...]");
                    double[] v = new double[args.length-1]; for (int i=1;i<args.length;i++) v[i-1]=Double.parseDouble(args[i]);
                    System.out.println("count: " + v.length); System.out.println("mean: " + MathKit.mean(v)); System.out.println("min: " + MathKit.min(v)); System.out.println("max: " + MathKit.max(v)); break;
                }
                case "fileinfo": {
                    if (args.length != 2) throw new IllegalArgumentException("usage: fileinfo <path>");
                    Path p=Path.of(args[1]); if (!Files.isRegularFile(p)) throw new IllegalArgumentException("not a file: " + p);
                    long bytes=Files.size(p); long lines; try (var stream=Files.lines(p)) { lines=stream.count(); }
                    System.out.println("path: " + p); System.out.println("bytes: " + bytes); System.out.println("lines: " + lines); break;
                }
                default: throw new IllegalArgumentException("unknown command: " + cmd + " (run java help)");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage()); System.exit(1);
        }
    }
}
