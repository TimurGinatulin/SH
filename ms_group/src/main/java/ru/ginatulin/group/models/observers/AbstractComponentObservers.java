package ru.ginatulin.group.models.observers;

import java.util.ArrayList;
import java.util.List;

public class AbstractComponentObservers {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }
    public String getTitle(){
        return null;
    }

    protected void notify(Object arg) {
        observers.forEach(observer -> observer.update(this, arg));
    }
}
