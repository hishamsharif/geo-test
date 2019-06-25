package com.nwsstechsol.geo.util.hibernate;

import java.lang.reflect.Field;
import java.sql.Connection;

import com.zaxxer.hikari.pool.HikariProxyConnection;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom ConnectionFinder implementation.
 * 
 * This implementation attempts to retrieve the OracleConnection
 * 
 *
 * @author hisham
 */
@Slf4j
public class OracleHikariConnectionFinder implements org.geolatte.geom.codec.db.oracle.ConnectionFinder {

	private static final long serialVersionUID = -2971309073206731424L;

	@Override
	public Connection find(Connection con) {
		Field delegate = null;
		try {
			delegate = ((HikariProxyConnection) con).getClass().getSuperclass().getDeclaredField("delegate");
		} catch (NoSuchFieldException | SecurityException e) {
			log.error("error while reflectively accessing hikari proxy connection class ::" , e);
		}
		delegate.setAccessible(true);
		try {
			return (Connection) delegate.get(con);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("error while getting hikari proxy conn ::" , e);
		}
		return con;
	}

}