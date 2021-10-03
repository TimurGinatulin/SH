package ru.ginatulin.group.models.observers;

public interface Observer {
    void update(AbstractComponentObservers subject, Object arg);
}
