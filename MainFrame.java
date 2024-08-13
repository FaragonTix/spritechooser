package sprite_chooser;


import javax.swing.*;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.Graphics2D;

public class MainFrame {
	private static Map<String, String> folderPositions = new HashMap<>();
	private static ArrayList<String> poseList = new ArrayList<>();
    private static int currentPoseIndex = 0;
    private static ArrayList<String> imagePaths = new ArrayList<>();
    private static int currentImageIndex = 0;
    private static JLabel imgNameLabel;
    private static JLabel main_label;
    private static JLabel next_label;
    private static JLabel prev_label;
    private static ArrayList<String> replicaFolders = new ArrayList<>();
    private static ArrayList<String> replicas = new ArrayList<>();
    private static int currentReplicaIndex = -1;
    private static final ButtonGroup buttonGroup = new ButtonGroup();
    private static String selectedRadioButtonValue = "center";
    private static JRadioButton rdbtnLeft = new JRadioButton("Left");
    private static JRadioButton rdbtnCenter = new JRadioButton("Center");
    private static JRadioButton rdbtnRight = new JRadioButton("Right");
    private static JLabel lblNewLabel = new JLabel();
    private static String mainFolder="src/sprite_chooser/sprites/";

    public static void main(String[] args) {
    	String filePathO = "src/output.txt";
    	String filePathF = "src/final.txt";
    	String filePathP = "src/poses.txt";
    	//readPosesFromFile(filePathP);
        try {
            
            FileWriter writer = new FileWriter(filePathO, false);
            readPosesFromFile(filePathP);
            
            writer.write(""); 

            
            writer.close();
         
            FileWriter writerF = new FileWriter(filePathF, false);

            
            writerF.write(""); 

            
            writerF.close();

            System.out.println("File content has been cleared on program startup.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        readAndLoadReplicas();
		/*
		 * if (replicaFolders.size() > 0) { loadImagesInFolder(replicaFolders.get(0));
		 * replicaFolders.remove(0); }
		 */
        System.out.println(replicaFolders);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("MainFrame");
                
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900, 720);
                frame.getContentPane().setLayout(null);

                main_label = new JLabel("New label");
                main_label.setHorizontalAlignment(SwingConstants.CENTER);
                main_label.setBounds(10, 282, 407, 174);
                frame.getContentPane().add(main_label);

                next_label = new JLabel("New label");
                next_label.setVerticalAlignment(SwingConstants.TOP);
                next_label.setHorizontalAlignment(SwingConstants.CENTER);
                next_label.setBounds(10, 466, 407, 174);
                frame.getContentPane().add(next_label);

                prev_label = new JLabel();
                prev_label.setVerticalAlignment(SwingConstants.TOP);
                prev_label.setHorizontalAlignment(SwingConstants.CENTER);
                prev_label.setBounds(10, 113, 407, 174);
                frame.getContentPane().add(prev_label);

                
                lblNewLabel.setBounds(443, 10, 300, 600);
                frame.getContentPane().add(lblNewLabel);

                JButton btnNext = new JButton("Next");
                btnNext.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        currentImageIndex = (currentImageIndex + 1) % imagePaths.size();
                        ImageIcon newIcon = createImageIcon(currentImageIndex);
                        lblNewLabel.setIcon(newIcon);
                        updateImageNameLabel();
                    }
                });
                btnNext.setBounds(654, 635, 85, 21);
                frame.getContentPane().add(btnNext);

                JButton btnPrev = new JButton("Prev");
                btnPrev.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        currentImageIndex = (currentImageIndex - 1 + imagePaths.size()) % imagePaths.size();
                        ImageIcon newIcon = createImageIcon(currentImageIndex);
                        lblNewLabel.setIcon(newIcon);
                        updateImageNameLabel();
                    }
                });
                btnPrev.setBounds(455, 635, 85, 21);
                frame.getContentPane().add(btnPrev);

                imgNameLabel = new JLabel("Img_name");
                imgNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imgNameLabel.setBounds(550, 639, 94, 13);
                frame.getContentPane().add(imgNameLabel);
                if (replicaFolders.size() > 0) {
                    loadImagesInFolder(replicaFolders.get(0));
                    replicaFolders.remove(0);
                }
                JButton btnOK = new JButton("OK");
                btnOK.setBounds(765, 616, 85, 40);
                frame.getContentPane().add(btnOK);

                JRadioButton rdbtnLeft = new JRadioButton("Left");
                buttonGroup.add(rdbtnLeft);
                rdbtnLeft.setBounds(747, 435, 103, 21);
                frame.getContentPane().add(rdbtnLeft);

                JRadioButton rdbtnCenter = new JRadioButton("Center");
                rdbtnCenter.setSelected(true);
                buttonGroup.add(rdbtnCenter);
                rdbtnCenter.setBounds(747, 462, 103, 21);
                frame.getContentPane().add(rdbtnCenter);

                JRadioButton rdbtnRight = new JRadioButton("Right");
                buttonGroup.add(rdbtnRight);
                rdbtnRight.setBounds(747, 494, 103, 21);
                frame.getContentPane().add(rdbtnRight);
                
                JButton btnNewButton = new JButton("Save");
                btnNewButton.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		//ffff
                		String inputFile = "src/text.txt";
                        String input2File = "src/output.txt";
                        String outputFile = "src/final.txt";

                        try {
                            modifyFile(inputFile, input2File, outputFile);
                            System.out.println("File modification completed successfully.");
                            JOptionPane.showMessageDialog(null,
                                    "Screenplay was saved.",
                                    "Sucess",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    
                	}
                });
                btnNewButton.setBounds(765, 100, 85, 21);
                frame.getContentPane().add(btnNewButton);
                
                JButton btnNewButton_1 = new JButton("Next pose");
                btnNewButton_1.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		displayNextPoseImage();
                	}
                });
                btnNewButton_1.setBounds(753, 386, 85, 21);
                frame.getContentPane().add(btnNewButton_1);

                btnOK.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {displayNextPoseImage();
                    	// Write to the output file
                    	// Get the selected radio button value
                        if (rdbtnLeft.isSelected()) {
                            selectedRadioButtonValue = "left";
                        } else if (rdbtnCenter.isSelected()) {
                            selectedRadioButtonValue = "center";
                        } else if (rdbtnRight.isSelected()) {
                            selectedRadioButtonValue = "right";
                        } else {
                            selectedRadioButtonValue = "center"; // Set a default value if none is selected.
                        }
                        if (currentReplicaIndex < replicas.size()) {
                        writeOutputToFile();}
                        if (currentReplicaIndex < replicaFolders.size()) {
                            String selectedFolder = replicaFolders.get(currentReplicaIndex);
                            loadImagesInFolder(selectedFolder);
                        }
                        folderPositions.put(replicaFolders.get(currentReplicaIndex), selectedRadioButtonValue);
                        

                        

                        displayNextReplica();
                    }
                });

                frame.setVisible(true);
                updateImageNameLabel();
                displayNextReplica();
            }
        });
    }
    
   
    
    
    
    private static void displayNextPoseImage() {
        if (poseList.isEmpty() || imagePaths.isEmpty()) {
            return;
        }

        int initialPoseIndex = currentPoseIndex;
        boolean foundImage = false;

        do {
            currentPoseIndex = (currentPoseIndex + 1) % poseList.size();
            String nextPose = poseList.get(currentPoseIndex);

            System.out.println("Attempting to find image for pose: " + nextPose);

            // Find the next image with the next pose as the second word in its name
            for (String imagePath : imagePaths) {
                String[] imageNameParts = new File(imagePath).getName().split("\\s+");
                if (imageNameParts.length > 1 && imageNameParts[1].equals(nextPose)) {
                    // Display the image
                    ImageIcon newIcon = createImageIcon(imagePaths.indexOf(imagePath));
                    lblNewLabel.setIcon(newIcon);

                    // Update the image name label
                    currentImageIndex = imagePaths.indexOf(imagePath);
                    updateImageNameLabel();

                    foundImage = true;
                    System.out.println("Image found: " + imagePath);
                    return; // Break out of the loop once the image is found
                }
            }
        } while (currentPoseIndex != initialPoseIndex);

        // If no image with the next pose is found, print a message
        if (!foundImage) {
            System.out.println("No image found for the next pose: " + poseList.get(currentPoseIndex));
        }
    }




    private static void setSelectedRadioButton(String position) {
        switch (position) {
            case "left":
                buttonGroup.setSelected(rdbtnLeft.getModel(), true);
                break;
            case "center":
                buttonGroup.setSelected(rdbtnCenter.getModel(), true);
                break;
            case "right":
                buttonGroup.setSelected(rdbtnRight.getModel(), true);
                break;
        }
    }




    private static void loadImagesInFolder(String folderName) {
        String folderPath = mainFolder + folderName;

        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            imagePaths.clear();
            for (File file : files) {
                if (file.isFile()) {
                    imagePaths.add(file.getAbsolutePath());
                }
            }
        }
        currentImageIndex = 0;
        ImageIcon newIcon = createImageIcon(currentImageIndex);
        lblNewLabel.setIcon(newIcon);
        updateImageNameLabel();
        
    }

    private static ImageIcon createImageIcon(int index) {
        if (index >= 0 && index < imagePaths.size()) {
            String imagePath = imagePaths.get(index);
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(300, 600, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        }
        return new ImageIcon();
    }
    public static void modifyFile(String inputFile, String input2File, String outputFile) throws IOException {
        ArrayList<String> nameAndPos = readNameToPosition(input2File);
        int curr=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("show")) {
                    String[] words = nameAndPos.get(curr).split("@");
                    if (words.length >= 2) {
                        String name = words[0];
                        String position = words[1];
                        if (position != null) {
                            writer.write("show " + name + " at " + position);
                           
                        } else {
                            writer.write(line); // Handle the case when the name is not found in input2.txt
                        }
                    }
                } else if (line.startsWith("hide")) {
                	String[] words = nameAndPos.get(curr).split("@");
                    
                        String name = words[0];
                        writer.write("hide " + name);
                        curr++;
                    
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        }
    }

    public static ArrayList<String> readNameToPosition(String input2File) throws IOException {
    	ArrayList<String> nameAndPosition = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader(input2File)) ) {
            String line;
            while ((line = reader.readLine()) != null) {
                    nameAndPosition.add(line);
                }
            }
        

        return nameAndPosition;
    }
    

    private static void updateImageNameLabel() {
        if (currentImageIndex >= 0 && currentImageIndex < imagePaths.size()) {
            String imagePath = imagePaths.get(currentImageIndex);
            String imageName = new File(imagePath).getName();
            String imageNameWithoutExtension = imageName.substring(0, imageName.lastIndexOf('.'));
            imgNameLabel.setText(imageNameWithoutExtension);
        }
    }
    
    

    private static void readAndLoadReplicas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/text.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("show")) {
                    replicaFolders.add(line.replace("show ", ""));
                    String nextLine = reader.readLine();
                    replicas.add(nextLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayNextReplica() {
        if (replicas.isEmpty()) {
            return;
        }

        currentReplicaIndex++;
        if (currentReplicaIndex < replicas.size()) {
            String currentReplica = replicas.get(currentReplicaIndex);

            main_label.setText(currentReplica.replace("\\n", "\n"));
            updateNextLabel();
            updatePrevLabel();
        } else {
            main_label.setText("No more replicas.");
            next_label.setText("");
            prev_label.setText("");
        }
    }
    private static void readPosesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                poseList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void updateNextLabel() {
        if (currentReplicaIndex < replicas.size()) {
            int nextIndex = currentReplicaIndex + 1;
            int secondNextIndex = currentReplicaIndex + 2;
            int thirdNextIndex = currentReplicaIndex + 3;

            String nextLabelContent = "";

            if (nextIndex < replicas.size()) {
                nextLabelContent += "<html>" + replicas.get(nextIndex).replace("\\n", "\n");
            }

            if (secondNextIndex < replicas.size()) {
                nextLabelContent += "<div>" + replicas.get(secondNextIndex).replace("\\n", "\n") + "<html>";
            }

            if (thirdNextIndex < replicas.size()) {
                nextLabelContent += "<div>" + replicas.get(thirdNextIndex).replace("\\n", "\n") + "<html>";
            }

            next_label.setText(nextLabelContent);
        }
    }

    private static void updatePrevLabel() {
        if (currentReplicaIndex > 0) {
            int prevIndex = currentReplicaIndex - 1;
            int secondPrevIndex = currentReplicaIndex - 2;
            int thirdPrevIndex = currentReplicaIndex - 3;

            String prevLabelContent = "";

            prevLabelContent += replicas.get(prevIndex).replace("\\n", "\n");

            if (secondPrevIndex >= 0) {
                prevLabelContent = "<html>" + replicas.get(secondPrevIndex).replace("\\n", "\n") + "<div>"
                        + prevLabelContent + "<html>";
            }

            if (thirdPrevIndex >= 0) {
                prevLabelContent = "<html>" + replicas.get(thirdPrevIndex).replace("\\n", "\n") + "<div>"
                        + prevLabelContent + "<html>";
            }

            prev_label.setText(prevLabelContent);
        } else {
            prev_label.setText(""); // Empty for the first replica
        }
    }

    // Method to write output to the "output.txt" file
    private static void writeOutputToFile() {
        try {
            File outputFile = new File("src/output.txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }

            FileWriter writer = new FileWriter(outputFile, true); // Append mode

            // Write the current image name and selected radio button value
            String imagePath = imagePaths.get(currentImageIndex);
            String imageName = new File(imagePath).getName();
            String imageNameWithoutExtension = imageName.substring(0, imageName.lastIndexOf('.'));
            System.out.println(currentImageIndex);
            writer.write(imageNameWithoutExtension + "@" + selectedRadioButtonValue + "\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
