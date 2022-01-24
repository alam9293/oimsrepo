package com.webapp.ims.dis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.dis.model.Disepfriem;

@Repository
public interface DisEPFRepository extends JpaRepository<Disepfriem,String> {
	List<Disepfriem> findByAppId(String appId);
	Disepfriem getDetailsByappId(String appId);
}
