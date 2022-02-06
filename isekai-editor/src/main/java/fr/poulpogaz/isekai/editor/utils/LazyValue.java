package fr.poulpogaz.isekai.editor.utils;

public abstract class LazyValue<E> {

    private E value;
    private boolean created = false;

    public E get() {
        if (!created) {
            try {
                value = create();
            } catch (Throwable t) {
                t.printStackTrace();
                value = null;
            }

            created = true;
        }

        return value;
    }

    protected abstract E create() throws Throwable;
}
