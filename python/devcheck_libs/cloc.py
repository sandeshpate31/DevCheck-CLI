"""CLOC-like educational source scanner."""
from pathlib import Path
LANG={".py":"Python",".java":"Java",".js":"JavaScript",".jsx":"JavaScript",".ts":"TypeScript",".tsx":"TypeScript",".html":"HTML",".css":"CSS",".c":"C",".h":"C/C++",".cpp":"C++",".cs":"C#",".go":"Go",".rb":"Ruby",".php":"PHP",".sh":"Shell",".sql":"SQL",".md":"Markdown"}
SKIP={".git",".venv","venv","node_modules","__pycache__","build","dist"}
def scan(root="."):
    root=Path(root)
    if not root.exists(): raise FileNotFoundError(root)
    totals={}
    for p in root.rglob("*"):
        if not p.is_file() or any(part in SKIP for part in p.parts): continue
        lang=LANG.get(p.suffix.lower())
        if not lang: continue
        lines=p.read_text(encoding="utf-8",errors="ignore").splitlines()
        blank=comment=code=0; block=False
        for line in lines:
            t=line.strip()
            if not t: blank+=1; continue
            is_comment=False
            if lang=="Python" or lang=="Shell" or lang=="Ruby": is_comment=t.startswith("#")
            elif lang in ("Java","JavaScript","TypeScript","C","C++","C#","Go"):
                if block:
                    is_comment=True
                    if "*/" in t: block=False
                elif t.startswith("//") or t.startswith("/*"):
                    is_comment=True
                    if t.startswith("/*") and "*/" not in t: block=True
            elif lang=="HTML": is_comment=t.startswith("<!--")
            elif lang=="CSS": is_comment=t.startswith("/*")
            elif lang=="SQL": is_comment=t.startswith("--")
            elif lang=="Markdown": is_comment=t.startswith("<!--")
            if is_comment: comment+=1
            else: code+=1
        v=totals.setdefault(lang,{"files":0,"blank":0,"comment":0,"code":0})
        v["files"]+=1; v["blank"]+=blank; v["comment"]+=comment; v["code"]+=code
    return totals
def format_report(root="."):
    data=scan(root)
    print("Language        files    blank  comment    code")
    print("------------------------------------------------")
    for lang,v in sorted(data.items()):
        print(f"{lang:<14} {v['files']:>5} {v['blank']:>8} {v['comment']:>8} {v['code']:>8}")
    print("------------------------------------------------")
    print(f"SUM: {sum(v['files'] for v in data.values())} files, {sum(v['code'] for v in data.values())} code, {sum(v['comment'] for v in data.values())} comment, {sum(v['blank'] for v in data.values())} blank")
