import sys, unittest
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from devcheck_libs import arithmetic, textkit

class ArithmeticTests(unittest.TestCase):
    def test_add(self): self.assertEqual(arithmetic.add(2, 3), 5)
    def test_divide_by_zero(self):
        with self.assertRaises(ZeroDivisionError): arithmetic.divide(2, 0)
    def test_stats(self): self.assertEqual(arithmetic.stats([1, 2, 3])["mean"], 2)

class TextTests(unittest.TestCase):
    def test_reverse(self): self.assertEqual(textkit.reverse("hello"), "olleh")
    def test_palindrome(self): self.assertTrue(textkit.is_palindrome("Never odd or even"))

if __name__ == "__main__": unittest.main()
