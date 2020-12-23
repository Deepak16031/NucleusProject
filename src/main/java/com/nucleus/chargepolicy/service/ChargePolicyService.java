package com.nucleus.chargepolicy.service;

import com.nucleus.chargepolicy.dao.ChargePolicyDao;
import com.nucleus.chargepolicy.model.ChargePolicy;

import java.util.List;

public interface ChargePolicyService {

    public List<ChargePolicy> getPolicyList();
    public void setEligibilityPolicyDAO(ChargePolicyDao chargePolicyDao);
    public int insert(ChargePolicy chargePolicy);
    public void getCharge(String code);
    ChargePolicy getChargePolicy(String chargePolicyCode);
    void updateStatus(String chargePolicyCode,String newStatus,String approvedBy);
    void updateEntry(ChargePolicy chargePolicy, String chargePolicyCode);
    void deleteChargePolicy(String chargePolicyCode);
    List<String> getChargeCodesList();
    public String getChargeCodeName(String chargeCode);
}
