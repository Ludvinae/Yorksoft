package com.mns.cda.suivimns.mock.dao;

import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.model.SoftwareType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockSoftwareTypeDao implements SoftwareTypeDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends SoftwareType> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends SoftwareType> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<SoftwareType> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public SoftwareType getOne(Integer integer) {
        return null;
    }

    @Override
    public SoftwareType getById(Integer integer) {
        return null;
    }

    @Override
    public SoftwareType getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends SoftwareType> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SoftwareType> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends SoftwareType> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends SoftwareType> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SoftwareType> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SoftwareType> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SoftwareType, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends SoftwareType> S save(S entity) {
        return null;
    }

    @Override
    public <S extends SoftwareType> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<SoftwareType> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<SoftwareType> findAll() {
        return List.of();
    }

    @Override
    public List<SoftwareType> findAllById(Iterable<Integer> integers) {
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
    public void delete(SoftwareType entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends SoftwareType> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SoftwareType> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<SoftwareType> findAll(Pageable pageable) {
        return null;
    }
}
