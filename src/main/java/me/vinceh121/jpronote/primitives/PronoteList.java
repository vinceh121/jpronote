package me.vinceh121.jpronote.primitives;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PronoteList<T> extends AbstractPronotePrimitive implements IPronoteList<T> {
	@JsonProperty("V")
	private final List<T> value = new ArrayList<>();

	@Override
	public List<T> getValue() {
		return this.value;
	}

	/**
	 * NOOP for PronoteList
	 */
	@Override
	public void setValue(final Object value) {
	}

	@Override
	public void forEach(final Consumer<? super T> action) {
		this.value.forEach(action);
	}

	@Override
	public int size() {
		return this.value.size();
	}

	@Override
	public boolean isEmpty() {
		return this.value.isEmpty();
	}

	@Override
	public boolean contains(final Object o) {
		return this.value.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return this.value.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.value.toArray();
	}

	@Override
	public <A> A[] toArray(final A[] a) {
		return this.value.toArray(a);
	}

	@Override
	public boolean add(final T e) {
		return this.value.add(e);
	}

	@Override
	public boolean remove(final Object o) {
		return this.value.remove(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return this.value.containsAll(c);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		return this.value.addAll(c);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends T> c) {
		return this.value.addAll(index, c);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return this.value.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return this.value.retainAll(c);
	}

	@Override
	public void replaceAll(final UnaryOperator<T> operator) {
		this.value.replaceAll(operator);
	}

	@Override
	public boolean removeIf(final Predicate<? super T> filter) {
		return this.value.removeIf(filter);
	}

	@Override
	public void sort(final Comparator<? super T> c) {
		this.value.sort(c);
	}

	@Override
	public void clear() {
		this.value.clear();
	}

	@Override
	public boolean equals(final Object o) {
		return this.value.equals(o);
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public T get(final int index) {
		return this.value.get(index);
	}

	@Override
	public T set(final int index, final T element) {
		return this.value.set(index, element);
	}

	@Override
	public void add(final int index, final T element) {
		this.value.add(index, element);
	}

	@Override
	public Stream<T> stream() {
		return this.value.stream();
	}

	@Override
	public T remove(final int index) {
		return this.value.remove(index);
	}

	@Override
	public Stream<T> parallelStream() {
		return this.value.parallelStream();
	}

	@Override
	public int indexOf(final Object o) {
		return this.value.indexOf(o);
	}

	@Override
	public int lastIndexOf(final Object o) {
		return this.value.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return this.value.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(final int index) {
		return this.value.listIterator(index);
	}

	@Override
	public List<T> subList(final int fromIndex, final int toIndex) {
		return this.value.subList(fromIndex, toIndex);
	}

	@Override
	public Spliterator<T> spliterator() {
		return this.value.spliterator();
	}

	@Override
	public String toString() {
		return "PronoteList " + this.value;
	}
}
