#!/usr/bin/env python3
import os, sys, re

MD_FILES = [
    "PSP_AD_DUAL.md"
]

PROJECT_DIR = os.path.dirname(os.path.abspath(__file__))

toc_entries = []
anchor_counter = [0]

def make_anchor(text):
    anchor_counter[0] += 1
    slug = re.sub(r'[^a-zA-Z0-9áéíóúñÁÉÍÓÚÑ]+', '-', text).strip('-').lower()
    return f"sec-{anchor_counter[0]}-{slug}"

def simple_md_to_html(md_text):
    lines = md_text.split('\n')
    html_lines = []
    in_code = False
    in_table = False
    in_list = False
    list_type = 'ul'
    
    for line in lines:
        if line.strip().startswith('```'):
            if in_code:
                html_lines.append('</code></pre>')
                in_code = False
            else:
                lang = line.strip().replace('```', '').strip()
                html_lines.append(f'<pre><code class="{lang}">')
                in_code = True
            continue
        if in_code:
            html_lines.append(line.replace('<', '&lt;').replace('>', '&gt;'))
            continue
            
        if '|' in line and line.strip().startswith('|'):
            cells = [c.strip() for c in line.strip().split('|')[1:-1]]
            if all(set(c.strip()) <= set('-: ') for c in cells):
                continue
            if not in_table:
                html_lines.append('<table>')
                in_table = True
                html_lines.append('<thead><tr>')
                for cell in cells:
                    html_lines.append(f'<th>{process_inline(cell)}</th>')
                html_lines.append('</tr></thead><tbody>')
                continue
            else:
                html_lines.append('<tr>')
                for cell in cells:
                    html_lines.append(f'<td>{process_inline(cell)}</td>')
                html_lines.append('</tr>')
                continue
        else:
            if in_table:
                html_lines.append('</tbody></table>')
                in_table = False

        def close_list():
            nonlocal in_list
            if in_list:
                html_lines.append(f'</{list_type}>')
                in_list = False

        if line.startswith('# '):
            close_list()
            text = line[2:].strip()
            aid = make_anchor(text)
            toc_entries.append((1, text, aid))
            html_lines.append(f'<h1 id="{aid}">{process_inline(text)}</h1>')
        elif line.startswith('## '):
            close_list()
            text = line[3:].strip()
            aid = make_anchor(text)
            toc_entries.append((2, text, aid))
            html_lines.append(f'<h2 id="{aid}">{process_inline(text)}</h2>')
        elif line.startswith('### '):
            close_list()
            text = line[4:].strip()
            aid = make_anchor(text)
            toc_entries.append((3, text, aid))
            html_lines.append(f'<h3 id="{aid}">{process_inline(text)}</h3>')
        elif line.startswith('#### '):
            close_list()
            text = line[5:].strip()
            html_lines.append(f'<h4>{process_inline(text)}</h4>')
        elif line.strip().startswith('- ') or line.strip().startswith('* '):
            content = line.strip()[2:]
            if not in_list:
                list_type = 'ul'
                html_lines.append('<ul>')
                in_list = True
            html_lines.append(f'<li>{process_inline(content)}</li>')
        elif re.match(r'\s*\d+\.\s+', line):
            content = re.sub(r'\s*\d+\.\s+', '', line, count=1)
            if not in_list:
                list_type = 'ol'
                html_lines.append('<ol>')
                in_list = True
            html_lines.append(f'<li>{process_inline(content)}</li>')
        else:
            close_list()
            if line.strip():
                html_lines.append(f'<p>{process_inline(line)}</p>')
    
    if in_table:
        html_lines.append('</tbody></table>')
    if in_list:
        html_lines.append(f'</{list_type}>')
    
    return '\n'.join(html_lines)

def process_inline(text):
    text = re.sub(r'\[([^\]]+)\]\(([^)]+)\)', r'<a href="\2">\1</a>', text)
    text = re.sub(r'\*\*([^*]+)\*\*', r'<strong>\1</strong>', text)
    text = re.sub(r'\*([^*]+)\*', r'<em>\1</em>', text)
    text = re.sub(r'`([^`]+)`', r'<code class="inline">\1</code>', text)
    return text

def build_toc_html():
    lines = []
    lines.append('<div class="toc-page">')
    lines.append('<h1 class="toc-title">Índice</h1>')
    lines.append('<div class="toc">')
    for level, text, aid in toc_entries:
        css_class = f"toc-level-{level}"
        lines.append(f'<div class="{css_class}"><a href="#{aid}">{text}</a></div>')
    lines.append('</div>')
    lines.append('</div>')
    return '\n'.join(lines)

def generate_html():
    sections = []
    for md_file in MD_FILES:
        filepath = os.path.join(PROJECT_DIR, md_file)
        if os.path.exists(filepath):
            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()
            sections.append(simple_md_to_html(content))
    
    toc_html = build_toc_html()
    content_html = '<div class="section-divider"></div>'.join(sections)
    
    html = f"""<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>PSP Actividad DUAL - API Productos</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap');
        * {{ margin: 0; padding: 0; box-sizing: border-box; }}
        body {{
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
            line-height: 1.7; color: #1a1a2e; background: #ffffff;
            padding: 40px 60px; max-width: 900px; margin: 0 auto;
        }}
        .cover {{
            page-break-after: always; display: flex; flex-direction: column;
            justify-content: center; align-items: center; min-height: 90vh;
            text-align: center; border: 3px solid #2d3436; border-radius: 12px;
            padding: 60px; background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        }}
        .cover h1 {{ font-size: 2.8em; font-weight: 700; color: #2d3436; margin-bottom: 20px; border: none; padding: 0; }}
        .cover .subtitle {{ font-size: 1.4em; color: #636e72; margin-bottom: 40px; }}
        .cover .info {{ font-size: 1.1em; color: #636e72; line-height: 2; }}
        .cover .info strong {{ color: #2d3436; }}
        .toc-page {{ page-break-after: always; padding-top: 30px; }}
        .toc-title {{ font-size: 2em; font-weight: 700; color: #2d3436; border-bottom: 3px solid #0984e3; padding-bottom: 10px; margin-bottom: 25px; }}
        .toc {{ padding: 0; }}
        .toc a {{ color: #2d3436; text-decoration: none; display: block; padding: 6px 0; border-bottom: 1px dotted #dfe6e9; transition: color 0.2s; }}
        .toc a:hover {{ color: #0984e3; }}
        .toc-level-1 {{ font-size: 1.15em; font-weight: 700; margin-top: 14px; }}
        .toc-level-1 a {{ border-bottom: 1px solid #b2bec3; padding: 8px 0; }}
        .toc-level-2 {{ font-size: 1em; font-weight: 600; padding-left: 22px; color: #0984e3; }}
        .toc-level-2 a {{ color: #0984e3; }}
        .toc-level-3 {{ font-size: 0.92em; font-weight: 400; padding-left: 44px; color: #636e72; }}
        .toc-level-3 a {{ color: #636e72; }}
        .section-divider {{ page-break-before: always; margin-top: 40px; }}
        h1 {{ font-size: 1.9em; font-weight: 700; color: #2d3436; padding-bottom: 10px; border-bottom: 3px solid #0984e3; margin: 30px 0 20px 0; }}
        h2 {{ font-size: 1.4em; font-weight: 600; color: #0984e3; margin: 25px 0 12px 0; }}
        h3 {{ font-size: 1.15em; font-weight: 600; color: #2d3436; margin: 20px 0 10px 0; }}
        h4 {{ font-size: 1em; font-weight: 600; color: #636e72; margin: 15px 0 8px 0; }}
        p {{ margin: 8px 0; text-align: justify; }}
        ul, ol {{ margin: 10px 0 10px 25px; }}
        li {{ margin: 5px 0; }}
        table {{ width: 100%; border-collapse: collapse; margin: 15px 0; font-size: 0.9em; }}
        th {{ background: #0984e3; color: white; padding: 10px 12px; text-align: left; font-weight: 600; }}
        td {{ padding: 8px 12px; border-bottom: 1px solid #dfe6e9; }}
        tr:nth-child(even) td {{ background: #f8f9fa; }}
        pre {{ background: #2d3436; color: #dfe6e9; padding: 15px 20px; border-radius: 8px; overflow-x: auto; margin: 12px 0; font-size: 0.85em; line-height: 1.5; }}
        code.inline {{ background: #f1f3f5; color: #e74c3c; padding: 2px 6px; border-radius: 4px; font-size: 0.9em; }}
        pre code {{ background: none; color: inherit; padding: 0; }}
        a {{ color: #0984e3; text-decoration: none; }}
        strong {{ font-weight: 600; }}
        @media print {{
            body {{ padding: 20px 40px; }}
            .cover {{ border: 2px solid #2d3436; }}
            pre {{ white-space: pre-wrap; word-wrap: break-word; }}
        }}
        @page {{ margin: 2cm; size: A4; }}
    </style>
</head>
<body>
    <div class="cover">
        <h1>PSP Actividad DUAL</h1>
        <div class="subtitle">Desarrollo de Servicio REST Seguro</div>
        <div class="info">
            <p><strong>Módulo:</strong> Programación de Servicios y Procesos</p>
            <p><strong>Ciclo:</strong> 2º DAM</p>
            <p><strong>Curso:</strong> 2025/2026</p>
        </div>
    </div>
    {toc_html}
    {content_html}
</body>
</html>"""
    return html

if __name__ == '__main__':
    html = generate_html()
    output_path = os.path.join(PROJECT_DIR, "PSP_Actividad_DUAL.html")
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(html)
    print(f"HTML generado: {output_path}")
