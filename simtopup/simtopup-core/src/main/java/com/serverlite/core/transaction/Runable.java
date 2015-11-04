package com.serverlite.core.transaction;

@FunctionalInterface
public interface Runable<T> {
	T run( );
}
