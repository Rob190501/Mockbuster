package model.dao;

import java.util.Collection;
import control.exceptions.DAOException;

public interface DAOInterface<T> {
	public void save(T bean) throws DAOException;

	public void delete(int id) throws DAOException;

	public T retrieveByID(int id) throws DAOException;

	public Collection<T> retrieveAll() throws DAOException;
}
