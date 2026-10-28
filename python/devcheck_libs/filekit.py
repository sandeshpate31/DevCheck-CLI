"""Read-only file helpers. These functions do not modify files."""
from pathlib import Path
import hashlib

def file_info(path: str) -> dict[str, int | str]:
    p = Path(path)
    if not p.is_file(): raise FileNotFoundError(f"not a file: {path}")
    content = p.read_bytes()
    try: lines = len(content.decode("utf-8").splitlines())
    except UnicodeDecodeError: lines = 0
    return {"path": str(p), "bytes": len(content), "lines": lines}

def sha256(path: str) -> str:
    p = Path(path)
    if not p.is_file(): raise FileNotFoundError(f"not a file: {path}")
    return hashlib.sha256(p.read_bytes()).hexdigest()

def list_files(path: str, depth: int = 2) -> list[str]:
    root = Path(path)
    if not root.is_dir(): raise NotADirectoryError(f"not a directory: {path}")
    found = []
    for p in sorted(root.rglob("*")):
        if p.is_file() and len(p.relative_to(root).parts) <= depth:
            found.append(str(p))
    return found
