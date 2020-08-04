package com.ecommerce.services;

import com.ecommerce.domains.Address;
import com.ecommerce.domains.City;
import com.ecommerce.domains.User;
import com.ecommerce.domains.enums.UserType;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.dto.UserNewDTO;
import com.ecommerce.repositories.AddressRepository;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.services.exceptions.DataIntegrityException;
import com.ecommerce.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! id" +
                id + "Tipo: " + User.class.getName()));
    }

    public List<User> findCategoriesList() {
        return userRepository.findAll();
    }

    public Page<User> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        return userRepository.findAll(pageRequest);
    }

    @Transactional
    public User createUser(User user) {
        user.setId(null);
        userRepository.save(user);
        addressRepository.saveAll(user.getAddressList());
        return user;
    }


    public User updaterUser(User user) {
        User newUser = findUserById(user.getId());
        updateData(newUser, user);
        return userRepository.save(newUser);
    }

    public User fromDTO(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail(), null, null);
    }

    public User fromDTO(UserNewDTO dto) {
        User user = new User(null, dto.getName(),
                dto.getEmail(), dto.getUserCode(), UserType.toEnum(dto.getUserType()));
        City city = new City(dto.getCityId(), null, null);
        Address  address = new Address(null, dto.getPlace(),
                dto.getNumber(), dto.getComplement(), dto.getNeighborhood(), dto.getPostCode(), user, city);
        user.getAddressList().add(address);
        user.getPhones().add(dto.getPhone1());
        if (dto.getPhone2() != null) {
            user.getPhones().add(dto.getPhone2());
        }
        if (dto.getPhone3() != null) {
            user.getPhones().add(dto.getPhone3());
        }
        return user;
    }

    public void deleteById(Long id) {
        findUserById(id);
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir usuário porque possua entidades relacionadas");
        }
    }

    private void updateData(User newUser, User user){
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
    }
}
