package repository;

public interface IRepository<T, ID> {
    T add(T t);

    void update(T t);

    void delete(ID id);

    T get(ID id);

    boolean exists(ID id);
}