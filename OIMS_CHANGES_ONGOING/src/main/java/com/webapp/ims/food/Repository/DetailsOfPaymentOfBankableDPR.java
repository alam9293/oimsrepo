package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.DetailsOfPaymentBankableDPR;
import com.webapp.ims.food.Model.FoodBankableDPR;

@Repository
public interface DetailsOfPaymentOfBankableDPR extends JpaRepository<DetailsOfPaymentBankableDPR, String> {

	public List<DetailsOfPaymentBankableDPR> getDetailsByunitId(String unitId);
}
