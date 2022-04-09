package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.ProvinceDTO;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import com.svalero.tripalbumapi.repository.CountryRepository;
import com.svalero.tripalbumapi.repository.ProvinceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Flux<Province> findAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public Flux<Province> findProvinces(Country country) {
        return provinceRepository.findByCountry(country);
    }

    @Override
    public Mono<Province> findProvince(String id) throws ProvinceNotFoundException {
        return provinceRepository.findById(id).onErrorReturn(new Province());
    }

    @Override
    public Mono<Province> addProvince(ProvinceDTO provinceDto) throws CountryNotFoundException {
        Mono<Country> country = countryRepository.findById(provinceDto.getCountry()).onErrorReturn(new Country());

        ModelMapper mapper = new ModelMapper();
        Province province = mapper.map(provinceDto, Province.class);
        province.setCountry(country.block());
        return provinceRepository.save(province);
    }

    @Override
    public Mono<Void> deleteProvince(String id) throws ProvinceNotFoundException {
        Mono<Province> province = provinceRepository.findById(id).onErrorReturn(new Province());
        return provinceRepository.deleteById(id);
    }

    @Override
    public Mono<Province> modifyProvince(String id, ProvinceDTO newProvinceDto) throws ProvinceNotFoundException, CountryNotFoundException {
        Mono<Province> monoProvince = provinceRepository.findById(id).onErrorReturn(new Province());
        Mono<Country> country = countryRepository.findById(newProvinceDto.getCountry()).onErrorReturn(new Country());

        ModelMapper mapper = new ModelMapper();
        Province province = mapper.map(newProvinceDto, Province.class);
        province.setId(id);
        province.setCountry(country.block());

        return provinceRepository.save(province);
    }
}
