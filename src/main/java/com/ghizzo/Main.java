package com.ghizzo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ghizzo.controller.MowController;
import com.ghizzo.model.exception.IllegalPositionException;


public class Main {

	final static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException {

		MowController mowController = new MowController();

	    JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files", "txt");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(new JFrame("filePickerFrame"));
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
			Path child = chooser.getSelectedFile().toPath();
			if (Files.probeContentType(child) == null || !Files.probeContentType(child).equals("text/plain")) {
				logger.warn("New file {} is not a plain text file", chooser.getSelectedFile().getName());
			} else {
				try {
					mowController.handleLines(Files.readAllLines(child));
				} catch (IllegalPositionException e) {
					logger.warn(e.getMessage());
				}
			}
	    }
	    System.exit(0);
	}

}