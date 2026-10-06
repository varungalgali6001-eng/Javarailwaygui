import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class RailwayBookingGUI {

    // Train data using arrays
    static String[] trainNo = {
        "12951", "12009", "12124",
        "12220", "11019", "12137"
    };

    static String[] trainName = {
        "Mumbai Rajdhani Express",
        "Shatabdi Express",
        "Deccan Queen",
        "Duronto Express",
        "Konark Express",
        "Punjab Mail"
    };

    static String[] from = {
        "Mumbai", "Mumbai", "Pune",
        "Mumbai", "Mumbai", "Mumbai"
    };

    static String[] to = {
        "New Delhi", "Ahmedabad", "Mumbai",
        "Secunderabad", "Bhubaneswar", "Firozpur"
    };

    static String[] time = {
        "05:00 PM", "06:20 AM", "07:15 AM",
        "11:05 PM", "03:05 PM", "07:35 PM"
    };

    static double[] fare = {
        1800, 1100, 500,
        1500, 1400, 1600
    };

    static int[] seats = {
        25, 20, 30,
        18, 22, 16
    };


    // GUI components
    static JFrame frame;

    static JTextField nameField;
    static JTextField ageField;
    static JTextField phoneField;
    static JTextField ticketField;

    static JComboBox<String> genderBox;
    static JComboBox<String> trainBox;
    static JComboBox<String> classBox;

    static JLabel trainInfo;
    static JLabel totalLabel;

    static double finalFare = 0;
    static int tickets = 0;


    public static void main(String[] args) {
        createGUI();
    }


    // Create complete GUI
    static void createGUI() {

        frame = new JFrame("Railway Ticket Booking System");
        frame.setSize(850, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Title
        JLabel title = new JLabel(
            "RAILWAY TICKET BOOKING SYSTEM",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 25));
        main.add(title, BorderLayout.NORTH);


        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));


        // --------------------------------------------------
        // Passenger Details
        // --------------------------------------------------

        JPanel passenger = new JPanel(new GridLayout(4, 2, 10, 10));
        passenger.setBorder(
            BorderFactory.createTitledBorder("Passenger Details")
        );

        nameField = new JTextField();
        ageField = new JTextField();
        phoneField = new JTextField();

        String[] gender = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(gender);

        passenger.add(new JLabel("Passenger Name:"));
        passenger.add(nameField);

        passenger.add(new JLabel("Age:"));
        passenger.add(ageField);

        passenger.add(new JLabel("Gender:"));
        passenger.add(genderBox);

        passenger.add(new JLabel("Phone Number:"));
        passenger.add(phoneField);

        center.add(passenger);
        center.add(Box.createVerticalStrut(10));


        // --------------------------------------------------
        // Train Selection
        // --------------------------------------------------

        JPanel trainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        trainPanel.setBorder(
            BorderFactory.createTitledBorder("Train Selection")
        );

        trainBox = new JComboBox<>();

        // Loop
        for (int i = 0; i < trainName.length; i++) {
            trainBox.addItem(trainNo[i] + " - " + trainName[i]);
        }

        trainInfo = new JLabel();

        trainPanel.add(new JLabel("Select Train:"));
        trainPanel.add(trainBox);

        trainPanel.add(new JLabel("Train Details:"));
        trainPanel.add(trainInfo);

        center.add(trainPanel);
        center.add(Box.createVerticalStrut(10));


        // --------------------------------------------------
        // Booking Details
        // --------------------------------------------------

        JPanel booking = new JPanel(new GridLayout(2, 2, 10, 10));
        booking.setBorder(
            BorderFactory.createTitledBorder("Booking Details")
        );

        String[] classes = {
            "Sleeper",
            "AC 3 Tier",
            "AC 2 Tier",
            "AC First Class"
        };

        classBox = new JComboBox<>(classes);
        ticketField = new JTextField("1");

        booking.add(new JLabel("Travel Class:"));
        booking.add(classBox);

        booking.add(new JLabel("Number of Tickets:"));
        booking.add(ticketField);

        center.add(booking);
        center.add(Box.createVerticalStrut(10));


        // --------------------------------------------------
        // Fare
        // --------------------------------------------------

        JPanel farePanel = new JPanel(new GridLayout(1, 2));
        farePanel.setBorder(
            BorderFactory.createTitledBorder("Fare Details")
        );

        totalLabel = new JLabel("Rs. 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        farePanel.add(new JLabel("Total Fare:"));
        farePanel.add(totalLabel);

        center.add(farePanel);

        main.add(center, BorderLayout.CENTER);


        // --------------------------------------------------
        // Buttons
        // --------------------------------------------------

        JPanel buttons = new JPanel();

        JButton calculateButton = new JButton("Calculate Fare");
        JButton bookButton = new JButton("Book Ticket");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");

        buttons.add(calculateButton);
        buttons.add(bookButton);
        buttons.add(clearButton);
        buttons.add(exitButton);

        main.add(buttons, BorderLayout.SOUTH);


        // Button actions
        trainBox.addActionListener(e -> {
            showTrain();
            finalFare = 0;
            totalLabel.setText("Rs. 0.00");
        });

        classBox.addActionListener(e -> {
            finalFare = 0;
            totalLabel.setText("Rs. 0.00");
        });

        calculateButton.addActionListener(e -> calculateFare());
        bookButton.addActionListener(e -> bookTicket());
        clearButton.addActionListener(e -> clearForm());
        exitButton.addActionListener(e -> System.exit(0));


        showTrain();

        frame.add(main);
        frame.setVisible(true);
    }


    // Display selected train information
    static void showTrain() {

        int i = trainBox.getSelectedIndex();

        trainInfo.setText(
            from[i] + " → " + to[i]
            + " | " + time[i]
            + " | Seats: " + seats[i]
            + " | Rs." + fare[i]
        );
    }


    // Uses Scanner to read number
    static int readNumber(String value) {

        Scanner sc = new Scanner(value);

        if (sc.hasNextInt()) {
            int number = sc.nextInt();
            sc.close();
            return number;
        }

        sc.close();
        return -1;
    }


    // Calculate ticket fare
    static void calculateFare() {

        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Enter passenger name.");
            return;
        }

        int age = readNumber(ageField.getText());
        tickets = readNumber(ticketField.getText());

        if (age <= 0 || age > 120 || tickets <= 0) {
            JOptionPane.showMessageDialog(
                frame,
                "Enter valid age and number of tickets."
            );
            return;
        }

        int i = trainBox.getSelectedIndex();

        // Check seats using if
        if (tickets > seats[i]) {
            JOptionPane.showMessageDialog(
                frame,
                "Only " + seats[i] + " seats are available."
            );
            return;
        }

        finalFare = fare[i] * tickets;

        String selectedClass =
            classBox.getSelectedItem().toString();


        // Switch for class charges
        switch (selectedClass) {

            case "AC 3 Tier":
                finalFare += 500 * tickets;
                break;

            case "AC 2 Tier":
                finalFare += 900 * tickets;
                break;

            case "AC First Class":
                finalFare += 1500 * tickets;
                break;

            default:
                break;
        }


        // Discount using if-else
        if (age < 12) {
            finalFare = finalFare * 0.50;

        } else if (age >= 60) {
            finalFare = finalFare * 0.70;
        }


        // 5% service charge
        finalFare += finalFare * 0.05;

        totalLabel.setText(
            "Rs. " + String.format("%.2f", finalFare)
        );
    }


    // Book ticket
    static void bookTicket() {

        if (finalFare == 0) {
            JOptionPane.showMessageDialog(
                frame,
                "Please calculate fare first."
            );
            return;
        }

        int i = trainBox.getSelectedIndex();


        int choice = JOptionPane.showConfirmDialog(
            frame,
            "Total Fare: Rs. "
            + String.format("%.2f", finalFare)
            + "\nConfirm Booking?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION
        );


        if (choice == JOptionPane.YES_OPTION) {

            // Reduce seats
            seats[i] -= tickets;


            // Generate booking ID
            int number =
                1000 + (int)(Math.random() * 9000);

            String bookingID = "RLY" + number;


            // Ticket
            String ticket =
                "====================================\n"
                + "         RAILWAY TICKET\n"
                + "====================================\n"
                + "Booking ID : " + bookingID + "\n"
                + "Passenger  : " + nameField.getText() + "\n"
                + "Age        : " + ageField.getText() + "\n"
                + "Gender     : " + genderBox.getSelectedItem() + "\n"
                + "Phone      : " + phoneField.getText() + "\n\n"

                + "Train No   : " + trainNo[i] + "\n"
                + "Train      : " + trainName[i] + "\n"
                + "Route      : " + from[i] + " -> " + to[i] + "\n"
                + "Departure  : " + time[i] + "\n"
                + "Class      : " + classBox.getSelectedItem() + "\n"
                + "Tickets    : " + tickets + "\n\n"

                + "Total Fare : Rs. "
                + String.format("%.2f", finalFare) + "\n"

                + "Status     : CONFIRMED\n"
                + "Seats Left : " + seats[i] + "\n"
                + "====================================\n"
                + "        HAVE A SAFE JOURNEY!\n"
                + "====================================";


            JTextArea area = new JTextArea(ticket);
            area.setEditable(false);
            area.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
            );

            JOptionPane.showMessageDialog(
                frame,
                new JScrollPane(area),
                "Ticket Generated",
                JOptionPane.INFORMATION_MESSAGE
            );

            showTrain();

            finalFare = 0;
            totalLabel.setText("Rs. 0.00");
        }
    }


    // Clear all entered data
    static void clearForm() {

        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        ticketField.setText("1");

        genderBox.setSelectedIndex(0);
        trainBox.setSelectedIndex(0);
        classBox.setSelectedIndex(0);

        finalFare = 0;

        totalLabel.setText("Rs. 0.00");

        showTrain();
    }
}