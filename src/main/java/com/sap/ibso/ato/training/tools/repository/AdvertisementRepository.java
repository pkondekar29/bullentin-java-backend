package com.sap.ibso.ato.training.tools.repository;

import com.sap.ibso.ato.training.tools.model.Advertisement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends CrudRepository<Advertisement, Long> {
}
