package CustomClasses;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator1 implements Iterator<Integer> {
    private final Iterator<Integer> baseIterator;
    private final Predicate<Integer> filter;

    private Integer nextElement;

    private boolean isNextElementSet;

    public FilteredIterator1(Iterator<Integer> baseIterator, Predicate<Integer> filter) {
        this.baseIterator = baseIterator;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        if(isNextElementSet) {
            return true;
        }
        while(baseIterator.hasNext()) {
            Integer currentElement = baseIterator.next();
            if(filter.test(currentElement)) {
                nextElement = currentElement;
                isNextElementSet = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if(!isNextElementSet && !hasNext()) {
            throw new NoSuchElementException();
        }
        isNextElementSet = false;
        return nextElement;
    }

    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        FilteredIterator1 even = new FilteredIterator1(list.iterator(), e -> e % 2 == 0);
        while(even.hasNext()) {
            System.out.println(even.next());
        }

        FilteredIterator1 odd = new FilteredIterator1(list.iterator(), e -> e % 2 != 0);
        while(odd.hasNext()) {
            System.out.println(odd.next());
        }

    }
}
