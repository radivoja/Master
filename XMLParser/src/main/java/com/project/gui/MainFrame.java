package com.project.gui;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.project.parser.XMLParser;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 7933447493166704041L;
	JButton btModel;
	JButton btDest;
	JTextField tfDest;
	JTextField tfModel;

	public MainFrame() {
		JPanel panelCenter = new JPanel();
		BoxLayout box = new BoxLayout(panelCenter, BoxLayout.Y_AXIS);
		panelCenter.setLayout(box);
		add(panelCenter, BorderLayout.CENTER);

		JPanel panModel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbModel = new JLabel("Putanja do modela: ");
		tfModel = new JTextField("D:\\WorkspacePapyrus\\Freehold\\Freehold.uml");
		btModel = new JButton("Open");
		btModel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfcModel = new JFileChooser();
				int result = jfcModel.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					tfModel.setText(jfcModel.getSelectedFile().getAbsolutePath());
				}
			}
		});
		panModel.add(lbModel);
		panModel.add(tfModel);
		panModel.add(btModel);

		JPanel panDest = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lbDest = new JLabel("Putanja do foldera: ");
		String FILE_DIRECTORY = "C:\\Users\\User\\Desktop\\Generated\\";
		tfDest = new JTextField(FILE_DIRECTORY);
		btDest = new JButton("Open");
		btDest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfcDest = new JFileChooser();
				jfcDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfcDest.setAcceptAllFileFilterUsed(false);
				int result = jfcDest.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					tfDest.setText(jfcDest.getSelectedFile().getAbsolutePath());
				}
			}
		});

		panDest.add(lbDest);
		panDest.add(tfDest);
		panDest.add(btDest);

		JPanel panelGenerate = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btGenerate = new JButton("Generate");
		btGenerate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Successful generation");
					XMLParser.generate(tfModel.getText(), tfDest.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		panelGenerate.add(btGenerate);

		panelCenter.add(panModel);
		panelCenter.add(panDest);
		panelCenter.add(panelGenerate);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

	}

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}

}