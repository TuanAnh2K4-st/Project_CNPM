package mapper;

import java.sql.ResultSet;

public interface IRowMapper<T> {
	T maplist(ResultSet rs);
	T mapFindById(ResultSet rs);
}
