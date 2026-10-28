package devcheck;

import javax.swing.*;
import java.awt.*;

/** Simple Swing front end for the DevCheck Java command group. */
public final class DevCheckGui1 {
    private DevCheckGui1() {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DevCheckGui1::createWindow);
    }

    private static void createWindow() {
        JFrame frame = new JFrame("DevCheck CLI — Java Toolbox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(760, 540);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        JLabel heading = new JLabel("DevCheck CLI");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        JLabel subheading = new JLabel("Run your custom Java commands from a graphical interface.");
        JPanel header = new JPanel(new GridLayout(2, 1, 2, 2));
        header.add(heading);
        header.add(subheading);
        root.add(header, BorderLayout.NORTH);

        JTextField command = new JTextField("greet Sandesh");
        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        output.setText("Choose a sample command or type one above.\n\nExamples:\n  greet Sandesh\n  calc add 5 3\n  reverse hello world\n  palindrome Never odd or even\n  stats 10 20 30\n");
        JButton run = new JButton("Run Java command");
        JButton help = new JButton("Show help");
        JPanel controls = new JPanel(new BorderLayout(8, 8));
        controls.add(new JLabel("Command:"), BorderLayout.WEST);
        controls.add(command, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(help);
        buttons.add(run);
        controls.add(buttons, BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.add(controls, BorderLayout.NORTH);
        center.add(new JScrollPane(output), BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        Runnable execute = () -> {
            String raw = command.getText().trim();
            if (raw.isEmpty()) {
                output.setText("Enter a command first.");
                return;
            }
            String[] args = raw.split("\\s+");
            try {
                java.util.List<String> processArgs = new java.util.ArrayList<>();
                processArgs.add(System.getProperty("java.home") + java.io.File.separator + "bin"
                        + java.io.File.separator + (System.getProperty("os.name").toLowerCase().contains("win") ? "java.exe" : "java"));
                processArgs.add("-cp");
                processArgs.add(System.getProperty("java.class.path"));
                processArgs.add("devcheck.Main");
                java.util.Collections.addAll(processArgs, args);
                Process process = new ProcessBuilder(processArgs).redirectErrorStream(true).start();
                String result = new String(process.getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                int exitCode = process.waitFor();
                output.setText((result.isBlank() ? "(no output)" : result) + "\\nExit code: " + exitCode);
            } catch (Exception ex) {
                output.setText("Error: " + ex.getMessage());
            }
        };
        run.addActionListener(e -> execute.run());
        command.addActionListener(e -> execute.run());
        help.addActionListener(e -> {
            command.setText("help");
            execute.run();
        });
        frame.setContentPane(root);
        frame.setVisible(true);
    }
}
