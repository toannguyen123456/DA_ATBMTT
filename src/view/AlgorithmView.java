package view;
import controller.AlgorithmController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
public class AlgorithmView extends JFrame {

    public AlgorithmView() {
        this.init();
    }
    public void init() {
        this.setTitle("Mã Hóa");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 423);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(25, 25, 112));
        mainPanel.setBorder(new EmptyBorder(5, 20, 10, 20));

        JPanel jPanelMHDXCB = new JPanel(new BorderLayout());
        jPanelMHDXCB.setBackground(new Color(25, 25, 112));

        ActionListener actionListener = new AlgorithmController(this);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10 , 10));
        buttonPanel.setBackground(new Color(25, 25, 112));

        JButton dcButton = createButtonWithSize("Dịch Chuyển", 30, 30);
        dcButton.setBackground(new Color(245, 245, 245));
        dcButton.setFont(new Font("Arial", Font.PLAIN, 14));
        dcButton.addActionListener(actionListener);

        JButton ttButton = createButtonWithSize("Thay Thế",30, 30);
        ttButton.setBackground(new Color(245, 245, 245));
        ttButton.setFont(new Font("Arial", Font.PLAIN, 14));
        ttButton.addActionListener(actionListener);

        JButton affineButton = createButtonWithSize("Affine",30, 30);
        affineButton.setBackground(new Color(245, 245, 245));
        affineButton.setFont(new Font("Arial", Font.PLAIN, 14));
        affineButton.addActionListener(actionListener);

        JButton vigenereButton = createButtonWithSize("Vigenere",30, 30);
        vigenereButton.setBackground(new Color(245, 245, 245));
        vigenereButton.setFont(new Font("Arial", Font.PLAIN, 14));
        vigenereButton.addActionListener(actionListener);

        JButton hillButton = createButtonWithSize("Hill",30, 30);
        hillButton.setBackground(new Color(245, 245, 245));
        hillButton.setFont(new Font("Arial", Font.PLAIN, 14));
        hillButton.addActionListener(actionListener);

        buttonPanel.add(dcButton);
        buttonPanel.add(ttButton);
        buttonPanel.add(affineButton);
        buttonPanel.add(vigenereButton);
        buttonPanel.add(hillButton);

        jPanelMHDXCB.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(jPanelMHDXCB, BorderLayout.NORTH);

        Border blueBorder = BorderFactory.createLineBorder(Color.white, 1);
        TitledBorder titleBorder = BorderFactory.createTitledBorder(blueBorder, "Mã hóa cơ bản");
        titleBorder.setTitleColor(Color.WHITE);
        jPanelMHDXCB.setBorder(BorderFactory.createCompoundBorder(titleBorder, new EmptyBorder(10, 10, 10, 10)));


        // Section 2: Mã hóa đối xứng
        JPanel jPanelMHDX = new JPanel(new BorderLayout());
        jPanelMHDX.setBackground(new Color(25, 25, 112));

        JPanel buttonPanelDX = new JPanel(new GridLayout(4, 3, 10, 10));
        buttonPanelDX.setBackground(new Color(25, 25, 112));

        JButton desButton = createButtonWithSize("DES", 30, 30);
        desButton.setBackground(new Color(245, 245, 245));
        desButton.setFont(new Font("Arial", Font.PLAIN, 14));
        desButton.addActionListener(actionListener);

        JButton aesButton = createButtonWithSize("AES", 30, 30);
        aesButton.setBackground(new Color(245, 245, 245));
        aesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        aesButton.addActionListener(actionListener);

        JButton Button3DES4 = createButtonWithSize("3DES", 30, 30);
        Button3DES4.setBackground(new Color(245, 245, 245));
        Button3DES4.setFont(new Font("Arial", Font.PLAIN, 14));
        Button3DES4.addActionListener(actionListener);

        JButton ButtonBlowfish = createButtonWithSize("Blowfish", 30, 30);
        ButtonBlowfish.setBackground(new Color(245, 245, 245));
        ButtonBlowfish.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonBlowfish.addActionListener(actionListener);

        JButton ButtonChaCha20 = createButtonWithSize("ChaCha20", 30, 30);
        ButtonChaCha20.setBackground(new Color(245, 245, 245));
        ButtonChaCha20.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonChaCha20.addActionListener(actionListener);

        JButton ButtonRC2 = createButtonWithSize("RC2", 30, 30);
        ButtonRC2.setBackground(new Color(245, 245, 245));
        ButtonRC2.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonRC2.addActionListener(actionListener);



        JButton ButtonRC4 = createButtonWithSize("RC4", 30, 30);
        ButtonRC4.setBackground(new Color(245, 245, 245));
        ButtonRC4.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonRC4.addActionListener(actionListener);


        JButton ButtonRC5 = createButtonWithSize("RC5", 30, 30);
        ButtonRC5.setBackground(new Color(245, 245, 245));
        ButtonRC5.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonRC5.addActionListener(actionListener);

        JButton ButtonRC51 = createButtonWithSize("RC5", 30, 30);
        ButtonRC51.setBackground(new Color(245, 245, 245));
        ButtonRC51.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonRC51.addActionListener(actionListener);


        buttonPanelDX.add(desButton);
        buttonPanelDX.add(aesButton);
        buttonPanelDX.add(Button3DES4);
        buttonPanelDX.add(ButtonBlowfish);
        buttonPanelDX.add(ButtonChaCha20);
        buttonPanelDX.add(ButtonRC2);
        buttonPanelDX.add(ButtonRC4);
        buttonPanelDX.add(ButtonRC5);
        buttonPanelDX.add(ButtonRC51);

        jPanelMHDX.add(buttonPanelDX, BorderLayout.CENTER);
        TitledBorder titledBorderDX = BorderFactory.createTitledBorder(blueBorder, "Mã hóa đối xứng");
        titledBorderDX.setTitleColor(Color.WHITE);
        jPanelMHDX.setBorder(BorderFactory.createCompoundBorder(titledBorderDX, new EmptyBorder(10, 10, 10, 10)));
        mainPanel.add(jPanelMHDX, BorderLayout.CENTER);

// Section 3: Mã hóa bất đối xứng
        JPanel jPanelMHBDX = new JPanel(new BorderLayout());
        jPanelMHBDX.setBackground(new Color(25, 25, 112));

        JPanel buttonPanelBDX = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanelBDX.setBackground(new Color(25, 25, 112));

        JButton rsaButton = createButtonWithSize("RSA", 30, 30);
        rsaButton.setBackground(new Color(245, 245, 245));
        rsaButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rsaButton.addActionListener(actionListener);

        JButton elgamalButton = createButtonWithSize("ElGamal", 30, 30);
        elgamalButton.setBackground(new Color(245, 245, 245));
        elgamalButton.setFont(new Font("Arial", Font.PLAIN, 14));
        elgamalButton.addActionListener(actionListener);

        buttonPanelBDX.add(rsaButton);
        buttonPanelBDX.add(elgamalButton);

        jPanelMHBDX.add(buttonPanelBDX, BorderLayout.CENTER);

        TitledBorder titledBorderBDX = BorderFactory.createTitledBorder(blueBorder, "Mã hóa bất đối xứng");
        titledBorderBDX.setTitleColor(Color.WHITE);
        jPanelMHBDX.setBorder(BorderFactory.createCompoundBorder(titledBorderBDX, new EmptyBorder(10, 10, 10, 10)));
        mainPanel.add(jPanelMHBDX, BorderLayout.SOUTH);


        this.add(mainPanel);
        this.setVisible(true);

    }

    public static JButton createButtonWithSize(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }
}
