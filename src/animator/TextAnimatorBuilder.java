package animator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import data.Car;
import light.Light;
import data.Road;


/** 
 * A class for building Animators.
 */
public class TextAnimatorBuilder implements AnimatorBuilder {
  TextAnimator _animator;
  public TextAnimatorBuilder() {
    _animator = new TextAnimator();
  }
  public Animator getAnimator() {
    if (_animator == null) { throw new IllegalStateException(); }
    Animator returnValue = _animator;
    _animator = null;
    return returnValue;
  }
  public void addLight(Light d, int i, int j) {
    _animator.addLight(d,i,j);
  }
  public void addHorizontalRoad(Road l, int i, int j, boolean eastToWest) {
    _animator.addRoad(l,i,j);
  }
  public void addVerticalRoad(Road l, int i, int j, boolean southToNorth) {
    _animator.addRoad(l,i,j);
  }


  /** Class for drawing the Model. */
  private static class TextAnimator implements Animator {

    /** Triple of a model element <code>x</code> and coordinates. */
    private static class Element<T> {
      T x;
      int i;
      int j;
      Element(T x, int i, int j) {
        this.x = x;
        this.i = i;
        this.j = j;
      }
    }
    
    private List<Element<Road>> _roadElements;
    private List<Element<Light>> _lightElements;
    
    TextAnimator() {
      _roadElements = new ArrayList<Element<Road>>();
      _lightElements = new ArrayList<Element<Light>>();
    }    
    void addLight(Light x, int i, int j) {
      _lightElements.add(new Element<Light>(x,i,j));
    }
    void addRoad(Road x, int i, int j) {
      _roadElements.add(new Element<Road>(x,i,j));
    }
    
    public void dispose() { }
    
    public void update(Observable o, Object arg) {
      for (Element<Light> e : _lightElements) {
        System.out.print("Light at (" + e.i + "," + e.j + "): ");
        if (e.x.getLightState()) {
          System.out.println("Blue");
        } else {
          System.out.println("Yellow");
        }
      }
      for (Element<Road> e : _roadElements) {
        for (Car d : e.x.getCars()) {
          System.out.print("Road at (" + e.i + "," + e.j + "): ");
          System.out.println("Car at " + d.getPosition());
        }
      }
    }
  }
}
