package ait.cohort46.person.service;

import ait.cohort46.person.dao.PersonRepository;
import ait.cohort46.person.dto.AddressDto;
import ait.cohort46.person.dto.CityPopulationDto;
import ait.cohort46.person.dto.PersonDto;
import ait.cohort46.person.dto.exception.PersonNotFoundException;
import ait.cohort46.person.model.Address;
import ait.cohort46.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public Boolean addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            return false;
        }
        personRepository.save(modelMapper.map(personDto, Person.class));
        return true;
    }

    @Override
    public PersonDto findPersonById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto removePerson(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto updatePersonName(Integer id, String name) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if (name != null) {
            person.setName(name);
        }
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto updatePersonAddress(Integer id, AddressDto addressDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        Address currentAddress = person.getAddress();
        if (currentAddress == null) {
            currentAddress = new Address();
        }
        if (addressDto.getCity() != null) {
            currentAddress.setCity(addressDto.getCity());
        }
        if (addressDto.getStreet() != null) {
            currentAddress.setStreet(addressDto.getStreet());
        }
        if (addressDto.getBuilding() > 0) {
            currentAddress.setBuilding(addressDto.getBuilding());
        }
        person.setAddress(currentAddress);
        personRepository.save(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    public PersonDto[] findPersonsByCity(String city) {
        Person[] persons = personRepository.findByAddress_City(city);
        return Arrays.stream(persons)
                .map(p -> modelMapper.map(p, PersonDto.class))
                .toArray(PersonDto[]::new);

    }

    @Override
    public PersonDto[] findPersonsByName(String name) {
        Person[] persons = personRepository.findByNameIgnoreCase(name);
        return Arrays.stream(persons)
                .map(p -> modelMapper.map(p, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public PersonDto[] findPersonsBetweenAge(Integer minAge, Integer maxAge) {
        LocalDate from = LocalDate.now().minusYears(maxAge);
        LocalDate to = LocalDate.now().minusYears(minAge);
        Person[] persons = personRepository.findByBirthDateBetween(from, to);
        return Arrays.stream(persons)
                .map(p -> modelMapper.map(p, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public Iterable<CityPopulationDto> getCitiesPopulation() {
        return null;
    }
}