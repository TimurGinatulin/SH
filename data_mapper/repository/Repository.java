package hw.data_mapper.repository;

public interface Repository<T> {
    T findById(Integer id);

    Integer save(T entity);

    T update(T entity);

    T delete(T entity);
}
