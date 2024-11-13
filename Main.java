import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    // Declare GUI objects & story progression variables
    private static JTextArea storyArea;
    private static JButton upButton, rightButton, downButton, leftButton, yesButton, noButton, restartButton;
    private static String state;
    private static String scenario;
    private static String startText = "It's the apocalypse, and you're currently stuck as the janitor in CostCo. People are bustling around the store, stacking on top of eachother. Will you stay and make bastion in the CostCo?";

    // *** STORY PROGRESSION SECTION ***
    static class DirectionListener implements ActionListener {
        private String command;

        public DirectionListener(String command) {
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (command.equals("Restart")) {
                restartGame();
            } 
            else if (state.equals("start")) {
                processStateStart();
            } 
            else if (state.equals("s1_tavern") && scenario.equals("s1_none")) {
                processWeddingChoice();
            } 
            else if (state.equals("s1_tavern") && scenario.equals("s1_noWedding")) {
           
            }
            // ADD MORE ELSE IF STATEMENTS FOR FURTHER STORY BRANCHES!!!
        }

        private void processStateStart() {
            if (command.equals("Yes")) {
                storyArea.setText("You make fort in TV aisle, and make walls of bright lights and reflections to throw off zombies. You see a TV already broken, do you search for a glass shard? (y/n)");
                toggleDirectionButtons(false);
                toggleYesNoButtons(true);
                state = "s1_tavern";
                scenario = "s1_none";
            }
            else if (command.equals("No")) {
                storyArea.setText("You walk out of the store instead of staying safe. You know whats gonna happen. The zombies dogpile you.");
            }
        }

        private void processWeddingChoice() {
            if (command.equals("Yes")) {
                storyArea.setText("You grab a glass shard and stay defended. All the zombies get very scared of your strength and run, you win!");
                endGame();
            } else if (command.equals("No")) {
                storyArea.setText("You raise your fists against the zombies, and realize they're way too strong. You try to run but they happen to also be faster. :(");
                toggleYesNoButtons(false);
                toggleDirectionButtons(true);
                scenario = "s1_noWedding";
            }
        }

      

     
        }
    

    // *** GUI CUSTOMIZATION BELOW ***
    public static void main(String[] args) {
        JFrame frame = new JFrame("Central Park Adventure");
        JPanel canvas = new JPanel();
        canvas.setPreferredSize(new Dimension(500, 500));
        canvas.setLayout(new BorderLayout());
        Color backgroundColor = new Color(0xbee1e6);
        canvas.setBackground(backgroundColor);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(backgroundColor);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.setBackground(backgroundColor);

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("janitorcloset.jpg");
        Image scaledImage = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imagePanel.add(imageLabel);

        contentPanel.add(imagePanel, BorderLayout.NORTH);

        storyArea = new JTextArea();
        storyArea.setEditable(false);
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);
        storyArea.setMargin(new Insets(8, 8, 8, 8));
        storyArea.setFont(new Font("DejaVu Sans", Font.PLAIN, 20));
        storyArea.setText(startText);

        JScrollPane storyScrollPane = new JScrollPane(storyArea);
        storyScrollPane.setPreferredSize(new Dimension(500, 200));
        contentPanel.add(storyScrollPane, BorderLayout.CENTER);

        canvas.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        upButton = createButton("⬆", "Up", new Color(0x695c99));
        rightButton = createButton("➡", "Right", new Color(0x695c99));
        downButton = createButton("⬇", "Down", new Color(0x695c99));
        leftButton = createButton("⬅", "Left", new Color(0x695c99));
        yesButton = createButton("✓ YES", "Yes", new Color(0x247b7b));
        noButton = createButton("✗ NO ", "No", new Color(0xaf4d98));
        restartButton = createButton("RESTART", "Restart", new Color(0x979dac));

        gbc.gridx = 1; gbc.gridy = 0;
        buttonPanel.add(upButton, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        buttonPanel.add(rightButton, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        buttonPanel.add(downButton, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        buttonPanel.add(leftButton, gbc);

        JPanel spacerPanel = new JPanel();
        spacerPanel.setBackground(backgroundColor);
        gbc.gridx = 3; gbc.gridy = 1; gbc.gridheight = 3;
        buttonPanel.add(spacerPanel, gbc);

        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 1;
        buttonPanel.add(yesButton, gbc);
        gbc.gridx = 4; gbc.gridy = 1;
        buttonPanel.add(noButton, gbc);
        gbc.gridx = 4; gbc.gridy = 2;
        buttonPanel.add(restartButton, gbc);

        yesButton.setEnabled(false);
        noButton.setEnabled(false);

        canvas.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(canvas);

        state = "start";
        scenario = "s0";

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JButton createButton(String text, String command, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.addActionListener(new DirectionListener(command));
        return button;
    }

    // *** DO NOT MODIFY THE CODE BELOW ***
    private static void restartGame() {
        storyArea.setText(startText);
        state = "start";
        scenario = "s0";
        toggleDirectionButtons(false);
        toggleYesNoButtons(true);
    }

    private static void toggleDirectionButtons(boolean enable) {
        upButton.setEnabled(enable);
        rightButton.setEnabled(enable);
        downButton.setEnabled(enable);
        leftButton.setEnabled(enable);
    }

    private static void toggleYesNoButtons(boolean enable) {
        yesButton.setEnabled(enable);
        noButton.setEnabled(enable);
    }

    private static void endGame() {
        toggleDirectionButtons(false);
        toggleYesNoButtons(false);
    }
}
