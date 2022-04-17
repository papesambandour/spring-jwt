package dev.local.springjwt.dao;

import dev.local.springjwt.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilDao extends JpaRepository<Profil,Long> {
}
