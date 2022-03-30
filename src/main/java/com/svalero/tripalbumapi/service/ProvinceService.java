package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.ProvinceDTO;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;

import java.util.List;

public interface ProvinceService {
    List<Province> findAllProvinces();
    List<Province> findProvinces(Country country);
    Province findProvince(long id) throws ProvinceNotFoundException;

    Province addProvince(ProvinceDTO provinceDto) throws CountryNotFoundException;
    void deleteProvince(long id) throws ProvinceNotFoundException;
    Province modifyProvince(long id, ProvinceDTO provinceDto) throws ProvinceNotFoundException, CountryNotFoundException;
}
