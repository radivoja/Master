package com.project.gui;

import java.awt.BorderLayout;

import java.awt.FlowLayout;


import javax.swing.*;

import com.project.parser.XMLParser;

public class MainFrame extends JFrame {

	private static final String BUTTON_NAME = "Open";
	private static final String MODEL_NAME = "D:\\WorkspacePapyrus\\Freehold\\Freehold.uml";
	private static final String FILE_DIRECTORY = "C:\\Users\\User\\Desktop\\Generated\\";
	private final JTextField tfDest = new JTextField(FILE_DIRECTORY, 30);
	private final JTextField tfModel =  new JTextField(MODEL_NAME, 30);

	public MainFrame() {
		JPanel panelCenter = new JPanel();
		BoxLayout box = new BoxLayout(panelCenter, BoxLayout.Y_AXIS);
		panelCenter.setLayout(box);
		add(panelCenter, BorderLayout.CENTER);

		JPanel panModel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton btModel = new JButton(BUTTON_NAME);

		attachActionListener(btModel, JFileChooser.FILES_ONLY);
		panModel.add(new JLabel("Model path: "));
		panModel.add(tfModel);
		panModel.add(btModel);

		JPanel panDest = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JButton btDest = new JButton("Open");
		attachActionListener(btDest, JFileChooser.DIRECTORIES_ONLY);

		panDest.add(new JLabel("Folder path: "));
		panDest.add(tfDest);
		panDest.add(btDest);

		JPanel panelGenerate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btGenerate = new JButton("Generate");

		// Some random value different from: 0 - JFileChooser.FILES_ONLY or 1 - JFileChooser.DIRECTORIES_ONLY
		attachActionListener(btGenerate,10 );

		panelGenerate.add(btGenerate);

		panelCenter.add(panModel);
		panelCenter.add(panDest);
		panelCenter.add(panelGenerate);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void attachActionListener(JButton jButton, int mode) {
		jButton.addActionListener(e -> {
			try {
				switch (mode) {
					case JFileChooser.FILES_ONLY -> {
						JFileChooser jfcModel = new JFileChooser();
						int result = jfcModel.showOpenDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
							tfModel.setText(jfcModel.getSelectedFile().getAbsolutePath());
						}
					}
					case JFileChooser.DIRECTORIES_ONLY -> {
						JFileChooser jfcDest = new JFileChooser();
						jfcDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jfcDest.setAcceptAllFileFilterUsed(false);
						int result = jfcDest.showOpenDialog(null);
						if (result == JFileChooser.APPROVE_OPTION) {
							tfDest.setText(jfcDest.getSelectedFile().getAbsolutePath());
						}
					}
					default -> {
						System.out.println("Successful generation");
						XMLParser.generate(tfModel.getText(), tfDest.getText());
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
	}

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}

}