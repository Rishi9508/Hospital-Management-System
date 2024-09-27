import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

class DoctorDetailsUI extends JFrame {
    static String doc_name, doc_reg;

    public DoctorDetailsUI(List<String> names, List<String> education, List<String> registration,
            List<String> languages, List<String> experience, List<String> location,
            List<String> weekAvailability, List<String> dailyTiming,
            List<BufferedImage> doctorImages) {

        setTitle("Doctor Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new GridLayout(0, 2, 20, 20));

        for (int i = 0; i < names.size(); i++) {
            JPanel doctorPanel = new JPanel(new BorderLayout(10, 10));
            doctorPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            if (doctorImages.get(i) != null) {
                JLabel imageLabel = new JLabel(
                        new ImageIcon(doctorImages.get(i).getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                doctorPanel.add(imageLabel, BorderLayout.WEST);
            }

            JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel nameLabel = new JLabel("Dr " + names.get(i));
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            infoPanel.add(nameLabel);

            JLabel eduLabel = new JLabel(education.get(i));
            eduLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(eduLabel);

            JLabel regLabel = new JLabel("Registration No: " + registration.get(i));
            regLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(regLabel);

            JLabel langLabel = new JLabel("Languages: " + languages.get(i));
            langLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(langLabel);

            JLabel expLabel = new JLabel(experience.get(i) + " years experience overall");
            expLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(expLabel);

            JLabel locLabel = new JLabel("Location: " + location.get(i));
            locLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(locLabel);

            JLabel weekLabel = new JLabel("Available: " + weekAvailability.get(i));
            weekLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(weekLabel);

            JLabel dailyLabel = new JLabel("Timing: " + dailyTiming.get(i));
            dailyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            infoPanel.add(dailyLabel);

            doctorPanel.add(infoPanel, BorderLayout.CENTER);

            JButton bookButton = new JButton("BOOK AN APPOINTMENT");
            bookButton.setFont(new Font("Arial", Font.BOLD, 14));
            bookButton.setPreferredSize(new Dimension(220, 40));
            bookButton.setBackground(Color.decode("#007BFF"));
            bookButton.setForeground(Color.WHITE);
            bookButton.setFocusPainted(false);

            int index = i;
            bookButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Booking an appointment with Dr. " + names.get(index));
                    Appointment.showDoctorAvailability();
                    doc_name = names.get(index);
                    doc_reg = registration.get(index);
                    dispose();
                }
            });

            setLocationRelativeTo(null);
            doctorPanel.add(bookButton, BorderLayout.SOUTH);
            add(doctorPanel);
        }
        setVisible(true);
    }
}

class RoundedPanel extends JPanel {
    private int cornerRadius;

    public RoundedPanel(int radius) {
        this.cornerRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
    }
}

class RoundedTextField extends JTextField {
    private int cornerRadius;

    public RoundedTextField(int radius) {
        this.cornerRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            super.paintComponent(g);
        } else {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
    }
}

class RoundedBorder extends AbstractBorder {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 10, 10, 10);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = 10;
        return insets;
    }
}

class Appointment_BookingUI {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/rishi?user=root";
    private static final String username = "root";
    private static final String password = "Crossroad@09";
    JFrame frame;
    private BufferedImage backgroundImage;

    Appointment_BookingUI(List<String> d_name, List<String> d_regis, List<String> app_day, List<String> app_time,
            List<Float> price,
            String n, String addr, String pho, String age, String gen) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(900, 700);

        try {
            backgroundImage = ImageIO.read(new File("appoint.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);
        int sum = 0;
        for (int i = 0; i < price.size(); i++) {
            sum += price.get(i);
        }

        JLabel patientNameLabel = new JLabel("Patient Name: " + n + "            Age: " + age);
        patientNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        patientNameLabel.setBounds(100, 80, 500, 30);
        backgroundPanel.add(patientNameLabel);

        JLabel patientAddressLabel = new JLabel("Patient Address: " + addr);
        patientAddressLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        patientAddressLabel.setBounds(100, 120, 500, 30);
        backgroundPanel.add(patientAddressLabel);

        JLabel patientPhoneLabel = new JLabel("Phone No.: " + pho);
        patientPhoneLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        patientPhoneLabel.setBounds(100, 160, 500, 30);
        backgroundPanel.add(patientPhoneLabel);

        JLabel patientGenderLabel = new JLabel("Gender: " + gen);
        patientGenderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        patientGenderLabel.setBounds(100, 200, 500, 30);
        backgroundPanel.add(patientGenderLabel);

        JLabel appointmentDetailsLabel = new JLabel("Appointment Details");
        appointmentDetailsLabel.setFont(new Font("Arial", Font.BOLD, 25));
        appointmentDetailsLabel.setBounds(300, 250, 300, 30);
        backgroundPanel.add(appointmentDetailsLabel);

        JLabel registrationLabel = new JLabel("Registration No.");
        registrationLabel.setFont(new Font("Arial", Font.BOLD, 16));
        registrationLabel.setBounds(40, 300, 180, 30);
        backgroundPanel.add(registrationLabel);

        JLabel doctorNameLabel = new JLabel("Doctor Name");
        doctorNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        doctorNameLabel.setBounds(220, 300, 180, 30);
        backgroundPanel.add(doctorNameLabel);

        JLabel chargesLabel = new JLabel("Charges");
        chargesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chargesLabel.setBounds(400, 300, 180, 30);
        backgroundPanel.add(chargesLabel);

        JLabel dateTimeLabel = new JLabel("Date & Time");
        dateTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateTimeLabel.setBounds(500, 300, 180, 30);
        backgroundPanel.add(dateTimeLabel);

        JLabel total = new JLabel("Total Payable : " + "₹ " + sum);
        total.setFont(new Font("Arial", Font.BOLD, 20));
        total.setBounds(340, 530, 250, 40);
        backgroundPanel.add(total);

        JLabel delete = new JLabel("Delete");
        delete.setFont(new Font("Arial", Font.BOLD, 16));
        delete.setBounds(750, 300, 100, 30);
        backgroundPanel.add(delete);

        int yPosition = 350;
        for (int i = 0; i < d_name.size(); i++) {
            JLabel regLabel = new JLabel(d_regis.get(i));
            regLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            regLabel.setBounds(65, yPosition, 180, 30);
            backgroundPanel.add(regLabel);

            JLabel docNameLabel = new JLabel(d_name.get(i));
            docNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            docNameLabel.setBounds(210, yPosition, 180, 30);
            backgroundPanel.add(docNameLabel);

            JLabel chargeLabel = new JLabel("₹" + price.get(i));
            chargeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            chargeLabel.setBounds(405, yPosition, 180, 30);
            backgroundPanel.add(chargeLabel);

            JLabel dateTimeValueLabel = new JLabel(app_day.get(i) + " " + app_time.get(i));
            dateTimeValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            dateTimeValueLabel.setBounds(505, yPosition, 240, 30);
            backgroundPanel.add(dateTimeValueLabel);

            JButton deleteButton = new JButton("Delete");
            deleteButton.setBounds(750, yPosition, 80, 30);
            backgroundPanel.add(deleteButton);

            int index = i;
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int response = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this appointment?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                        } catch (ClassNotFoundException f) {
                            f.printStackTrace();
                        }
                        try {
                            Connection conn = DriverManager.getConnection(url, username, password);
                            String query = "DELETE FROM booking WHERE DOCTOR_REGISTRATION = ? AND PATIENT_ID = ?";
                            PreparedStatement pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, d_regis.get(index));
                            pstmt.setString(2, Generate_Bill.user);
                            int rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Appointment deleted successfully.");
                            } else {
                                System.out.println("Appointment not deleted.");
                            }
                        } catch (SQLException g) {
                            System.out.println(g.getMessage());
                        }
                        frame.dispose();
                        new Third();
                    }
                }
            });

            yPosition += 40;
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class Generate_Bill implements ActionListener {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/rishi?user=root";
    private static final String username = "root";
    private static final String password = "Crossroad@09";
    JTextField text1;
    JLabel label1;
    JButton submit;
    JFrame frame;
    static String user;
    List<String> d_name = new ArrayList<>();
    List<String> d_regis = new ArrayList<>();
    List<String> app_day = new ArrayList<>();
    List<String> app_time = new ArrayList<>();
    List<Float> price = new ArrayList<>();
    static String n, addr, pho, age, gen;

    private void clearLists() {
        d_name.clear();
        d_regis.clear();
        app_day.clear();
        app_time.clear();
    }

    public void search_Booking(String user) {
        clearLists();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM booking WHERE PATIENT_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String doctor_name = rs.getString("DOCTOR_NAME");
                String doctor_registration = rs.getString("DOCTOR_REGISTRATION");
                String appointment_day = rs.getString("APPOINTMENT_DAY");
                String appointment_time = rs.getString("REGISTRATION_TIMING");
                d_name.add(doctor_name);
                d_regis.add(doctor_registration);
                app_day.add(appointment_day);
                app_time.add(appointment_time);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getPatient(String user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM patient WHERE ADHAAR = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                n = rs.getString("NAME");
                addr = rs.getString("ADDRESS");
                pho = rs.getString("PHONE");
                age = rs.getString("AGE");
                gen = rs.getString("GENDER");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getPrice() {
        for (int i = 0; i < d_regis.size(); i++) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Connection conn = DriverManager.getConnection(url, username, password);
                String query = "SELECT * FROM doctor WHERE REGISTRATION = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, d_regis.get(i));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Float pr = rs.getFloat("PRICE");
                    price.add(pr);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    Generate_Bill() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 220);
        frame.setLayout(new GridBagLayout());

        RoundedPanel panel1 = new RoundedPanel(30);
        panel1.setPreferredSize(new Dimension(350, 49));
        panel1.setOpaque(false);
        panel1.setBackground(Color.lightGray);
        panel1.setLayout(null);
        label1 = new JLabel("ADHAAR_NO : ");
        label1.setFont(new Font("Arial", Font.BOLD, 12));
        label1.setBounds(7, 10, 100, 30);
        panel1.add(label1);
        text1 = new RoundedTextField(20);
        text1.setBounds(93, 5, 250, 40);
        text1.setFont(new Font("Consolas", Font.PLAIN, 25));
        text1.setBackground(Color.BLACK);
        text1.setForeground(Color.white);
        text1.setCaretColor(Color.RED);
        text1.setBorder(new EmptyBorder(5, 5, 5, 5));
        text1.setOpaque(false);
        panel1.add(text1);
        panel1.setBounds(0, 250, 390, 50);
        frame.add(panel1);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel1, gbc);

        submit = new JButton("Submit") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        submit.setFont(new Font("Arial", Font.PLAIN, 25));
        submit.setForeground(Color.WHITE);
        submit.setBounds(140, 520, 110, 40);
        submit.setOpaque(false);
        submit.setContentAreaFilled(false);
        submit.setFocusPainted(false);
        submit.addActionListener(this);
        GridBagConstraints bbc = new GridBagConstraints();
        bbc.gridx = 0;
        bbc.gridy = 1;
        bbc.insets = new Insets(20, 0, 0, 0);
        bbc.anchor = GridBagConstraints.PAGE_END;
        frame.add(submit, bbc);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            user = text1.getText();
            search_Booking(user);
            getPatient(user);
            getPrice();
            new Appointment_BookingUI(d_name, d_regis, app_day, app_time, price, n, addr, pho, age, gen);
            frame.dispose();
        }
    }
}

class Viewpatient {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/rishi?user=root";
    private static final String username = "root";
    private static final String password = "Crossroad@09";
    List<String> names = new ArrayList<>();
    List<String> ages = new ArrayList<>();
    List<String> father = new ArrayList<>();
    List<String> add = new ArrayList<>();
    List<String> phone = new ArrayList<>();
    List<String> blood = new ArrayList<>();
    List<String> gender = new ArrayList<>();
    List<String> adhaar = new ArrayList<>();
    JFrame frame_view;

    private void clearLists() {
        names.clear();
        ages.clear();
        father.clear();
        add.clear();
        phone.clear();
        blood.clear();
        gender.clear();
        adhaar.clear();
    }

    Viewpatient(String USER) {
        clearLists();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM patient WHERE USER_NAME = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, USER);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String n = rs.getString("NAME");
                String age = rs.getString("AGE");
                String f_n = rs.getString("FATHER_NAME");
                String addr = rs.getString("ADDRESS");
                String pho = rs.getString("PHONE");
                String blo = rs.getString("BLOOD");
                String gen = rs.getString("GENDER");
                String adh = rs.getString("ADHAAR");
                names.add(n);
                ages.add(age);
                father.add(f_n);
                add.add(addr);
                phone.add(pho);
                blood.add(blo);
                gender.add(gen);
                adhaar.add(adh);
            }
            if (!names.isEmpty()) {
                display();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String del) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement state = conn.createStatement();
            String query = "DELETE FROM patient WHERE ADHAAR = " + del;
            int rowsaffected = state.executeUpdate(query);
            if (rowsaffected > 0) {
                int result = JOptionPane.showConfirmDialog(null, "Would You Like To Proceed Further", "Delete",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    new Third();
                    frame_view.dispose();
                }
            } else {
                System.out.println("Data not Deleted");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void display() {
        frame_view = new JFrame();
        frame_view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_view.setSize(500, 800);
        frame_view.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1, 10, 10));

        int n = names.size();
        Font font = new Font("Arial", Font.BOLD, 20);

        for (int i = 0; i < n; i++) {
            JPanel patientPanel = new JPanel();
            patientPanel.setLayout(new GridLayout(0, 1, 5, 5));
            patientPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel fatherPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel agePhonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel genderBloodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel adhaarDeletePanel = new JPanel(new BorderLayout());

            JLabel nameLabel = new JLabel("Patient Name: " + names.get(i));
            JLabel fatherLabel = new JLabel("Father's Name: " + father.get(i));
            JLabel ageLabel = new JLabel("Age: " + ages.get(i));
            JLabel phoneLabel = new JLabel("Phone: " + phone.get(i));
            JLabel addressLabel = new JLabel("Address: " + add.get(i));
            JLabel bloodLabel = new JLabel("Blood Group: " + blood.get(i));
            JLabel genderLabel = new JLabel("Gender: " + gender.get(i));
            JLabel adhaarLabel = new JLabel("Adhaar: " + adhaar.get(i));

            nameLabel.setFont(font);
            fatherLabel.setFont(font);
            ageLabel.setFont(font);
            phoneLabel.setFont(font);
            addressLabel.setFont(font);
            bloodLabel.setFont(font);
            genderLabel.setFont(font);
            adhaarLabel.setFont(font);

            namePanel.add(nameLabel);
            fatherPanel.add(fatherLabel);
            agePhonePanel.add(ageLabel);
            agePhonePanel.add(Box.createHorizontalStrut(50));
            agePhonePanel.add(phoneLabel);

            addressPanel.add(addressLabel);

            genderBloodPanel.add(genderLabel);
            genderBloodPanel.add(Box.createHorizontalStrut(50));
            genderBloodPanel.add(bloodLabel);

            JButton deleteButton = new JButton("Delete") {
                @Override
                protected void paintComponent(Graphics g) {
                    if (getModel().isPressed()) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.RED);
                    }
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                    super.paintComponent(g);
                }

                @Override
                protected void paintBorder(Graphics g) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                }
            };
            deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
            deleteButton.setPreferredSize(new Dimension(120, 40));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setOpaque(false);
            deleteButton.setContentAreaFilled(false);
            deleteButton.setFocusPainted(false);

            int index = i;
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    delete(adhaar.get(index));
                }
            });
            adhaarDeletePanel.add(adhaarLabel, BorderLayout.WEST);
            adhaarDeletePanel.add(deleteButton, BorderLayout.EAST);

            patientPanel.add(namePanel);
            patientPanel.add(fatherPanel);
            patientPanel.add(agePhonePanel);
            patientPanel.add(addressPanel);
            patientPanel.add(genderBloodPanel);
            patientPanel.add(adhaarDeletePanel);

            patientPanel.setBorder(BorderFactory.createTitledBorder("Patient " + (i + 1)));
            mainPanel.add(patientPanel);
        }
        frame_view.add(mainPanel, BorderLayout.CENTER);

        frame_view.setLocationRelativeTo(null);
        frame_view.setResizable(false);
        frame_view.setVisible(true);
    }
}

class Appointment extends JFrame implements ActionListener {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/rishi?user=root";
    private static final String username = "root";
    private static final String password = "Crossroad@09";
    JTextField text1;
    JLabel label1;
    static String proof, selectedDay, selectedTime, patient_name, patient_age, patient_father, patient_address,
            patient_phone, patient_blood, patient_gender;
    JButton submit, button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11,
            button12;
    static JButton confirmButton;
    JFrame frame_new, frame_doc;
    static JFrame frame_last;
    private BufferedImage backgroundImage;
    JPanel backgroundPanel;
    List<BufferedImage> doctorImages = new ArrayList<>();
    List<String> Names = new ArrayList<>();
    List<String> education = new ArrayList<>();
    List<String> registration = new ArrayList<>();
    List<String> language = new ArrayList<>();
    List<String> experience = new ArrayList<>();
    List<String> location = new ArrayList<>();
    List<String> weekA = new ArrayList<>();
    List<String> dailyT = new ArrayList<>();
    List<String> special = new ArrayList<>();

    private void clearLists() {
        Names.clear();
        education.clear();
        registration.clear();
        language.clear();
        experience.clear();
        location.clear();
        weekA.clear();
        dailyT.clear();
    }

    public void findDoctor(String speciality) {
        clearLists();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM doctor WHERE SPECIALITY = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, speciality);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                byte[] imageBytes = rs.getBytes("IMAGE");
                String name = rs.getString("NAME");
                String edu = rs.getString("EDUCATION");
                String reg = rs.getString("REGISTRATION");
                String lang = rs.getString("LANGUAGE");
                String exep = rs.getString("EXPERIENCE");
                String loc = rs.getString("LOCATION");
                String week = rs.getString("WEEK");
                String daily = rs.getString("TIMING");
                String spec = rs.getString("SPECIALITY");
                if (imageBytes != null) {
                    try {
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                        BufferedImage doctorImage = ImageIO.read(bis);
                        doctorImages.add(doctorImage);
                    } catch (IOException ex) {
                        Logger.getLogger(Appointment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                Names.add(name);
                education.add(edu);
                registration.add(reg);
                language.add(lang);
                experience.add(exep);
                location.add(loc);
                weekA.add(week);
                dailyT.add(daily);
                special.add(spec);
            }
            if (!Names.isEmpty()) {
                new DoctorDetailsUI(Names, education, registration, language, experience, location, weekA, dailyT,
                        doctorImages);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doctor_Appointment() {
        frame_doc = new JFrame();
        frame_doc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_doc.setSize(835, 570);
        frame_doc.setLayout(null);
        try {
            backgroundImage = ImageIO.read(new File("white.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        frame_doc.setContentPane(backgroundPanel);

        button1 = createButton("icon1.png", 15, 15);
        button2 = createButton("icon2.png", 215, 15);
        button3 = createButton("icon3.png", 415, 15);
        button4 = createButton("icon4.png", 615, 15);
        button5 = createButton("icon5.png", 15, 195);
        button6 = createButton("icon6.png", 215, 195);
        button7 = createButton("icon7.png", 415, 195);
        button8 = createButton("icon8.png", 615, 195);
        button9 = createButton("icon9.png", 15, 375);
        button10 = createButton("icon10.png", 215, 375);
        button11 = createButton("icon11.png", 415, 375);
        button12 = createButton("icon12.png", 615, 375);

        frame_doc.setResizable(false);
        frame_doc.setLocationRelativeTo(null);
        frame_doc.setVisible(true);
    }

    private JButton createButton(String iconPath, int x, int y) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isMouseOver() ? Color.RED : Color.BLACK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }

            private boolean isMouseOver() {
                return getMousePosition() != null;
            }
        };

        button.setPreferredSize(new Dimension(190, 160));
        button.setBounds(x, y, 190, 160);
        button.addActionListener(this);
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setForeground(Color.BLACK);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(new RoundedBorder(30));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.repaint();
            }
        });

        backgroundPanel.add(button);
        return button;
    }

    public void search(String user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT ADHAAR FROM patient WHERE ADHAAR = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                doctor_Appointment();
            } else {
                int response = JOptionPane.showConfirmDialog(null,
                        "Enter Valid Adhaar ID!!!",
                        "Warning",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    new Appointment();
                }
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showDoctorAvailability() {
        frame_last = new JFrame("Select Doctor's Available Slot");
        frame_last.setSize(400, 300);
        frame_last.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_last.setLayout(null);

        JLabel weekLabel = new JLabel("Select Day:");
        weekLabel.setBounds(50, 30, 100, 30);
        JComboBox<String> weekDropdown = new JComboBox<>(
                new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" });
        weekDropdown.setBounds(160, 30, 180, 30);

        JLabel timingLabel = new JLabel("Select Time:");
        timingLabel.setBounds(50, 80, 100, 30);
        JComboBox<String> timingDropdown = new JComboBox<>(
                new String[] { "10:00 AM - 12:00 PM", "2:00 PM - 4:00 PM", "6:00 PM - 8:00 PM" });
        timingDropdown.setBounds(160, 80, 180, 30);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.setBounds(90, 150, 200, 50);
        confirmButton.setFocusable(false);
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setOpaque(false);
        confirmButton.setBorder(new RoundedBorder(30));
        confirmButton.addActionListener(e -> {
            selectedDay = (String) weekDropdown.getSelectedItem();
            selectedTime = (String) timingDropdown.getSelectedItem();
            int result = JOptionPane.showConfirmDialog(frame_last,
                    "Booking confirmed for: " + selectedDay + " at " + selectedTime,
                    "Confirmation",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                bookAppointment(DoctorDetailsUI.doc_name, DoctorDetailsUI.doc_reg, proof);
            }

            frame_last.dispose();
        });
        frame_last.add(weekLabel);
        frame_last.add(weekDropdown);
        frame_last.add(timingLabel);
        frame_last.add(timingDropdown);
        frame_last.add(confirmButton);

        frame_last.setLocationRelativeTo(null);
        frame_last.setVisible(true);
    }

    public static void bookAppointment(String name, String registration, String id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM patient WHERE ADHAAR = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, proof);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                patient_name = rs.getString("NAME");
                patient_age = rs.getString("AGE");
                patient_father = rs.getString("FATHER_NAME");
                patient_address = rs.getString("ADDRESS");
                patient_phone = rs.getString("PHONE");
                patient_blood = rs.getString("BLOOD");
                patient_gender = rs.getString("GENDER");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO booking VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, patient_name);
            ps.setString(2, patient_age);
            ps.setString(3, patient_father);
            ps.setString(4, patient_address);
            ps.setString(5, patient_phone);
            ps.setString(6, patient_blood);
            ps.setString(7, patient_gender);
            ps.setString(8, proof);
            ps.setString(9, name);
            ps.setString(10, registration);
            ps.setString(11, selectedDay);
            ps.setString(12, selectedTime);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                new Third();
            } else {
                System.out.println("Failed");
            }
        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }
    }

    public Appointment() {
        frame_new = new JFrame();
        frame_new.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_new.setSize(400, 220);
        frame_new.setLayout(new GridBagLayout());

        RoundedPanel panel1 = new RoundedPanel(30);
        panel1.setPreferredSize(new Dimension(350, 49));
        panel1.setOpaque(false);
        panel1.setBackground(Color.lightGray);
        panel1.setLayout(null);
        label1 = new JLabel("ADHAAR_NO : ");
        label1.setFont(new Font("Arial", Font.BOLD, 12));
        label1.setBounds(7, 10, 100, 30);
        panel1.add(label1);
        text1 = new RoundedTextField(20);
        text1.setBounds(93, 5, 250, 40);
        text1.setFont(new Font("Consolas", Font.PLAIN, 25));
        text1.setBackground(Color.BLACK);
        text1.setForeground(Color.white);
        text1.setCaretColor(Color.RED);
        text1.setBorder(new EmptyBorder(5, 5, 5, 5));
        text1.setOpaque(false);
        panel1.add(text1);
        panel1.setBounds(0, 250, 390, 50);
        frame_new.add(panel1);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        frame_new.add(panel1, gbc);

        frame_new.setResizable(false);
        frame_new.setLocationRelativeTo(null);
        frame_new.setVisible(true);

        submit = new JButton("Submit") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        submit.setFont(new Font("Arial", Font.PLAIN, 25));
        submit.setForeground(Color.WHITE);
        submit.setBounds(140, 520, 110, 40);
        submit.setOpaque(false);
        submit.setContentAreaFilled(false);
        submit.setFocusPainted(false);
        submit.addActionListener(this);
        GridBagConstraints bbc = new GridBagConstraints();
        bbc.gridx = 0;
        bbc.gridy = 1;
        bbc.insets = new Insets(20, 0, 0, 0);
        bbc.anchor = GridBagConstraints.PAGE_END;
        frame_new.add(submit, bbc);

        frame_new.setResizable(false);
        frame_new.setLocationRelativeTo(null);
        frame_new.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            proof = text1.getText();
            search(proof);
            frame_new.dispose();
        }
        if (e.getSource() == button1) {
            findDoctor("CARDIOLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button2) {
            findDoctor("NEUROLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button3) {
            findDoctor("GYNECOLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button4) {
            findDoctor("UROLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button5) {
            findDoctor("GASTROENTEROLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button6) {
            findDoctor("NEPHROLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button7) {
            findDoctor("DERMATOLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button8) {
            findDoctor("ORTHOPEDIC");
            frame_doc.dispose();
        }
        if (e.getSource() == button9) {
            findDoctor("NEUROSURGERY");
            frame_doc.dispose();
        }
        if (e.getSource() == button10) {
            findDoctor("PULMONOLOGY");
            frame_doc.dispose();
        }
        if (e.getSource() == button11) {
            findDoctor("DENTIST");
            frame_doc.dispose();
        }
        if (e.getSource() == button12) {
            findDoctor("ENT");
            frame_doc.dispose();
        }
    }
}

class Patient implements ActionListener {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/rishi?user=root";
    private static final String username = "root";
    private static final String password = "Crossroad@09";
    JTextField firstNameField, age, fathername, address, phone, blood, gender, proof;
    JFrame frame_add, frame_x;
    JButton save, button_add, button_view;
    static String user;

    public void write(String name, int age, String father, String address, String phone, String blood, String gender,
            String adhaar) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO patient VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, father);
            ps.setString(5, address);
            ps.setString(6, phone);
            ps.setString(7, blood);
            ps.setString(8, gender);
            ps.setString(9, adhaar);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                new Third();
            } else {
                System.out.println("Failed");
            }
        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }
    }

    public void add_Patient() {
        frame_add = new JFrame();
        frame_add.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_add.setSize(400, 650);
        frame_add.setLayout(null);

        ImageIcon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(100, 5, 200, 100);
        frame_add.add(logoLabel);

        firstNameField = new JTextField("Full Name");
        Second.styleTextField(firstNameField, "Full Name");
        firstNameField.setBounds(50, 100, 200, 50);
        firstNameField.setOpaque(false);
        firstNameField.setBorder(new RoundedBorder(30));
        frame_add.add(firstNameField);

        age = new JTextField("Age");
        Second.styleTextField(age, "Age");
        age.setBounds(270, 100, 70, 50);
        age.setOpaque(false);
        age.setBorder(new RoundedBorder(30));
        frame_add.add(age);

        fathername = new JTextField("Father Name");
        Second.styleTextField(fathername, "Father Name");
        fathername.setBounds(50, 165, 290, 50);
        fathername.setOpaque(false);
        fathername.setBorder(new RoundedBorder(30));
        frame_add.add(fathername);

        address = new JTextField("Address");
        Second.styleTextField(address, "Address");
        address.setBounds(50, 230, 290, 50);
        address.setOpaque(false);
        address.setBorder(new RoundedBorder(30));
        frame_add.add(address);

        phone = new JTextField("Phone No.");
        Second.styleTextField(phone, "Phone No.");
        phone.setBounds(50, 295, 290, 50);
        phone.setOpaque(false);
        phone.setBorder(new RoundedBorder(30));
        frame_add.add(phone);

        blood = new JTextField("Blood Group");
        Second.styleTextField(blood, "Blood Group");
        blood.setBounds(50, 360, 140, 50);
        blood.setOpaque(false);
        blood.setBorder(new RoundedBorder(30));
        frame_add.add(blood);

        gender = new JTextField("Gender");
        Second.styleTextField(gender, "Gender");
        gender.setBounds(200, 360, 140, 50);
        gender.setOpaque(false);
        gender.setBorder(new RoundedBorder(30));
        frame_add.add(gender);

        JLabel genderInfoLabel = new JLabel("M=Male | F=Female | O=Other");
        genderInfoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        genderInfoLabel.setForeground(Color.GRAY);
        genderInfoLabel.setBounds(75, 420, 290, 20);
        frame_add.add(genderInfoLabel);

        proof = new JTextField("Adhaar Number");
        Second.styleTextField(proof, "Adhaar Number");
        proof.setBounds(50, 450, 290, 50);
        proof.setOpaque(false);
        proof.setBorder(new RoundedBorder(30));
        frame_add.add(proof);

        save = new JButton("Save") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.RED);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };

        save.setFont(new Font("Arial", Font.PLAIN, 25));
        save.setForeground(Color.WHITE);
        save.setBounds(140, 520, 110, 40);
        save.setOpaque(false);
        save.setContentAreaFilled(false);
        save.setFocusPainted(false);
        save.addActionListener(this);
        frame_add.add(save);

        frame_add.setResizable(false);
        frame_add.setLocationRelativeTo(null);
        frame_add.setVisible(true);
    }

    public Patient() {
        user = First.user_name;
        frame_x = new JFrame();
        frame_x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_x.setResizable(false);
        frame_x.setSize(550, 200);

        frame_x.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 40));

        ImageIcon icon = new ImageIcon("addPatient.png");
        button_add = new JButton("Add Patient", icon);
        button_add.setPreferredSize(new Dimension(200, 80));
        button_add.addActionListener(this);
        button_add.setFocusable(false);
        button_add.setFont(new Font("Arial", Font.PLAIN, 20));
        button_add.setForeground(Color.BLACK);
        button_add.setContentAreaFilled(false);
        button_add.setOpaque(false);
        button_add.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        button_add.setBorder(new RoundedBorder(30));
        frame_x.add(button_add);

        ImageIcon icon1 = new ImageIcon("viewPatient.png");
        button_view = new JButton("View Patient", icon1);
        button_view.setPreferredSize(new Dimension(200, 80));
        button_view.addActionListener(this);
        button_view.setFocusable(false);
        button_view.setFont(new Font("Arial", Font.PLAIN, 20));
        button_view.setForeground(Color.BLACK);
        button_view.setContentAreaFilled(false);
        button_view.setOpaque(false);
        button_view.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        button_view.setBorder(new RoundedBorder(30));
        frame_x.add(button_view);

        frame_x.setLocationRelativeTo(null);
        frame_x.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_add) {
            add_Patient();
            frame_x.dispose();
        }
        if (e.getSource() == button_view) {
            new Viewpatient(First.user_name);
            frame_x.dispose();
        }
        if (e.getSource() == save) {
            String name = firstNameField.getText();
            String a = age.getText();
            int umar = Integer.parseInt(a);
            String father = fathername.getText();
            String adres = address.getText();
            String pho = phone.getText();
            String blo = blood.getText();
            String gen = gender.getText();
            String adhaar = proof.getText();
            write(name, umar, father, adres, pho, blo, gen, adhaar);
            frame_add.dispose();
        }
    }
}

class Third extends JFrame implements ActionListener {
    JButton button_a, button_b, button_c, button_d;
    JFrame frame;
    private BufferedImage backgroundImage;

    public Third() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1000, 604);

        try {
            backgroundImage = ImageIO.read(new File("hosp.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g2d.dispose();
                }
            }
        };
        backgroundPanel.setLayout(null);
        frame.setContentPane(backgroundPanel);

        ImageIcon icon = new ImageIcon("patient.png");
        button_a = new JButton("Patient", icon);
        button_a.setBounds(80, 200, 250, 80);
        button_a.addActionListener(this);
        button_a.setFocusable(false);
        button_a.setFont(new Font("Arial", Font.PLAIN, 20));
        button_a.setForeground(Color.BLACK);
        button_a.setContentAreaFilled(false);
        button_a.setOpaque(false);
        button_a.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        button_a.setBorder(new RoundedBorder(30));
        backgroundPanel.add(button_a);

        ImageIcon icon1 = new ImageIcon("doctor.png");
        button_b = new JButton("Book Appoinment", icon1);
        button_b.setBounds(350, 200, 250, 80);
        button_b.addActionListener(this);
        button_b.setFocusable(false);
        button_b.setFont(new Font("Arial", Font.PLAIN, 20));
        button_b.setForeground(Color.BLACK);
        button_b.setContentAreaFilled(false);
        button_b.setOpaque(false);
        button_b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        button_b.setBorder(new RoundedBorder(30));
        backgroundPanel.add(button_b);

        ImageIcon icon2 = new ImageIcon("bill.png");
        button_c = new JButton("Generate Bill", icon2);
        button_c.setBounds(620, 200, 250, 80);
        button_c.addActionListener(this);
        button_c.setFocusable(false);
        button_c.setFont(new Font("Arial", Font.PLAIN, 20));
        button_c.setForeground(Color.BLACK);
        button_c.setContentAreaFilled(false);
        button_c.setOpaque(false);
        button_c.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        button_c.setBorder(new RoundedBorder(30));
        backgroundPanel.add(button_c);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button_a) {
            new Patient();
            frame.dispose();
        }
        if (e.getSource() == button_b) {
            new Appointment();
            frame.dispose();
        }
        if (e.getSource() == button_c) {
            new Generate_Bill();
            frame.dispose();
        }
    }
}

class Second extends JFrame implements ActionListener {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/rishi?user=root";
    private static final String username = "root";
    private static final String password = "Crossroad@09";
    JTextField firstNameField, lastNameField, userNameField;
    JPasswordField passwordField, confirmPasswordField;
    JLabel usernameTipLabel, passwordTipLabel;
    JCheckBox showPasswordCheckBox;
    JButton nextButton, signInInsteadButton;
    JFrame frame;

    public void save(String fN, String lN, String user, String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO user (USER,FIRSTNAME,LASTNAME,PASSWORD) VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, fN);
            ps.setString(3, lN);
            ps.setString(4, pass);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                frame.dispose();
                new First();
            } else {
                System.out.println("Failed");
            }
        } catch (SQLException x) {
            System.out.println(x.getMessage());
        }
    }

    Second() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(null);

        firstNameField = new JTextField("First name");
        styleTextField(firstNameField, "First name");
        firstNameField.setBounds(50, 30, 140, 40);
        firstNameField.setBorder(new RoundedBorder(30));
        firstNameField.setOpaque(false);
        frame.add(firstNameField);

        lastNameField = new JTextField("Last name");
        styleTextField(lastNameField, "Last name");
        lastNameField.setBounds(200, 30, 140, 40);
        lastNameField.setBorder(new RoundedBorder(30));
        lastNameField.setOpaque(false);
        frame.add(lastNameField);

        userNameField = new JTextField("Username");
        styleTextField(userNameField, "Username");
        userNameField.setBounds(50, 80, 290, 40);
        userNameField.setBorder(new RoundedBorder(30));
        userNameField.setOpaque(false);
        frame.add(userNameField);

        usernameTipLabel = new JLabel("You can use letters, numbers & periods");
        usernameTipLabel.setForeground(Color.GRAY);
        usernameTipLabel.setBounds(60, 120, 290, 20);
        frame.add(usernameTipLabel);

        passwordField = new JPasswordField();
        passwordField.setText("Password");
        passwordField.setEchoChar((char) 0);
        styleTextField(passwordField, "Password");
        passwordField.setBounds(50, 150, 290, 40);
        passwordField.setBorder(new RoundedBorder(30));
        passwordField.setOpaque(false);
        frame.add(passwordField);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setText("Confirm");
        confirmPasswordField.setEchoChar((char) 0);
        styleTextField(confirmPasswordField, "Confirm");
        confirmPasswordField.setBounds(50, 200, 290, 40);
        confirmPasswordField.setBorder(new RoundedBorder(30));
        confirmPasswordField.setOpaque(false);
        frame.add(confirmPasswordField);

        passwordTipLabel = new JLabel("Use 8 or more characters with a mix of letters, numbers & symbols");
        passwordTipLabel.setForeground(Color.GRAY);
        passwordTipLabel.setBounds(60, 240, 290, 20);
        frame.add(passwordTipLabel);

        showPasswordCheckBox = new JCheckBox("Show password");
        showPasswordCheckBox.setBounds(65, 270, 150, 20);
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                    confirmPasswordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('●');
                    confirmPasswordField.setEchoChar('●');
                }
            }
        });
        frame.add(showPasswordCheckBox);

        signInInsteadButton = new JButton("LogIn");
        signInInsteadButton.setForeground(Color.BLUE);
        signInInsteadButton.setBounds(50, 300, 110, 30);
        signInInsteadButton.setBorderPainted(true);
        signInInsteadButton.setOpaque(false);
        signInInsteadButton.setContentAreaFilled(false);
        signInInsteadButton.setBorder(new RoundedBorder(30));
        signInInsteadButton.addActionListener(this);
        frame.add(signInInsteadButton);

        nextButton = new JButton("Sign In") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        nextButton.setBackground(Color.BLUE);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBounds(230, 300, 110, 30);
        nextButton.setOpaque(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setBorder(new RoundedBorder(30));
        nextButton.addActionListener(this);
        frame.add(nextButton);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void styleTextField(JTextField textField, String placeholder) {
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.GRAY);
        textField.setCaretColor(Color.GRAY);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar('●');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String user_name = userNameField.getText();
            char[] password_c = passwordField.getPassword();
            String password = new String(password_c);
            char[] confirm_password = confirmPasswordField.getPassword();
            String confirm = new String(confirm_password);
            if (password.equals(confirm)) {
                save(firstName, lastName, user_name, password);
            } else {

            }
        }
        if (e.getSource() == signInInsteadButton) {
            new First();
            frame.dispose();
        }
    }
}

public class First extends JFrame implements ActionListener {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/rishi?user=root";
    private static final String username = "root";
    private static final String password = "Crossroad@09";
    static JTextField text1, text2;
    JLabel label1, label2, label3;
    JButton button, button1, playButton, depositButton, withdrawButton, exitButton;
    JFrame firstframe, frame_x;
    static String user_name;

    public void read(String user, String pass) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            String query = "SELECT USER,PASSWORD FROM user WHERE USER = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                new Third();
            } else {
                int response = JOptionPane.showConfirmDialog(null,
                        "Enter Valid UserName Password!!!",
                        "Warning",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    new First();
                }
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public First() {
        firstframe = new JFrame();
        firstframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstframe.setSize(410, 235);
        firstframe.setLayout(new GridLayout(4, 1, 10, 15));
        firstframe.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        label3 = new JLabel();
        label3.setText("Are you a New User?");
        firstframe.add(label3);

        button = new JButton("Sign UP") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.BLUE);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFont(new Font("Comic Sans", Font.BOLD, 13));
        button.setBounds(180, 10, 10, 10);
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.addActionListener(this);
        firstframe.add(button);

        RoundedPanel panel1 = new RoundedPanel(30);
        panel1.setPreferredSize(new Dimension(380, 45));
        panel1.setOpaque(false);
        panel1.setBackground(Color.lightGray);
        panel1.setLayout(null);
        label1 = new JLabel();
        label1.setText("USERNAME ");
        label1.setFont(new Font("Arial", Font.BOLD, 15));
        label1.setBounds(7, 8, 100, 30);
        panel1.add(label1);
        text1 = new RoundedTextField(30);
        text1.setBounds(105, 5, 260, 35);
        text1.setFont(new Font("Consolas", Font.PLAIN, 25));
        text1.setBackground(Color.BLACK);
        text1.setForeground(Color.white);
        text1.setCaretColor(Color.RED);
        text1.setBorder(new EmptyBorder(5, 5, 5, 5));
        text1.setOpaque(false);
        panel1.add(text1);
        panel1.setBounds(0, 90, 390, 50);
        firstframe.add(panel1);

        RoundedPanel panel2 = new RoundedPanel(30);
        panel2.setPreferredSize(new Dimension(380, 45));
        panel2.setOpaque(false);
        panel2.setBackground(Color.lightGray);
        panel2.setLayout(null);
        label2 = new JLabel();
        label2.setText("PASSWORD ");
        label2.setFont(new Font("Arial", Font.BOLD, 15));
        label2.setBounds(7, 8, 100, 30);
        panel2.add(label2);
        text2 = new RoundedTextField(30);
        text2.setBounds(105, 5, 260, 35);
        text2.setFont(new Font("Consolas", Font.PLAIN, 25));
        text2.setBackground(Color.BLACK);
        text2.setForeground(Color.white);
        text2.setCaretColor(Color.RED);
        text2.setBorder(new EmptyBorder(5, 5, 5, 5));
        text2.setOpaque(false);
        panel2.add(text2);
        panel2.setBounds(0, 90, 390, 50);
        firstframe.add(panel2);

        button1 = new JButton("LogIn") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.RED);
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(Color.RED);
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        button1.setFocusable(false);
        button1.setContentAreaFilled(false);
        button1.setOpaque(false);
        button1.setFont(new Font("Comic Sans", Font.BOLD, 13));
        button1.setBounds(105, 150, 40, 30);
        button1.setBorder(new RoundedBorder(10));
        button1.addActionListener(this);

        firstframe.setResizable(false);
        firstframe.setLocationRelativeTo(null);
        firstframe.add(button1);
        firstframe.setVisible(true);
    }

    public static void main(String[] args) {
        new First();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            new Second();
            firstframe.dispose();
        }
        if (e.getSource() == button1) {
            user_name = text1.getText();
            String pass_word = text2.getText();
            read(user_name, pass_word);
            firstframe.dispose();
        }
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int radius) {
            this.cornerRadius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
    }

    class RoundedTextField extends JTextField {
        private int cornerRadius;

        public RoundedTextField(int radius) {
            this.cornerRadius = radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (isOpaque()) {
                super.paintComponent(g);
            } else {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            }
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        }
    }
}