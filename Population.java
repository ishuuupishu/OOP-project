import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

// enumeration for gender
enum Gender {
  MALE, FEMALE
}

// population class
class Population implements Serializable {
  private String name;
  private int age;
  private Gender gender;
  private String nid;
  private String religion;

  // constructor
  public Population(String name, int age, Gender gender, String nid, String religion) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.nid = nid;
    this.religion = religion;

  }

  // getters
  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public Gender getGender() {
    return gender;
  }

  public String getNid() {
    return nid;
  }

  public String getReligion() {
    return religion;
  }

  public void add(Population populations) {
  }
}

// GUI class
class PopulationGUI extends JFrame implements ActionListener {
  private JTextField nameField;
  private JTextField ageField;
  private JTextField nidField;
  private JTextField religionField;
  private JRadioButton maleButton;
  private JRadioButton femaleButton;
  private JButton saveButton;
  private JButton viewButton;
  private ArrayList<Population> populations;

  // constructor
  public PopulationGUI() {
    // create GUI components
    nameField = new JTextField(12);
    ageField = new JTextField(12);
    nidField = new JTextField(12);
    religionField = new JTextField(12);
    maleButton = new JRadioButton("Male");
    femaleButton = new JRadioButton("Female");
    saveButton = new JButton("Save");
    viewButton = new JButton("View");

    // create gender button group
    ButtonGroup genderGroup = new ButtonGroup();
    genderGroup.add(maleButton);
    genderGroup.add(femaleButton);

    // create panel for input fields
    JPanel inputPanel = new JPanel();
    inputPanel.add(new JLabel("Name:"));
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("Age:"));
    inputPanel.add(ageField);
    inputPanel.add(new JLabel("Nid:"));
    inputPanel.add(nidField);
    inputPanel.add(new JLabel("Religion:"));
    inputPanel.add(religionField);
    inputPanel.add(maleButton);
    inputPanel.add(femaleButton);

    // create panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(saveButton);
    buttonPanel.add(viewButton);

    // set layout and add panels to frame
    setLayout(new BorderLayout());
    add(inputPanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);

    // set action listeners for buttons
    saveButton.addActionListener(this);
    viewButton.addActionListener(this);

    // initialize array list for population
    populations = new ArrayList<Population>();
  }

  // action performed event handler
  public void actionPerformed(ActionEvent event) {
    // save button clicked
    if (event.getSource() == saveButton) {
      // get input fields
      String name = nameField.getText();
      int age = Integer.parseInt(ageField.getText());
      String nid = nidField.getText();
      String religion = religionField.getText();
      Gender gender = maleButton.isSelected() ? Gender.MALE : Gender.FEMALE;

      // create new population object
      Population population = new Population(name, age, gende, nid, religion);
      // add population to array list
      populations.add(population);

      // clear input fields
      nameField.setText("");
      ageField.setText("");
      nidField.setText("");
      religionField.setText("");
      maleButton.setSelected(false);
      femaleButton.setSelected(false);
    }
    // view button clicked
    else if (event.getSource() == viewButton) {
      // serialize array list of populations to file
      try {
        FileOutputStream fileOut = new FileOutputStream("population.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(populations);
        out.close();
        fileOut.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      // deserialize array list of populayions from file
      try {
        FileInputStream fileIn = new FileInputStream("population.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        populations = (ArrayList<Population>) in.readObject();
        in.close();
        fileIn.close();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }

      // create data for JTable
      String[][] data = new String[populations.size()][5];
      for (int i = 0; i < populations.size(); i++) {
        Population p = populations.get(i);
        data[i][0] = p.getName();
        data[i][1] = Integer.toString(p.getAge());
        data[i][3] = p.getGender() == Gender.MALE ? "Male" : "Female";
        data[i][4] = p.getnid();
        data[i][5] = p.getreligion();
      }

      // create column names for JTable
      String[] columnNames = { "Name", "Age", "Gender", "Nid", "Religion" };

      // create JTable with data and column names
      JTable table = new JTable(data, columnNames);

      // add JTable to scroll pane
      JScrollPane scrollPane = new JScrollPane(table);

      // add scroll pane to frame and show
      add(scrollPane, BorderLayout.CENTER);
      setVisible(true);
    }
  }

  public static void main(String[] args) {
    PopulationGUI gui = new PopulationGUI();
    gui.setTitle("Census Paper");
    gui.setSize(700, 600);
    gui.setLocationRelativeTo(null);
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setVisible(true);
  }
}