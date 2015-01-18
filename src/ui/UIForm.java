package ui;

/**
 * @see UIFormBuilder
 */
public final class UIForm {
  private final String _heading;
  private final Pair[] _form;

  // fields are the prompt displayed to the user (title, year, director)
  // fields are paired with a test
  static final class Pair {
    final String prompt;
    final UIFormTest test;

    Pair(String thePrompt, UIFormTest theTest) {
      prompt = thePrompt;
      test = theTest;
    }
  }
  
  UIForm(String heading, Pair[] menu) {
    _heading = heading;
    _form = menu;
  }
  public int size() {
    return _form.length;
  }
  public String getHeading() {
    return _heading;
  }
  public String getPrompt(int i) {
    return _form[i].prompt;
  }
  public boolean checkInput(int i, String input) {
    if (null == _form[i])
      return true;
    return _form[i].test.run(input);
  }
}
