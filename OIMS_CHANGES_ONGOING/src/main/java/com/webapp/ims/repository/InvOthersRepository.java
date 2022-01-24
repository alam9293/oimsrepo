package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.INVOthers;

@Repository
public interface InvOthersRepository extends JpaRepository<INVOthers, String> {
	public List<INVOthers> findAllByinvid(String invid);
}
