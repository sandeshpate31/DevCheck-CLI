"""DevCheck Python command group: 10 commands."""
import argparse, sys
from devcheck_libs import arithmetic, textkit, filekit, cloc


def build_parser():
    p = argparse.ArgumentParser(prog="devcheck py", description="DevCheck Python commands")
    sub = p.add_subparsers(dest="command", required=True)
    c = sub.add_parser("calc", help="arithmetic: add/subtract/multiply/divide/power")
    c.add_argument("operation", choices=["add", "subtract", "multiply", "divide", "power"]); c.add_argument("a", type=float); c.add_argument("b", type=float)
    s = sub.add_parser("stats", help="statistics for numbers"); s.add_argument("values", nargs="+", type=float)
    for name, help_text in [("reverse", "reverse text"), ("palindrome", "check palindrome"), ("count", "count characters and words"), ("upper", "uppercase text"), ("lower", "lowercase text")]:
        q = sub.add_parser(name, help=help_text); q.add_argument("text", nargs="+")
    f = sub.add_parser("fileinfo", help="show file size and line count"); f.add_argument("path")
    t = sub.add_parser("tree", help="list files up to depth two"); t.add_argument("path", nargs="?", default=".")
    h = sub.add_parser("hash", help="calculate a file's SHA-256 hash"); h.add_argument("path")
    cl = sub.add_parser("cloc", help="CLOC-like source code statistics"); cl.add_argument("path", nargs="?", default=".")
    m = sub.add_parser("mathx", help="advanced math and educational ML")
    m.add_argument("operation", choices=["mod","factorial","integrate-x2","vector-add","vector-dot","ml-predict"])
    m.add_argument("values", nargs="+")
    return p


def main(argv=None):
    parser = build_parser()
    args = parser.parse_args(argv)
    try:
        if args.command == "calc":
            fn = {"add": arithmetic.add, "subtract": arithmetic.subtract, "multiply": arithmetic.multiply, "divide": arithmetic.divide, "power": arithmetic.power}[args.operation]
            result = fn(args.a, args.b); print(int(result) if isinstance(result, float) and result.is_integer() else result)
        elif args.command == "stats":
            for k, v in arithmetic.stats(args.values).items(): print(f"{k}: {v}")
        elif args.command == "reverse": print(textkit.reverse(" ".join(args.text)))
        elif args.command == "palindrome":
            value = " ".join(args.text); print("Yes" if textkit.is_palindrome(value) else "No")
        elif args.command == "count":
            for k, v in textkit.counts(" ".join(args.text)).items(): print(f"{k}: {v}")
        elif args.command == "upper": print(" ".join(args.text).upper())
        elif args.command == "lower": print(" ".join(args.text).lower())
        elif args.command == "fileinfo":
            for k, v in filekit.file_info(args.path).items(): print(f"{k}: {v}")
        elif args.command == "tree":
            items = filekit.list_files(args.path)
            print("\n".join(items) if items else "(no files found)")
        elif args.command == "hash": print(filekit.sha256(args.path))
        elif args.command == "cloc": cloc.format_report(args.path)
        elif args.command == "mathx":
            v=args.values; op=args.operation
            if op=="mod": result=arithmetic.modulus(float(v[0]),float(v[1]))
            elif op=="factorial": result=arithmetic.factorial(int(v[0]))
            elif op=="integrate-x2": result=arithmetic.integrate_trapezoid(lambda x:x*x,float(v[0]),float(v[1]))
            elif op=="vector-add": result=arithmetic.vector_add([float(x) for x in v[0].split(",")],[float(x) for x in v[1].split(",")])
            elif op=="vector-dot": result=arithmetic.vector_dot([float(x) for x in v[0].split(",")],[float(x) for x in v[1].split(",")])
            else:
                xs=[float(x) for x in v[0].split(",")]; ys=[float(x) for x in v[1].split(",")]
                result=arithmetic.linear_regression_predict(xs,ys,float(v[2]))
            print(result)
        return 0
    except (ValueError, ZeroDivisionError, FileNotFoundError, NotADirectoryError) as exc:
        print(f"Error: {exc}", file=sys.stderr); return 1

if __name__ == "__main__":
    raise SystemExit(main())
