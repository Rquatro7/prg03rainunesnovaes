package br.com.ifba.infrastructure.dao;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import java.util.List;

// contrato basico de acesso a dados, igual para qualquer entidade do projeto
public interface GenericIDao<Entity extends PersistenceEntity> {

    void save(Entity entity);

    Entity update(Entity entity);

    void delete(Entity entity);

    Entity findById(Long id);

    List<Entity> findAll();
}
