package br.com.ifba.infrastructure.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

// superclasse das entidades: so guarda o id, que toda tabela do projeto tem
// @MappedSuperclass faz o Hibernate colocar o id na tabela de cada filha, sem criar tabela propria
@MappedSuperclass
public abstract class PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
