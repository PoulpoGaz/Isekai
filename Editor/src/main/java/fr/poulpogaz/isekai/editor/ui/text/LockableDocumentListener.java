package fr.poulpogaz.isekai.editor.ui.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class LockableDocumentListener implements DocumentListener {

    private boolean lock = false;

    public LockableDocumentListener() {

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            if (!isLock()) {
                insertUpdateImpl(e);
            }
        } finally {
            unlock();
        }
    }

    public void insertUpdateImpl(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            if (!isLock()) {
                removeUpdateImpl(e);
            }
        } finally {
            unlock();
        }
    }

    public void removeUpdateImpl(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            if (!isLock()) {
                changeUpdateImpl(e);
            }
        } finally {
            unlock();
        }
    }

    public void changeUpdateImpl(DocumentEvent e) {

    }

    public synchronized void lock() {
        lock = true;
    }

    public synchronized void unlock() {
        lock = false;
    }

    public synchronized boolean isLock() {
        return lock;
    }
}