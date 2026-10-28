"""Reusable text operations."""
import re

def reverse(text: str) -> str: return text[::-1]
def is_palindrome(text: str) -> bool:
    normalized = re.sub(r"[^a-z0-9]", "", text.lower())
    return normalized == normalized[::-1]
def counts(text: str) -> dict[str, int]:
    return {"characters": len(text), "characters_no_spaces": len(text.replace(" ", "")), "words": len(text.split())}
def title_words(text: str) -> str: return text.title()
