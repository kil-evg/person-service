package ait.cohort46.person.service;

import ait.cohort46.person.dto.ChildDto;
import ait.cohort46.person.dto.EmployeeDto;
import ait.cohort46.person.dto.PersonDto;
import ait.cohort46.person.model.Child;
import ait.cohort46.person.model.Employee;
import ait.cohort46.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonModelDtoMapper {
    private final ModelMapper modelMapper;

//    public PersonDto mapToDto(Person person) {
//        if (person instanceof Child) {
//            return modelMapper.map(person, ChildDto.class);
//        }
//        if (person instanceof Employee) {
//            return modelMapper.map(person, EmployeeDto.class);
//        }
//        return modelMapper.map(person, PersonDto.class);
//    }

    public <R, T> R mapToDto(T modelObj) {
        String dtoClassName = modelObj.getClass().getSimpleName() + "Dto";
        String dtoPackageName = "ait.cohort46.person.dto";
        try {
            Class<R> dtoClass = (Class<R>) Class.forName(dtoPackageName + "." + dtoClassName);
            return modelMapper.map(modelObj, dtoClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public Person mapToModel(PersonDto personDto) {
//        if (personDto instanceof ChildDto) {
//            return modelMapper.map(personDto, Child.class);
//        }
//        if (personDto instanceof EmployeeDto) {
//            return modelMapper.map(personDto, Employee.class);
//        }
//        return modelMapper.map(personDto, Person.class);
//    }

    public <R, T> R mapToModel(T dtoObj) {
        String modelClassName = dtoObj.getClass().getSimpleName().replace("Dto", "");
        String modelPackageName = "ait.cohort46.person.model";
        try {
            Class<R> modelClass = (Class<R>) Class.forName(modelPackageName + "." + modelClassName);
            return modelMapper.map(dtoObj, modelClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}