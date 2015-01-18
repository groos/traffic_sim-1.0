package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public final class TextUI implements UI {
  final BufferedReader _in;
  final PrintStream _out;

  public TextUI() {
    _in = new BufferedReader(new InputStreamReader(System.in));
    _out = System.out;
  }

  public void displayMessage(String message) {
    _out.println(message);
  }

  public void displayError(String message) {
    _out.println(message);
  }

  public String getResponse() {
    String result;
    try {
      result = _in.readLine();
    } catch (IOException e) {
      throw new IllegalArgumentException(); // re-throw UIError
    }
    if (result == null) {
      throw new IllegalArgumentException(); // input closed
    }
    return result;
  }

  public void processMenu(UIMenu menu) {
    _out.println(menu.getHeading());
    _out.println("Enter choice by number:");

    for (int i = 1; i < menu.size(); i++) {
      _out.println("  " + i + ". " + menu.getPrompt(i));
    }

    String response = getResponse();
    int selection;
    try {
      selection = Integer.parseInt(response, 10);
      if ((selection < 0) || (selection >= menu.size()))
        selection = 0;
    } catch (NumberFormatException e) {
      selection = 0;
    }

    menu.runAction(selection);
  }

  public String[] processForm(UIForm form) {
    // TODO
	  
	  _out.println(form.getHeading() + ':');
	  String [] answers = new String [form.size()];
	  
	  for (int i = 0; i < form.size(); i++){
		  _out.println(form.getPrompt(i));
		  
		  answers[i] = getResponse();
		  
		  while (!form.checkInput(i, answers[i])){
			  System.out.println("Invalid entry. Try Again.");
			  answers[i] = getResponse();
		  }
	  }
	  
    return answers;
  }
}
