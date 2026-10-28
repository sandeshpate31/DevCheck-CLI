package devcheck;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

/**
 * Swing GUI: command console, HTML/CSS/JS playground, and Word-style editor.
 */
public final class DevCheckGui {
    public static void main(String[] a) {
        SwingUtilities.invokeLater(() -> new DevCheckGui().show());
    }

    void show() {
        JFrame f = new JFrame("DevCheck — Developer Studio");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1100, 760);
        f.setLocationRelativeTo(null);
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("CLI Console", console());
        tabs.addTab("Web Studio", web());
        tabs.addTab("Document Editor", document());
        f.setContentPane(tabs);
        f.setVisible(true);
    }

    JPanel console() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        JTextField cmd = new JTextField("help");
        JTextArea out = new JTextArea("Run Java commands. Examples: calc add 2 3 | stats 1 2 3 | cloc .");
        out.setEditable(false);
        JButton run = new JButton("Run"), help = new JButton("Help");
        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.add(cmd);
        JPanel b = new JPanel();
        b.add(help);
        b.add(run);
        top.add(b, BorderLayout.EAST);
        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(out));
        Runnable execute = () -> {
            try {
                String[] a = cmd.getText().trim().split("\\\\s+");
                java.util.List<String> x = new java.util.ArrayList<>();
                x.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");
                x.add("-cp");
                x.add(System.getProperty("java.class.path"));
                x.add("devcheck.Main");
                java.util.Collections.addAll(x, a);
                Process q = new ProcessBuilder(x).redirectErrorStream(true).start();
                out.setText(new String(q.getInputStream().readAllBytes()));
                q.waitFor();
            } catch (Exception e) {
                out.setText("Error: " + e.getMessage());
            }
        };
        run.addActionListener(e -> execute.run());
        cmd.addActionListener(e -> execute.run());
        help.addActionListener(e -> {
            cmd.setText("help");
            execute.run();
        });
        return p;
    }

    JPanel web() {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        JTabbedPane editors = new JTabbedPane();
        JTextArea html = new JTextArea(
                "<!doctype html>\\n<html><body><h1>Hello DevCheck!</h1><p>Edit HTML, CSS and JavaScript.</p><button>Click</button></body></html>");
        JTextArea css = new JTextArea(
                "body { font-family: Arial; padding: 24px; color: #243; } h1 { color: #1677d2; }");
        JTextArea js = new JTextArea("function sayHello(){ alert('Hello from JavaScript!'); }");
        editors.addTab("HTML", new JScrollPane(html));
        editors.addTab("CSS", new JScrollPane(css));
        editors.addTab("JavaScript", new JScrollPane(js));
        JEditorPane preview = new JEditorPane();
        preview.setContentType("text/html");
        preview.setEditable(false);
        JButton refresh = new JButton("Refresh Preview"), save = new JButton("Save HTML");
        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Website Preview (HTML/CSS)"), BorderLayout.NORTH);
        right.add(new JScrollPane(preview));
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editors, right);
        split.setResizeWeight(.55);
        p.add(split);
        JPanel tools = new JPanel();
        tools.add(refresh);
        tools.add(save);
        p.add(tools, BorderLayout.SOUTH);
        refresh.addActionListener(e -> preview.setText("<html><head><style>" + css.getText() + "</style></head><body>"
                + html.getText() + "<script>" + js.getText() + "</script></body></html>"));
        save.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("index.html"));
            if (fc.showSaveDialog(p) == JFileChooser.APPROVE_OPTION)
                try {
                    Files.writeString(fc.getSelectedFile().toPath(), "<style>" + css.getText() + "</style>"
                            + html.getText() + "<script>" + js.getText() + "</script>");
                } catch (Exception ex) {
                    error(p, ex);
                }
        });
        refresh.doClick();
        return p;
    }

    JPanel document() {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        JTextPane editor = new JTextPane();
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        JButton bold = new JButton("B"), italic = new JButton("I"), heading = new JButton("Heading"),
                bullet = new JButton("• List"), left = new JButton("Left"), center = new JButton("Center"),
                open = new JButton("Open"), save = new JButton("Save"), template = new JButton("Template");
        JComboBox<String> size = new JComboBox<>(new String[] { "12", "14", "16", "18", "20", "24", "28", "32" });
        bar.add(bold);
        bar.add(italic);
        bar.add(heading);
        bar.add(size);
        bar.add(left);
        bar.add(center);
        bar.add(bullet);
        bar.add(open);
        bar.add(save);
        bar.add(template);
        bold.addActionListener(e -> style(editor, StyleConstants.Bold, true));
        italic.addActionListener(e -> style(editor, StyleConstants.Italic, true));
        heading.addActionListener(e -> {
            SimpleAttributeSet a = new SimpleAttributeSet();
            StyleConstants.setBold(a, true);
            StyleConstants.setFontSize(a, 24);
            editor.setCharacterAttributes(a, false);
        });
        size.addActionListener(e -> {
            SimpleAttributeSet a = new SimpleAttributeSet();
            StyleConstants.setFontSize(a, Integer.parseInt((String) size.getSelectedItem()));
            editor.setCharacterAttributes(a, false);
        });
        left.addActionListener(e -> align(editor, StyleConstants.ALIGN_LEFT));
        center.addActionListener(e -> align(editor, StyleConstants.ALIGN_CENTER));
        bullet.addActionListener(e -> editor.replaceSelection("• "));
        template.addActionListener(e -> editor.setText(
                "DOCUMENT TITLE\\n\\nPrepared by: __________________\\nDate: __________________\\n\\nIntroduction\\nWrite your content here...\\n\\nConclusion\\n"));
        open.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(p) == JFileChooser.APPROVE_OPTION)
                try {
                    editor.setText(Files.readString(fc.getSelectedFile().toPath()));
                } catch (Exception ex) {
                    error(p, ex);
                }
        });
        save.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("document.txt"));
            if (fc.showSaveDialog(p) == JFileChooser.APPROVE_OPTION)
                try {
                    Files.writeString(fc.getSelectedFile().toPath(), editor.getText());
                } catch (Exception ex) {
                    error(p, ex);
                }
        });
        p.add(bar, BorderLayout.NORTH);
        p.add(new JScrollPane(editor));
        return p;
    }

    static void style(JTextPane t, Object key, boolean value) {
        SimpleAttributeSet a = new SimpleAttributeSet();
        if (key == StyleConstants.Bold)
            StyleConstants.setBold(a, value);
        else
            StyleConstants.setItalic(a, value);
        t.setCharacterAttributes(a, false);
    }

    static void align(JTextPane t, int v) {
        SimpleAttributeSet a = new SimpleAttributeSet();
        StyleConstants.setAlignment(a, v);
        t.setParagraphAttributes(a, false);
    }

    static void error(Component c, Exception e) {
        JOptionPane.showMessageDialog(c, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
