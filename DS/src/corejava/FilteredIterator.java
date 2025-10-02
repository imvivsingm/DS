package corejava;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator implements Iterator<Integer> {
   private final Iterator<Integer> baseIterator;
   private final Predicate<Integer> filter;
   private Integer nextElement;
   private boolean nextElementSet = false;
   public FilteredIterator(Iterator<Integer> baseIterator, Predicate<Integer> filter) {
       this.baseIterator = baseIterator;
       this.filter = filter;
   }

    @Override
    public boolean hasNext() {
       if(nextElementSet) {
           return true;
       }
       while (baseIterator.hasNext()) {
           Integer elementCheck = baseIterator.next();

           if (filter.test(elementCheck)) {
               nextElement = elementCheck;
               nextElementSet = true;
               return true;
           }
       }
       return false;
    }

    @Override
    public Integer next() {
       if(!nextElementSet && !hasNext()) {
           throw new NoSuchElementException();
       }
       nextElementSet = false;
       return nextElement;
    }
}
