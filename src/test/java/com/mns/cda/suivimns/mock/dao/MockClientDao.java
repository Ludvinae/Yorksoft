package com.mns.cda.suivimns.mock.dao;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockClientDao implements ClientDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Client> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Client> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Client> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Client getOne(Integer integer) {
        return null;
    }

    @Override
    public Client getById(Integer integer) {
        return null;
    }

    @Override
    public Client getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Client> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Client> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Client> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Client> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Client> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Client, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Client> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Client> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Client> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public List<Client> findAllById(Iterable<Integer> integers) {
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
    public void delete(Client entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Client> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Client> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<ClientDto> getClient(int id) {
        return Optional.empty();
    }

    @Override
    public List<ClientDto> getAllClient() {
        return List.of();
    }

}
