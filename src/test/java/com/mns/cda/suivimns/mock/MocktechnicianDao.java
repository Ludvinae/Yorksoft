package com.mns.cda.suivimns.mock;

import com.mns.cda.suivimns.dao.TechnicianDao;
import com.mns.cda.suivimns.model.Technician;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MocktechnicianDao implements TechnicianDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Technician> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Technician> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Technician> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Technician getOne(Integer integer) {
        return null;
    }

    @Override
    public Technician getById(Integer integer) {
        return null;
    }

    @Override
    public Technician getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Technician> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Technician> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Technician> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Technician> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Technician> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Technician> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Technician, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Technician> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Technician> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Technician> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Technician> findAll() {
        return List.of();
    }

    @Override
    public List<Technician> findAllById(Iterable<Integer> integers) {
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
    public void delete(Technician entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Technician> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Technician> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Technician> findAll(Pageable pageable) {
        return null;
    }
}
