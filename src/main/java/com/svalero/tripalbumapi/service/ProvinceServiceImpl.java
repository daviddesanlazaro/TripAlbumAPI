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
        Mono<Province> provinceError = Mono.error(new ProvinceNotFoundException());
        return provinceRepository.findById(id).switchIfEmpty(provinceError);
    }

    @Override
    public Mono<Province> addProvince(ProvinceDTO provinceDto) throws CountryNotFoundException {
        Mono<Country> countryError = Mono.error(new CountryNotFoundException());
        Mono<Country> monoCountry = countryRepository.findById(provinceDto.getCountry()).switchIfEmpty(countryError);

        ModelMapper mapper = new ModelMapper();
        Province province = mapper.map(provinceDto, Province.class);
        province.setCountry(monoCountry.block());
        return provinceRepository.save(province);
    }

    @Override
    public Mono<Void> deleteProvince(String id) throws ProvinceNotFoundException {
        Mono<Province> provinceError = Mono.error(new ProvinceNotFoundException());
        provinceRepository.findById(id).switchIfEmpty(provinceError);
        return provinceRepository.deleteById(id);
    }

    @Override
    public Mono<Province> modifyProvince(String id, ProvinceDTO provinceDto) throws ProvinceNotFoundException, CountryNotFoundException {
        Mono<Province> provinceError = Mono.error(new ProvinceNotFoundException());
        Mono<Province> monoProvince = provinceRepository.findById(id).switchIfEmpty(provinceError);
        Mono<Country> countryError = Mono.error(new CountryNotFoundException());
        Mono<Country> monoCountry = countryRepository.findById(provinceDto.getCountry()).switchIfEmpty(countryError);

        if (!monoProvince.block().getId().equals(id))
            return provinceError;

        ModelMapper mapper = new ModelMapper();
        Province province = mapper.map(provinceDto, Province.class);
        province.setId(id);
        province.setCountry(monoCountry.block());

        return provinceRepository.save(province);
    }

    @Override
    public Mono<Province> patchProvince(String id, String name) throws ProvinceNotFoundException {
        Mono<Province> provinceError = Mono.error(new ProvinceNotFoundException());
        Province province = provinceRepository.findById(id).switchIfEmpty(provinceError).block();
        province.setName(name);
        return provinceRepository.save(province);
    }
}
