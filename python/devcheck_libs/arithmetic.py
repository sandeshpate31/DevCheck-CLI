"""Small educational arithmetic/statistics library; standard library only."""
from statistics import mean, median


def add(a: float, b: float) -> float: return a + b
def subtract(a: float, b: float) -> float: return a - b
def multiply(a: float, b: float) -> float: return a * b
def divide(a: float, b: float) -> float:
    if b == 0: raise ZeroDivisionError("cannot divide by zero")
    return a / b

def power(a: float, b: float) -> float: return a ** b

def stats(values: list[float]) -> dict[str, float]:
    if not values: raise ValueError("at least one number is required")
    return {"count": len(values), "mean": mean(values), "median": median(values), "min": min(values), "max": max(values), "sum": sum(values)}

def modulus(a: float, b: float) -> float:
    if b == 0: raise ZeroDivisionError("modulus by zero")
    return a % b

def factorial(n: int) -> int:
    import math
    if n < 0: raise ValueError("factorial requires n >= 0")
    return math.factorial(n)

def integrate_trapezoid(fn, a: float, b: float, steps: int = 1000) -> float:
    """Numerically integrate fn(x) on [a,b] using trapezoid rule."""
    if steps < 1: raise ValueError("steps must be >= 1")
    h = (b-a)/steps
    total = (fn(a)+fn(b))/2
    for i in range(1, steps): total += fn(a+i*h)
    return total*h

def vector_add(a, b):
    if len(a) != len(b): raise ValueError("vectors must have equal dimensions")
    return [x+y for x,y in zip(a,b)]

def vector_dot(a, b):
    if len(a) != len(b): raise ValueError("vectors must have equal dimensions")
    return sum(x*y for x,y in zip(a,b))

def linear_regression_predict(xs, ys, x):
    """Educational simple least-squares ML helper."""
    if len(xs) != len(ys) or len(xs) < 2: raise ValueError("need >=2 paired samples")
    mx, my = mean(xs), mean(ys)
    den = sum((v-mx)**2 for v in xs)
    if den == 0: raise ValueError("x values must not all be equal")
    slope = sum((a-mx)*(b-my) for a,b in zip(xs,ys))/den
    return my + slope*(x-mx)
