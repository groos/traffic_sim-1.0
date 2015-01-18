package tServer;

import java.util.Observable;

import main.main;
import animator.SwingAnimator;
import data.Agent;
import main.Control;

public final class TimeServerLinked extends Observable implements TimeServer {
  private static final class Node {
    final double waketime;
    final Agent agent;
    Node next;
  
    public Node(double waketime, Agent agent, Node next) {
      this.waketime = waketime;
      this.agent = agent;
      this.next = next;
    }
  }
  private double _currentTime;
  private int _size;
  private Node _head;
  //private SwingAnimator SA = new SwingAnimator(main.builder._painter, "Traffic_Sim", 1024, 768, 1);

  /*
  * Invariant: _head != null
  * Invariant: _head.agent == null
  * Invariant: (_size == 0) iff (_head.next == null)
  */
  public TimeServerLinked() {
    _size = 0;
    _head = new Node(0, null, null);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Node node = _head.next;
    String sep = "";
    while (node != null) {
      sb.append(sep).append("(").append(node.waketime).append(",")
        .append(node.agent).append(")");
      node = node.next;
      sep = ";";
    }
    sb.append("]");
    return (sb.toString());
  }

  public double currentTime() {
    return _currentTime;
  }

  public void enqueue(double waketime, Agent agent)
    throws IllegalArgumentException
  {
    if (waketime < _currentTime){
    	System.out.println("waketime is: " + waketime);
    	System.out.println("current time is: " + _currentTime);
    	System.out.println(agent.toString());
      throw new IllegalArgumentException();
  }
    Node prevElement = _head;
    while ((prevElement.next != null) &&
        (prevElement.next.waketime <= waketime)) {
      prevElement = prevElement.next;
    }
    Node newElement = new Node(waketime, agent, prevElement.next);
    prevElement.next = newElement;
    _size++;
  }

  Agent dequeue()
  {
    if (_size < 1)
      throw new java.util.NoSuchElementException();
    Agent rval = _head.next.agent;
    _head.next = _head.next.next;
    _size--;
    return rval;
  }

  int size() {
    return _size;
  }

  boolean empty() {
    return size() == 0;
  }

  public void run(double duration) {
    double endtime = _currentTime + duration;
    while ((!empty()) && (_head.next.waketime <= endtime)) {
      if ((_currentTime - _head.next.waketime) > 1e-09) { // when would this be satisfied? only if currenttime > next
        super.setChanged();
        super.notifyObservers(); 
      }
      _currentTime = _head.next.waketime;
      dequeue().run();
		Control.SA.update(this, null); 
		Control.A.update(this, null);
    }
    _currentTime = endtime;
  }
}
