package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.ProvinceDTO;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProvinceService {
    Flux<Province> findAllProvinces();
    Flux<Province> findProvinces(Country country);
    Mono<Province> findProvince(String id) throws ProvinceNotFoundException;

    Mono<Province> addProvince(ProvinceDTO provinceDto) throws CountryNotFoundException;
    Mono<Void> deleteProvince(String id) throws ProvinceNotFoundException;
    Mono<Province> modifyProvince(String id, ProvinceDTO provinceDto) throws ProvinceNotFoundException, CountryNotFoundException;
    Mono<Province> patchProvince(String id, String name) throws ProvinceNotFoundException;
}
