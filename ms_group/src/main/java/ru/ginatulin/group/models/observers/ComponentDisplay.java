package ru.ginatulin.group.models.observers;

public class ComponentDisplay implements Observer {
    @Override
    public void update(AbstractComponentObservers subject, Object arg) {
        System.out.println(subject.getTitle() + " change values at: " + arg);
    }
}
