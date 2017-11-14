package Controller;

import Model.FeatureLayout;
import Model.MachineLearning;

import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import Model.Features.CartesianFeature;
import Model.Features.EnumFeature;

import Model.Storage;

import javax.swing.*;
import java.util.ArrayList;

public class FeatureController {
    private String key; //Problem Key in HashMap
    private MachineLearning machineLearning;
    private Storage storage; //HashMap Reference
    private State state; //Indicates edit or add
    private ProblemWindowController control;

    public enum State {
        editProblem,
        addProblem
    }

    /**
     * @param machineLearning Reference to Model
     */
    public FeatureController(ProblemWindowController pwc, String state, String key) {
        this.control = pwc;
    	this.machineLearning = pwc.getMachine();
    	this.key = key;
        this.storage = machineLearning.getStorage();
        if (state.toLowerCase().equals("add")) {
            this.state = State.addProblem;
        } else {
            this.state = State.editProblem;
        }
    }

    public void initialize(DefaultListModel<GenericFeature> listModel) {
        if (state == State.addProblem) {
            instantiateFeatures(listModel);
        }
    }

    public State getState() {
        return state;
    }

    /**
     * instantiateFeatures method called upon constructing the AlternateWindow it's purpose is to
     * get user input for 'problems' to be learned with MachineLearning. As the user enters each
     * feature they are added to the DefaultListModel, upon completion they're 'learned' by
     * MachineLearning
     * @param listModel gui list to display features.
     */
    private void instantiateFeatures(DefaultListModel<GenericFeature> listModel) {
        GenericFeature feature = null;
        for (FeatureLayout featureLayout : machineLearning.getFeatureLayout()) {
            if (featureLayout.getFeatureType() == FeatureLayout.FeatureType.CartesianFeature) {
                feature = cartesianFeatureWindow(featureLayout.getName());
            } else if (featureLayout.getFeatureType() == FeatureLayout.FeatureType.IntegerFeature) {
                feature = integerFeatureWindow(featureLayout.getName());
            } else if (featureLayout.getFeatureType() == FeatureLayout.FeatureType.DiscreteFeature) {
                feature = enumFeatureWindow(featureLayout.getName());
            }
            if (feature != null) {
                listModel.addElement(feature);
            }
        }
    }

    /**
     * editFeature gets the selected feature from the JList and swaps it with the user's updated feature
     * @param list
     * @param listModel
     */
    public void editFeature(JList list, DefaultListModel<GenericFeature> listModel) {
        int index = list.getSelectedIndex();
        GenericFeature feature = listModel.getElementAt(index);
        if (feature instanceof CartesianFeature) {
            feature = cartesianFeatureWindow("fix this");
        } else if (feature instanceof EnumFeature) {
            feature = enumFeatureWindow("fix this");
        } else if (feature instanceof IntegerFeature) {
            feature = integerFeatureWindow("fix this");
        }

        if (feature != null) {
            /*Replace the current feature with updated*/
            listModel.removeElementAt(index);
            listModel.add(index, feature); //Replace in gui
        }
    }

    /**
     * cartesianFeatureWindow creates a new window which represents an cartesian feature, where the user
     * can enter the value. Creates a new CartesianFeature and returns it.
     * @return CartesianFeature
     */
    private CartesianFeature cartesianFeatureWindow(String name) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
            "X Coordinate:", xField,
            "Y Coordinate:", yField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + "(Cartesian):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new CartesianFeature(name, Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText()));
        }
        return null;
    }

    /**
     * enumFeatureWindow creates a new window which represents an enum feature, where the user
     * can enter the value. Creates a new EnumFeature and returns it.
     * @return EnumFeature
     */
    private EnumFeature enumFeatureWindow(String name) {
        JTextField enumField = new JTextField();
        Object[] message = {
            "Enumerated Value:", enumField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + "(Discrete):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new EnumFeature(name, enumField.getText());
        }
        return null;
    }

    /**
     * integerFeatureWindow creates a new window which represents an integer feature, where the user
     * can enter the value. Creates a new IntegerFeature and returns it.
     * @return IntegerFeature
     */
    private IntegerFeature integerFeatureWindow(String name) {
        JTextField intField = new JTextField();
        Object[] message = {
            "Integer Value:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + "(Integer):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new IntegerFeature(name, Integer.valueOf(intField.getText()));
        }
        return null;
    }

    /**
     * learnInstance calls MachineLearning learn method with an ArrayList that represents
     * the instance's features.
     * @param listModel guiList which stores all the new features.
     */
    public void learnInstance(DefaultListModel<GenericFeature> listModel) {
        ArrayList<GenericFeature> newInstance = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i ++) {
            newInstance.add(listModel.get(i));
        }
        System.out.println(key);
        machineLearning.learn(key, newInstance);
        control.getWindow().newScreen();
    }

    /**
     * updateInstance updates an instance stored in storage with the new instance which was
     * modified by the user.
     * @param listModel guiList which stores all the new features.
     */
    public void updateInstance(DefaultListModel<GenericFeature> listModel) {
        storage.remove(key);
        learnInstance(listModel);
        
    }
}
