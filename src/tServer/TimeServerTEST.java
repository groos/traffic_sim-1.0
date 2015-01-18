package tServer;
import data.Agent;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TimeServerTEST extends TestCase {
  //TimeServerQueue q = new TimeServerQueue();
  TimeServerLinked q = new TimeServerLinked();

  public TimeServerTEST(String name) {
    super(name);
  }

  public void testThatEmptySizeIsZero() {
    Assert.assertEquals(0, q.size());
  }

  public void testThatDequeueOnEmptyThrowsIndexOutOfBoundsException() {
    boolean exceptionOccurred = false;

    try {
      Agent o = q.dequeue();
    } catch (java.util.NoSuchElementException e) {
      exceptionOccurred = true;
    }

    Assert.assertTrue(exceptionOccurred);
  }

  public void testThatEnqueueFollowedByDequeueReturnsSameReference() {
    class TestThatEnqueueFollowedByDequeueReturnsSameReference
      implements Agent
    {
      public void run() {}
    }

    Agent x1 = new TestThatEnqueueFollowedByDequeueReturnsSameReference();
    q.enqueue(0, x1);
    Assert.assertSame(x1, q.dequeue());
    Assert.assertEquals(0, q.size());
  }

  public void testThatElementsAreInsertedInOrder() {
    class TestThatElementsAreInsertedInOrder implements Agent {
      public void run() {}
    }

    Agent x1 = new TestThatElementsAreInsertedInOrder();
    Agent x2 = new TestThatElementsAreInsertedInOrder();
    q.enqueue(0, x2);
    q.enqueue(1, x1);
    Assert.assertSame(x2, q.dequeue());
    Assert.assertSame(x1, q.dequeue());
    q.enqueue(1, x1);
    q.enqueue(0, x2);
    Assert.assertSame(x2, q.dequeue());
    Assert.assertSame(x1, q.dequeue());
    q.enqueue(0, x1);
    q.enqueue(0, x2);
    Assert.assertSame(x1, q.dequeue());
    Assert.assertSame(x2, q.dequeue());
    q.enqueue(0, x2);
    q.enqueue(0, x1);
    Assert.assertSame(x2, q.dequeue());
    Assert.assertSame(x1, q.dequeue());
  }

  public void testToString() {
    class TestToString implements Agent {
      public void run() {}
      public String toString() { return "x"; }
    }

    q.enqueue(0, new TestToString());
    q.enqueue(1, new TestToString());
    Assert.assertEquals("[(0.0,x);(1.0,x)]", q.toString());
  }

}
