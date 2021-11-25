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

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Province> findAllProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public List<Province> findProvinces(Country country) {
        return provinceRepository.findByCountry(country);
    }

    @Override
    public Province findProvince(long id) throws ProvinceNotFoundException {
        return provinceRepository.findById(id)
                .orElseThrow(ProvinceNotFoundException::new);
    }

    @Override
    public Province addProvince(ProvinceDTO provinceDto) throws CountryNotFoundException {
        Country country = countryRepository.findById(provinceDto.getCountry())
                .orElseThrow(CountryNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Province province = mapper.map(provinceDto, Province.class);
        province.setCountry(country);
        return provinceRepository.save(province);
    }

    @Override
    public Province deleteProvince(long id) throws ProvinceNotFoundException {
        Province province = provinceRepository.findById(id)
                .orElseThrow(ProvinceNotFoundException::new);
        provinceRepository.delete(province);
        return province;
    }

    @Override
    public Province modifyProvince(long id, ProvinceDTO newProvinceDto) throws ProvinceNotFoundException, CountryNotFoundException {
        Province province = provinceRepository.findById(id)
                .orElseThrow(ProvinceNotFoundException::new);
        Country country = countryRepository.findById(newProvinceDto.getCountry())
                .orElseThrow(CountryNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Province newProvince = mapper.map(newProvinceDto, Province.class);
        newProvince.setCountry(country);

        return provinceRepository.save(newProvince);
    }


}