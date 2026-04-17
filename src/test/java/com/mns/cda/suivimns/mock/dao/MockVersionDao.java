package com.mns.cda.suivimns.mock.dao;

import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.model.Version;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockVersionDao implements VersionDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Version> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Version> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Version> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Version getOne(Integer integer) {
        return null;
    }

    @Override
    public Version getById(Integer integer) {
        return null;
    }

    @Override
    public Version getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Version> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Version> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Version> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Version> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Version> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Version> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Version, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Version> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Version> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Version> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Version> findAll() {
        return List.of();
    }

    @Override
    public List<Version> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Version entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Version> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Version> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Version> findAll(Pageable pageable) {
        return null;
    }
}
